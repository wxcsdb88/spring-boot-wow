package com.futurever.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wxcsdb88 on 2017/9/27 15:45.
 */
public class FileXmlConfigLoader implements ConfigLoader {
    protected static Logger logger = LoggerFactory.getLogger(FilePropertyConfigLoader.class);
    private String fileName = "/config/config.xml";

    public FileXmlConfigLoader() {
    }

    public FileXmlConfigLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Properties load()
            throws Exception {
        Properties properties = new Properties();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream is = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            is = getClass().getResourceAsStream(this.fileName);
            Document doc = builder.parse(is, "UTF-8");
            Element root = doc.getDocumentElement();


            NodeList configList = root.getElementsByTagName("package");
            for (int i = 0; i < configList.getLength(); i++) {
                Element configElement = (Element) configList.item(i);
                String packageName = configElement.getAttribute("name");
                if ((packageName != null) && (!"".equals(packageName))) {
                    packageName = packageName + ".";
                } else {
                    packageName = "";
                }
                NodeList propertyList = configElement.getElementsByTagName("property");
                for (int j = 0; j < propertyList.getLength(); j++) {
                    Element propertyElement = (Element) propertyList.item(j);
                    properties.put(packageName + propertyElement.getAttribute("name"), propertyElement.getTextContent());
                }
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Can't Read Properties from File:" + this.fileName);
            }
            try {
                is.close();
            } catch (IOException localIOException) {
            }
        } finally {
            try {
                is.close();
            } catch (IOException localIOException1) {
            }
        }
        return properties;
    }

    @Override
    public void save(Properties properties)
            throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        OutputStream os = new FileOutputStream(this.fileName);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("config");
            doc.appendChild(root);

            Map<String, Element> keyPathMap = new HashMap<>(properties.stringPropertyNames().size());
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                String keyPack = key.substring(0, key.indexOf("."));
                String keyProp = key.substring(key.indexOf("."));
                Element pack = (Element) keyPathMap.get(keyPack);
                if (pack == null) {
                    pack = doc.createElement("package");
                    pack.setAttribute("name", keyPack);
                    root.appendChild(pack);
                    keyPathMap.put(keyPack, pack);
                }
                Element prop = doc.createElement("property");
                prop.setAttribute(keyProp, value);
                pack.appendChild(prop);
            }
            OutputStreamWriter outwriter = new OutputStreamWriter(os);
            Object source = new DOMSource(doc);
            Result result = new StreamResult(outwriter);
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty("encoding", "UTF-8");
            xformer.transform((Source) source, result);
            outwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Can't Save Properties to File:" + this.fileName + "! Disk IO Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Can't Save Properties to File:" + this.fileName);
        }
    }
}
