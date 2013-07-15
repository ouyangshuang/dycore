package com.dooioo.upload.uploads;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA at 13-6-26 上午10:44.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class ZipFileUploadTest {
    private byte[] data;

    @Before
    public void setup() throws IOException {
        data = FileUtils.readFileToByte("E:/data/TelServer.rar");
    }

    @Test
    public void testUpload() throws UploadException {
//        ZipFileUpload.upload(data,"TelServer.zip",false);
        System.out.println(ZipFileUpload.upload(data,"TelServer.rar",false));
    }

    @Test
    public void testUploadAndUnzip() throws UploadException {
//        ZipFileUpload.upload(data,"TelServer.zip",true);
        System.out.println(ZipFileUpload.upload(data,"TelServer.rar",true));
    }
}
