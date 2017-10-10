package com.futurever.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by wxcsdb88 on 2017/9/27 15:53.
 */
public class FileYamlConfigLoader implements ConfigLoader {
    protected static Logger logger = LoggerFactory.getLogger(FileYamlConfigLoader.class);
    private String fileName = "/config/config.yml";

    public FileYamlConfigLoader() {
    }

    public FileYamlConfigLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Properties load() throws Exception {
        return null;
    }

    @Override
    public void save(Properties paramProperties) throws Exception {

    }
}
