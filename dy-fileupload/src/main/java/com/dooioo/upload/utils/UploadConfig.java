package com.dooioo.upload.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午1:59.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class UploadConfig {
    private static final Logger LOGGER = Logger.getLogger(UploadConfig.class);
    private static UploadConfig uploadConfig = null;

    private String originalDirectory;
    private String targetDirectory;
    private String docDirectory;
    private String recordDirectory;
    private String flashDirectory;
    private Properties properties;

    private UploadConfig(){
        properties= new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("upload.properties"));
            originalDirectory = properties.getProperty("originalDirectory");
            targetDirectory = properties.getProperty("targetDirectory");
            docDirectory = properties.getProperty("docDirectory");
            recordDirectory = properties.getProperty("recordDirectory");
            flashDirectory = properties.getProperty("flashDirectory");
        } catch (IOException e) {
            LOGGER.error("UploadConfig -> init",e);
        }
    }

    public static synchronized  UploadConfig getInstance(){
        if(uploadConfig == null){
            uploadConfig = new UploadConfig();
        }
        return uploadConfig;
    }

    public String getOriginalDirectory() {
        return originalDirectory;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getDocDirectory() {
        return docDirectory;
    }

    public String getRecordDirectory() {
        return recordDirectory;
    }

    public String getFlashDirectory() {
        return flashDirectory;
    }

    public String getDocPath() {
        return properties.getProperty("docPath","");
    }

    public String getRecordPath() {
        return properties.getProperty("recordPath","");
    }

    public String getFlashPath() {
        return properties.getProperty("flashPath","");
    }

    public String getLogoHuge(){
        return properties.getProperty("logoHuge");
    }

    public String getTileLogoPath(){
        return properties.getProperty("tileLogoPath");
    }

    public String getSuperLogoPath(){
        return properties.getProperty("superLogoPath");
    }
}
