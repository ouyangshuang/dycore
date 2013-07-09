package com.dooioo.upload.uploads;

import com.dooioo.commons.Dates;
import com.dooioo.commons.Randoms;
import com.dooioo.upload.Company;
import com.dooioo.upload.UploadResult;
import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.image.factory.ImageFactory;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA at 13-6-20 上午11:46.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public final class ImageUpload{
    private static final Logger LOGGER = Logger.getLogger(ImageUpload.class);
    private static final String DATE_STYLE  = "yyyyMMdd";
    private static final String FILE_SEPARATOR = "/";
    private static final String FILE_EXT = ".";

    /**
     * 上传原图
     *
     * @throws Exception
     */
    public static UploadResult upload(byte[] data , String origiFileName , Company company, ImageArgConvert... imageArgConverts) throws Exception {
        String path =  Dates.getDateTime(DATE_STYLE);
        String fileName = Randoms.getPrimaryKey() + FILE_EXT + FileUtils.getFileExtName(origiFileName);
        FileUtils.existsAndCreate(UploadConfig.getInstance().getOriginalDirectory() + FILE_SEPARATOR + path + FILE_SEPARATOR );
        String targetFileName = path + FILE_SEPARATOR + fileName;
        // 同步
        List<ImageArgConvert> syncImageArgConvert = new ArrayList<ImageArgConvert>();
        //异步
        List<ImageArgConvert> asyncImageArgConvert = new ArrayList<ImageArgConvert>();
        if(imageArgConverts != null && imageArgConverts.length > 0){
            for(ImageArgConvert imageArgConvert : imageArgConverts){
                if(imageArgConvert.isAsync()){
                    asyncImageArgConvert.add(imageArgConvert);
                }else{
                    syncImageArgConvert.add(imageArgConvert);
                }
            }
        }

        //先存储原图
        UploadResult upload = ImageFactory.newInstance().upload(data, UploadConfig.getInstance().getOriginalDirectory() + FILE_SEPARATOR + targetFileName);
        upload.setOrigiName(origiFileName).setTargetName(targetFileName);

        //同步生成
        if(syncImageArgConvert.size() > 0){
            scaleMultiHandle(upload.getTargetName() , company ,syncImageArgConvert);
        }
        //异步生成
        if(asyncImageArgConvert.size() > 0){
            //TODO:
        }
        return upload;
    }

    /**
     * 上传原图
     *
     * @throws Exception
     */
    public static UploadResult upload(FileItem fileItem, Company company) throws Exception {
       return upload(fileItem.get(), fileItem.getName(),company);
    }

    /**
     * 生成多张缩略图                                         `
     * @param fileName
     * @param imageArgConverts
     */
    public static void scaleMultiHandle(String fileName , Company company,List<ImageArgConvert> imageArgConverts) throws Exception {
        ImageFactory.newInstance().generatesImageHandle(fileName ,company ,imageArgConverts);
    }
}
