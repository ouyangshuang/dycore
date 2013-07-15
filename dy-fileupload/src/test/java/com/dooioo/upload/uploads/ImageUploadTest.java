package com.dooioo.upload.uploads;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.image.ImageSize;
import com.dooioo.upload.image.Logo;
import com.dooioo.upload.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA at 13-6-26 上午11:50.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class ImageUploadTest {
    private byte[] data;

    @Before
    public void setUp() throws IOException {
        data = FileUtils.readFileToByte("E:/data/dooioo.jpg");
    }

    @Test
    public void testUpload() throws Exception {
        System.out.println(ImageUpload.upload(data, "dooioo.jpg",new ImageArgConvert(ImageSize.IMAGE_SIZE_500x375, Logo.Logo), new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75, Logo.Logo)));
    }

    @Test
    public void testScaleMultiHandle() throws UploadException {
    }
}
