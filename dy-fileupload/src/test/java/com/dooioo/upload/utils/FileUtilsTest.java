package com.dooioo.upload.utils;

import com.dooioo.commons.io.IoUtils;
import com.dooioo.upload.exception.UploadException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA at 13-6-25 下午4:55.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class FileUtilsTest {
    byte[] data;
    @Before
    public void setUp(){
        try {
            data = IoUtils.copyToByteArray(new FileInputStream("e:/data/dooioo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetFileExtName() throws UploadException {
        Assert.assertEquals("Error FileUtils.getFileExtName",FileUtils.getFileExtName("e:/data/dooioo.jpg"),"jpg");
        Assert.assertEquals("Error FileUtils.getFileExtName",FileUtils.getFileExtName("e:/data/dooioo.JPEG"),"JPEG");
        Assert.assertEquals("Error FileUtils.getFileExtName",FileUtils.getFileExtName("e:/data/dooioo.zip"),"zip");
    }

    @Test(expected = UploadException.class)
    public void testgetFileExtNameError() throws UploadException {
        Assert.assertEquals("Error FileUtils.getFileExtName",FileUtils.getFileExtName("e:/data/dooioo"),"zip");
    }

    @Test
    public void testReadToByte() throws IOException {
        byte[] data2 = FileUtils.readFileToByte("e:/data/dooioo.jpg");
        FileUtils.writeByteToFile(data2,"e:/data/dooioo3.jpg");
    }

    @Test
    public void testWriteFile() throws IOException {
        FileUtils.writeByteToFile(data,"e:/data/dooioo2.jpg");
    }
}
