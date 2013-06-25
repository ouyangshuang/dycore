package com.dooioo.upload;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.upload.DocUpload;
import com.dooioo.upload.upload.ImageUpload;
import com.dooioo.upload.upload.RecordUpload;
import com.dooioo.upload.upload.ZipFileUpload;
import com.dooioo.upload.utils.FileUtils;
import org.apache.commons.fileupload.FileItem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA at 13-6-25 上午10:28.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class UploadFactory {
    private static Set<String> IMAGE_TYPE = new HashSet<String>();
    private static Set<String> RECORD_TYPE = new HashSet<String>();
    private static Set<String> ZIP_TYPE = new HashSet<String>();

    static {
        //图片类型
        IMAGE_TYPE.add("png");
        IMAGE_TYPE.add("gif");
        IMAGE_TYPE.add("jpg");
        IMAGE_TYPE.add("jpeg");
        IMAGE_TYPE.add("bmp");
        IMAGE_TYPE.add("tiff");
        IMAGE_TYPE.add("icon");

        //录音文件
        RECORD_TYPE.add("wav");

        //压缩文件
        ZIP_TYPE.add("zip");
        ZIP_TYPE.add("rar");
    }

    public static Upload upload(FileItem fileItem, Company company) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(), company);
    }

    public static Upload upload(byte[] data, String fileName, Company company) throws UploadException {
        return upload(data, fileName, false , company);
    }

    public static Upload upload(FileItem fileItem, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(), company, imageArgConverts);
    }

    public static Upload upload(byte[] data, String fileName, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
        return upload(data, fileName, false ,company, imageArgConverts);
    }

    public static Upload upload(FileItem fileItem, boolean isunzip , Company company) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(), isunzip ,company);
    }

    public static Upload upload(byte[] data, String fileName, Company company, boolean isunzip) throws UploadException {
        return upload(data, fileName, isunzip, company, new ImageArgConvert[0]);
    }

    /**
     * 文件上传
     *
     * @param fileItem         上传文件流
     * @param isunzip          zip是否需要解压
     * @param imageArgConverts //图片上传时，图片尺寸图
     * @return
     * @throws UploadException
     */
    private static Upload upload(FileItem fileItem, boolean isunzip, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(), isunzip, company, imageArgConverts);
    }

    /**
     * 文件上传
     *
     * @param data             上传文件字节流
     * @param fileName         上传文件名称
     * @param isunzip          zip是否需要解压
     * @param imageArgConverts //图片上传时，图片尺寸图
     * @return
     * @throws UploadException
     */
    private static Upload upload(byte[] data, String fileName, boolean isunzip, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
        String extName = FileUtils.getFileExtName(fileName);
        //图片处理
        if (IMAGE_TYPE.contains(extName)) {
            return ImageUpload.upload(data, fileName , company, imageArgConverts);
        }
        if (RECORD_TYPE.contains(extName)) {
            return RecordUpload.upload(data, fileName);
        }
        if (ZIP_TYPE.contains(extName)) {
            return ZipFileUpload.upload(data, fileName, isunzip);
        }
        return DocUpload.upload(data, fileName);
    }
}
