package com.dooioo.upload;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.image.ImageSize;
import com.dooioo.upload.image.Logo;
import com.dooioo.upload.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA at 13-6-26 下午3:15.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class UploadFactoryTest {
    private byte[] picdata;
    private byte[] docdata;
    private byte[] recorddata;
    private byte[] rardata;

    @Before
    public void setup() throws IOException {
        picdata = FileUtils.readFileToByte("E:/data/dooioo.jpg");
        docdata = FileUtils.readFileToByte("E:/data/QQ空间.apk");
        recorddata =  FileUtils.readFileToByte("E:/data/dooioo.wav");
        rardata =  FileUtils.readFileToByte("E:/data/dooioo.zip");
    }

    @Test
    public void testPicUpload() throws Exception {
        System.out.println(UploadFactory.uploadPic(picdata,"dooioo.jpg",new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75)));
        System.out.println(UploadFactory.uploadPic(picdata,"dooioo.jpg",new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75)));
        System.out.println(UploadFactory.uploadPic(picdata,"dooioo.jpg",new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75)));
        System.out.println(UploadFactory.uploadPic(picdata,"dooioo.jpg",new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75)));
        System.out.println(UploadFactory.uploadPic(picdata,"dooioo.jpg",new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75)));
    }

    @Test
    public void testDocUpload() throws UploadException {
        System.out.println(UploadFactory.upload(docdata,"QQ空间.apk"));
    }

    @Test
    public void testRecordUpload() throws UploadException {
        System.out.println(UploadFactory.upload(recorddata,"dooioo.wav"));
    }

    @Test
    public void testRarUpload() throws UploadException {
        System.out.println("不解压:" + UploadFactory.uploadZip(rardata, "dooioo.zip", false));

        System.out.println("解压:" + UploadFactory.uploadZip(rardata, "dooioo.zip", true));
    }

    @Test
    public void  testGenPics() throws Exception {
        List<ImageArgConvert>  imageArgConverts = new ArrayList<ImageArgConvert>();
        imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_400x300, Logo.Logo));
        imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_300x225, Logo.Logo));
        imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_500x375, Logo.Logo));
        imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_900x675, Logo.Logo));
        imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75, Logo.Logo));
        UploadFactory.asyncGeneratePics("2013/0718/D1A8EA418FFC49AB8DC1FF5A424E8C59.jpg",imageArgConverts);
    }

    @Test
    public void testUUID(){
        System.out.println("UUID:"+ UUID.randomUUID());
    }
}
