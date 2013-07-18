package com.dooioo.upload.uploads;

import com.dooioo.upload.UploadResult;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.image.factory.ImageFactory;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import org.apache.commons.fileupload.FileItem;

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
    /**
     * 上传原图
     *
     * @throws Exception
     */
    public static UploadResult upload(byte[] data , String origiFileName , ImageArgConvert... imageArgConverts) throws Exception {
        String path =  FileUtils.createDatePath();
        String fileName = FileUtils.genrateFileName() + FileUtils.FILE_EXT + FileUtils.getFileExtName(origiFileName);
        FileUtils.existsAndCreate(UploadConfig.getInstance().getOriginalDirectory() +FileUtils. FILE_SEPARATOR + path + FileUtils.FILE_SEPARATOR );
        String targetFileName = path + FileUtils.FILE_SEPARATOR + fileName;

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
        UploadResult upload = ImageFactory.newInstance().upload(data, UploadConfig.getInstance().getOriginalDirectory() + FileUtils.FILE_SEPARATOR + targetFileName);
        upload.setOrigiName(origiFileName).setTargetName(targetFileName);

        //同步生成
        if(syncImageArgConvert.size() > 0){
            scaleMultiHandle(upload.getTargetName()  ,syncImageArgConvert);
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
    public static UploadResult upload(FileItem fileItem) throws Exception {
       return upload(fileItem.get(), fileItem.getName());
    }

    /**
     * 生成多张缩略图                                         `
     * @param fileName
     * @param imageArgConverts
     */
    public static void scaleMultiHandle(String fileName ,List<ImageArgConvert> imageArgConverts) throws Exception {
        ImageFactory.newInstance().generatesImageHandle(fileName  ,imageArgConverts);
    }
}
