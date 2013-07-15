package com.dooioo.upload.uploads;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA at 13-6-26 上午9:37.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class DocUploadTest {
    private byte[] data;

    @Before
    public void setup() throws IOException {
        data = FileUtils.readFileToByte("E:/data/dooioo.jpg");
    }

    @Test
    public void testUpload() throws UploadException {
        System.out.println(DocUpload.upload(data,"dooioo.jpg"));
    }
}
