package com.futurever.core.configuration;

import java.util.Properties;

/**
 * Created by wxcsdb88 on 2017/9/27 15:18.
 */
public interface ConfigLoader {
    /**
     * load config file
     * @return
     * @throws Exception
     */
    public abstract Properties load()
            throws Exception;

    /**
     * save the config file modify
     * @param paramProperties
     * @throws Exception
     */
    public abstract void save(Properties paramProperties)
            throws Exception;
}
