package com.futurever.elm.api.constants;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;

/**
 * Created by wxcsdb88 on 2017/8/23 9:28.
 */
public interface RequestField {

    String DEFAULT_PAGE_NUM = "1";
    String DEFAULT_PAGE_SIZE = "10";

    HashMap<String, Boolean> CONTENT_TYPE_VALUE_MAP = new HashMap<String, Boolean>() {

        // 允许的请求头字段， 目前限于 json ，xml， 流数据
        private static final long serialVersionUID = -4683167907376040562L;

        {
            put(MediaType.APPLICATION_JSON_VALUE, true);
            put(MediaType.APPLICATION_JSON_UTF8_VALUE, true);
            put(MediaType.APPLICATION_OCTET_STREAM_VALUE, true);
            put(MediaType.APPLICATION_XML_VALUE, true);
            put(MediaType.APPLICATION_FORM_URLENCODED_VALUE, true);
            put(MediaType.TEXT_XML_VALUE, true);
            put(MediaType.MULTIPART_FORM_DATA_VALUE, true);
        }
    };
    // common fields
    // 主键之类的，一般为Long型
    String ID = "id";
    String USERNAME = "username";
    String PASSWD = "password";
    String NAME = "name";
    String ROLE = "role";
    String STATUS = "status";
    String NAME_EN = "nameEn";
    String STARTTIME = "startTime";
    String ENDTIME = "endTime";
    String FORMAT = "format";
    String FILENAME = "filename";
    String LINE = "line";
    String PATH = "path";
    String METHOD_NAME = "methodName";
    // 查询条件类参数
    // 按照字段排序，以逗号分割，前面如果有-号，则表示逆序
    String SORT = "sort";
    // 展示字段
    String FIELD = "field";
    // 分页参数
    String PAGE_NUM = "pageNum";
    String PAGE_SIZE = "pageSize";
    String PAGE_TOTAL = "total";
    // 权限验证相关请求参数
    String APPID = "appId";
    String TOKEN = "token";
    HashMap<String, Boolean> HEADERS_MAP = new HashMap<String, Boolean>() {

        private static final long serialVersionUID = 7775136902227806197L;

        {
            put(HttpHeaders.CONTENT_TYPE, true);
            put(RequestField.TOKEN, true);
        }
    };
    String TIMESTAMP = "timestamp";
    String SIGN = "sign";

    // mxGraph and log view
    String XML = "xml";
    String HEIGHT = "h";
    String WIDTH = "w";
    String MIME = "mime";

    // 合约状态参数
    String Contract_Unknown = "Contract_Unknown";
    String Contract_Create = "Contract_Create";
    String Contract_Signature = "Contract_Signature";
    String Contract_In_Process = "Contract_In_Process";
    String Contract_Completed = "Contract_Completed";
    String Contract_Discarded = "Contract_Discarded";

    // 合约相关参数
    String PUBKEY = "pubkey";
    String PRIKEY = "prikey";
    String XML_CONTRACT = "xmlContract";
    String JSON_CONTRACT = "jsonContract";
    String CREATE_FlAG = "createFlag";

    String CONTRACT_PUBLISHER = "publishOwner";
    String CONTRACT_AUDIT_OP = "auditOp";
    String CONTRACT_SUGGESTION = "suggestion";
    String CONTRACT_ID = "contractId";
    String CONTRACT_OWNER = "contractOwner";
    String CONTRACT_STATE = "contractState";
    String CONTRACT_NAME = "contractName";

    // common fields
    // 主键之类的，一般为Long型
    HashMap<String, Boolean> COMMON_FIELDS_MAP = new HashMap<String, Boolean>() {
        private static final long serialVersionUID = -4683167907376040562L;

        {
            put(ID, true);
            put(USERNAME, true);
            put(PASSWD, true);
            put(NAME, true);
            put(ROLE, true);
            put(STATUS, true);
            put(NAME_EN, true);
            put(STARTTIME, true);
            put(ENDTIME, true);
            put(FORMAT, true);
            put(FILENAME, true);
            put(LINE, true);
            put(PATH, true);
            put(METHOD_NAME, true);
        }
    };

    HashMap<String, Boolean> QUERY_FIELDS_MAP = new HashMap<String, Boolean>() {
        private static final long serialVersionUID = 7775136902227806197L;

        {
            put(SORT, true);
            put(FIELD, true);
        }
    };
    // 分页参数
    HashMap<String, Boolean> PAGE_FIELDS_MAP = new HashMap<String, Boolean>() {
        private static final long serialVersionUID = 7775136902227806197L;

        {
            put(PAGE_NUM, true);
            put(PAGE_SIZE, true);
            put(PAGE_TOTAL, true);
        }
    };

    HashMap<String, Boolean> AUTH_FIELDS_MAP = new HashMap<String, Boolean>() {
        private static final long serialVersionUID = 7678601511286988624L;

        {
            put(APPID, true);
            put(TOKEN, true);
            put(TIMESTAMP, true);
            put(SIGN, true);
        }
    };

    HashMap<String, Boolean> OTHERS_FIELDS_MAP = new HashMap<String, Boolean>() {
        private static final long serialVersionUID = 6430092683526767280L;

        {
            put(XML, true);
            put(HEIGHT, true);
            put(WIDTH, true);
            put(MIME, true);
        }
    };

    HashMap<String, Boolean> CONTRACT_STATE_FIELDS_MAP = new HashMap<String, Boolean>() {

        private static final long serialVersionUID = 1326337925772304485L;

        {
            put(Contract_Unknown, true);
            put(Contract_Create, true);
            put(Contract_Signature, true);
            put(Contract_In_Process, true);
            put(Contract_Completed, true);
            put(Contract_Discarded, true);
        }
    };

    HashMap<String, Boolean> CONTRACT_FIELDS_MAP = new HashMap<String, Boolean>() {

        private static final long serialVersionUID = 6027192644764110097L;

        {
            put(PUBKEY, true);
            put(PRIKEY, true);
            put(XML_CONTRACT, true);
            put(JSON_CONTRACT, true);
            put(CREATE_FlAG, true);

            put(CONTRACT_PUBLISHER, true);
            put(CONTRACT_AUDIT_OP, true);
            put(CONTRACT_SUGGESTION, true);
            put(CONTRACT_ID, true);
            put(CONTRACT_OWNER, true);
            put(CONTRACT_STATE, true);
            put(CONTRACT_NAME, true);
        }
    };

    HashMap<String, Boolean> ALL_FIELDS_MAP = new HashMap<String, Boolean>() {

        private static final long serialVersionUID = -2881395865014355693L;

        {
            putAll(COMMON_FIELDS_MAP);
            putAll(QUERY_FIELDS_MAP);
            putAll(PAGE_FIELDS_MAP);
            putAll(AUTH_FIELDS_MAP);
            putAll(OTHERS_FIELDS_MAP);
            putAll(CONTRACT_STATE_FIELDS_MAP);
            putAll(CONTRACT_FIELDS_MAP);
        }
    };
}
