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

    private String docPath;
    private String recordPath;
    private String flashPath;

    private String logoHuge;
    private String tileLogoPath;
    private String superLogoPath;

    private Properties properties;

    private UploadConfig(){
        properties= new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("upload.properties"));
            originalDirectory = properties.getProperty("originalDirectory","/origimagesdisk/");
            targetDirectory = properties.getProperty("targetDirectory","/imagesdisk/images/");
            docDirectory = properties.getProperty("docDirectory","/flashdisk/");
            recordDirectory = properties.getProperty("recordDirectory","/recorddisk/");
            flashDirectory = properties.getProperty("flashDirectory","/flashdisk/");

            docPath =  properties.getProperty("docPath","/flashdisk/");
            flashPath =  properties.getProperty("flashPath","/flashdisk/");
            recordPath = properties.getProperty("recordPath","/recorddisk/");

            logoHuge =  properties.getProperty("logoHuge","/files131/images/logo_huge.png");
            tileLogoPath =  properties.getProperty("logoHuge","/files131/images/logo_tile.png");
            superLogoPath =  properties.getProperty("logoHuge","/files131/images/logo_super.png");
        } catch (IOException e) {
            LOGGER.error("UploadConfig -> init",e);
            originalDirectory = "/origimagesdisk/";
            targetDirectory = "/imagesdisk/images/";
            docDirectory = "/flashdisk/";
            recordDirectory = "/recorddisk/";
            flashDirectory = "/flashdisk/";

            docPath =  "/flashdisk/";
            flashPath =  "/flashdisk/";
            recordPath = "/recorddisk/";
            logoHuge =  "/files131/images/logo_huge.png";
            tileLogoPath =  "/files131/images/logo_tile.png";
            superLogoPath =  "/files131/images/logo_super.png";
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
        return docPath;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public String getFlashPath() {
        return flashPath;
    }

    public String getLogoHuge(){
        return logoHuge;
    }

    public String getTileLogoPath(){
        return tileLogoPath;
    }

    public String getSuperLogoPath(){
        return superLogoPath;
    }
}
