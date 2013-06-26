package com.dooioo.upload.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午2:50.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */

public class UploadConfigTest {
    private UploadConfig uploadConfig;

    @Before
    public  void start(){
        uploadConfig = UploadConfig.getInstance();
    }

    @Test
    public void testGetOriginalDirectory(){
        Assert.assertEquals("originalDirectory is error",uploadConfig.getOriginalDirectory(),"E:/data/orgimages");
    }

    @Test
    public void testGetTargetDirectory(){
        Assert.assertEquals("originalDirectory is error",uploadConfig.getTargetDirectory(),"E:/data/images");
    }

    @Test
    public void testGetDocDirectory(){
        Assert.assertEquals("originalDirectory is error",uploadConfig.getDocDirectory(),"E:/data/dcos");
    }

    @Test
    public void testGetRecordDirectory(){
        Assert.assertEquals("originalDirectory is error",uploadConfig.getRecordDirectory(),"E:/data/records");
    }

    @Test
    public void testGetFlashDirectory(){
        Assert.assertEquals("originalDirectory is error",uploadConfig.getFlashDirectory(),"E:/data/flashs");
    }
}
