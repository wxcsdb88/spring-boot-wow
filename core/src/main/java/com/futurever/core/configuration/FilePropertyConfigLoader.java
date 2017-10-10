package com.futurever.core.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by wxcsdb88 on 2017/9/27 15:23.
 */
public class FilePropertyConfigLoader implements ConfigLoader {
    protected static Logger logger = LoggerFactory.getLogger(FilePropertyConfigLoader.class);
    private String fileName = "/config/config.properties";

    public FilePropertyConfigLoader() {
    }

    public FilePropertyConfigLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Properties load() throws Exception {
        Properties properties = new Properties();
        try {
            InputStream is = getClass().getResourceAsStream(this.fileName);
            if (is == null) {
                String fileNameOrign = this.fileName;
                // tips: spring boot 配置文件处于jar 外的读取
                String absolutePath = new File("").getAbsolutePath();
                this.fileName = absolutePath + File.separator + fileName;
                File file = new File(this.fileName);
                if (file.exists()) {
                    is = new FileInputStream(file);
                    logger.debug("The File: %s not exist in the classpath and reload from outer %s", fileNameOrign, this.fileName);
                }
            }
            properties.load(is);
        } catch (Exception e) {
            logger.debug("Can't Read Properties from File: " + this.fileName);
        }
        return properties;
    }

    @Override
    public void save(Properties properties) throws Exception {
        try {
            File propFile = new File(this.fileName);
            properties.store(new FileOutputStream(propFile), "");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Can't Save Properties to File:" + this.fileName);
        }
    }
}
