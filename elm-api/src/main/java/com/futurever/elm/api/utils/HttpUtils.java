package com.futurever.elm.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.TreeMap;

/**
 * Created by wxcsdb88 on 2017/5/10 11:26.
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);


    private CloseableHttpClient httpClient;

    public HttpUtils() {
        httpClient = HttpClients.createDefault();
    }

    public static void main(String[] args) {
        HttpUtils httpclient = new HttpUtils();
        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("owner", 123);
        jsonObject.addProperty("status", gson.toJson(1));
        jsonObject.addProperty("signatureStatus", 2321);
        jsonObject.addProperty("name", 321);
        jsonObject.addProperty("contractId", 312);
    }

    /**
     * post to API
     * 1. when data is instanceof byte[], charset is invalid and the contentType should application/x-protobuf;
     * 2. when data is instanceof String(jsonString), charset is valid or default UTF-8;
     *
     * @param url         post API URI
     * @param data        post data, type of byte[] or jsonString
     * @param contentType HTTP Header Content-Type
     * @param token       token
     * @param charset     string Charset
     * @return {@link byte[]} of response
     * @throws Exception httpclient exception
     */
    public byte[] post(String url, Object data, String token, String contentType, TreeMap<String, String> queryParamMap, String charset) throws Exception {
        String queryString = RequestUtils.parseQueryParamMapToQuereyString(queryParamMap, "sign", "timestamp");
        String timestamp = DateUtils.currentTimestampStr();
        if ("".equals(queryString)) {
            queryString += "timestamp=" + timestamp + "&appId=15b12db830bb402258d616e7e8c80830" + "&token=66B227778E75D76FDB02DEABB474551C";
        } else {
            queryString += "&timestamp=" + timestamp + "&appId=15b12db830bb402258d616e7e8c80830" + "&token=66B227778E75D76FDB02DEABB474551C";
        }
        String sign = RequestUtils.getSign(queryString, true);
        StringBuilder sb = new StringBuilder(url);
        sb.append("?").append(queryString).append("&sign=").append(sign);
        url = sb.toString();

        HttpPost httpPost = new HttpPost(url);
        if (data instanceof String) {
            if (contentType == null || "".equals(contentType)) {
                contentType = "application/json";
            }
            if (charset == null || "".equals(charset)) {
                charset = "UTF-8";
            }
            StringEntity stringEntity = new StringEntity((String) data, charset);
            httpPost.setHeader(HTTP.CONTENT_TYPE, contentType);
            httpPost.setEntity(stringEntity);
        } else if (data instanceof byte[]) {
            if (contentType == null || "".equals(contentType)) {
                contentType = "application/x-protobuf"; // protobuf transfer
            }
            httpPost.setEntity(new ByteArrayEntity((byte[]) data));
            httpPost.setHeader("Content-Type", contentType);
        } else {
            if (contentType == null || "".equals(contentType)) {
                contentType = "application/x-protobuf"; // protobuf transfer
            }
            httpPost.setHeader("Content-Type", contentType);
        }
        if (token != null && !"".equals(token)) {
            httpPost.setHeader("token", token);
        }

        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            entityResponse = httpResponse.getEntity();
            log.info(String.format("url=%s, token=%s, response Header=%s", url, token, entityResponse.getContentType().toString()));
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK || httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                return EntityUtils.toByteArray(entityResponse);
            } else {
                throw new IOException("Remote Server response code is not 200! It`s " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }

    /**
     * @param url get API URI
     * @return string of response
     * @throws Exception httpclient exception
     */
    public String get(String url) throws Exception {

        String responseBody = "";
        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        //用get方法发送http请求
        HttpGet get = new HttpGet(url);
        try {
            //发送get请求
            httpResponse = httpClient.execute(get);
            entityResponse = httpResponse.getEntity();
            log.info(String.format("url=%s", url));
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK || httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                return EntityUtils.toString(entityResponse);
//                return EntityUtils.toByteArray(entityResponse);
            } else {
                throw new IOException("Remote Server response code is not 200! It`s " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }

    public String postXml(String url, String xmlStr, String token) throws Exception {
        byte[] reqBuffer = xmlStr.getBytes(Charset.forName("UTF-8"));
        byte[] respBuffer = post(url, reqBuffer, token, "application/xml; charset=UTF-8", null, null);
        return new String(respBuffer, Charset.forName("UTF-8"));
    }

}
