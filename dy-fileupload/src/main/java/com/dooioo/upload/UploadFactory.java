package com.dooioo.upload;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.uploads.DocUpload;
import com.dooioo.upload.uploads.ImageUpload;
import com.dooioo.upload.uploads.RecordUpload;
import com.dooioo.upload.uploads.ZipFileUpload;
import com.dooioo.upload.utils.FileUtils;
import org.apache.commons.fileupload.FileItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA at 13-6-25 上午10:28.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class UploadFactory {
//    private static Set<String> IMAGE_TYPE = new HashSet<String>();
    private static Set<String> RECORD_TYPE = new HashSet<String>();
    private static Set<String> ZIP_TYPE = new HashSet<String>();

    static {
        //图片类型
//        IMAGE_TYPE.add("png");
//        IMAGE_TYPE.add("gif");
//        IMAGE_TYPE.add("jpg");
//        IMAGE_TYPE.add("jpeg");
//        IMAGE_TYPE.add("bmp");
//        IMAGE_TYPE.add("tiff");
//        IMAGE_TYPE.add("icon");

        //录音文件
        RECORD_TYPE.add("wav");

        //压缩文件
        ZIP_TYPE.add("zip");
        ZIP_TYPE.add("rar");
    }
//
//    /**
//     * 上传Doc文件
//     *
//     * @param fileItem
//     * @param company
//     * @return
//     * @throws UploadException
//     */
//    public static UploadResult upload(FileItem fileItem, Company company) throws UploadException {
//        return upload(fileItem.get(), fileItem.getName(), company);
//    }
//
//    /**
//     * 上传非ZIP、RAR、图片、录音等其他文件
//     *
//     * @param data
//     * @param fileName
//     * @param company
//     * @return
//     * @throws UploadException
//     */
//    public static UploadResult upload(byte[] data, String fileName, Company company) throws UploadException {
//        return upload(data, fileName, company, false);
//    }
//
//    /**
//     * 上传图片
//     *
//     * @param fileItem
//     * @param company
//     * @return
//     * @throws UploadException
//     */
//    public static UploadResult upload(FileItem fileItem, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
//        return upload(fileItem.get(), fileItem.getName(), company, imageArgConverts);
//    }
//
//    /**
//     * 上传图片文件
//     * //TODO:目前只支持同步压缩 异步调用
//     *
//     * @param data             图片文件流
//     * @param fileName         图片原名称
//     * @param company          公司 : 用于区分是德融、德佑水印
//     * @param imageArgConverts 缩略图尺寸规格
//     * @return
//     * @throws UploadException
//     */
//    public static UploadResult upload(byte[] data, String fileName, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
//        return upload(data, fileName, company, false, imageArgConverts);
//    }
//
//    /**
//     * @param fileItem 上传Zip、rar文件流
//     * @param company  公司
//     * @param isunzip  是否解压
//     * @return
//     * @throws UploadException
//     */
//    public static UploadResult upload(FileItem fileItem, Company company, boolean isunzip) throws UploadException {
//        return upload(fileItem.get(), fileItem.getName(), company, isunzip);
//    }
//
//    /**
//     * @param data     上传Zip、rar字节流
//     * @param fileName 上传源文件的名称
//     * @param company  公司
//     * @param isunzip  是否解压
//     * @return
//     * @throws UploadException
//     */
//    public static UploadResult upload(byte[] data, String fileName, Company company, boolean isunzip) throws UploadException {
//        return upload(data, fileName, company, isunzip, new ImageArgConvert[0]);
//    }
//
//    /**
//     * 文件上传
//     *
//     * @param data             上传文件字节流
//     * @param fileName         上传文件名称
//     * @param isunzip          zip是否需要解压
//     * @param imageArgConverts //图片上传时，图片尺寸图
//     * @return
//     * @throws UploadException
//     */
//    private static UploadResult upload(byte[] data, String fileName, Company company, boolean isunzip, ImageArgConvert... imageArgConverts) throws UploadException {
//        String extName = FileUtils.getFileExtName(fileName).toLowerCase();
//        //图片处理
//        if (IMAGE_TYPE.contains(extName)) {
//            return ImageUpload.upload(data, fileName, company, imageArgConverts);
//        }
//        if (RECORD_TYPE.contains(extName)) {
//            return RecordUpload.upload(data, fileName);
//        }
//        if (ZIP_TYPE.contains(extName)) {
//            return ZipFileUpload.upload(data, fileName, isunzip);
//        }
//        return DocUpload.upload(data, fileName);
//    }
//
//    public static UploadResult uploadDoc(FileItem fileItem,boolean isunzip){
//        if (ZIP_TYPE.contains(extName)) {
//            return ZipFileUpload.upload(data, fileName, isunzip);
//        }
//        return DocUpload.upload(data, fileName);
//    }
//
//    public static UploadResult uploadDoc(FileItem fileItem,boolean isunzip){
//        if (ZIP_TYPE.contains(extName)) {
//            return ZipFileUpload.upload(data, fileName, isunzip);
//        }
//        return DocUpload.upload(data, fileName);
//    }
//
//

    /**
     * 上传图片
     * //TODO:目前只支持同步压缩 异步调用
     * @param fileItem
     * @param company
     * @param imageArgConverts
     * @return
     * @throws UploadException
     */
    public static UploadResult uploadPic(FileItem fileItem, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
        return uploadPic(fileItem.get(), fileItem.getName(), company, imageArgConverts);
    }

    /**
     * 上传图片
     * //TODO:目前只支持同步压缩 异步调用未实现
     * @param data
     * @param company
     * @param imageArgConverts
     * @return
     * @throws UploadException
     */
    public static UploadResult uploadPic(byte[] data, String fileName, Company company, ImageArgConvert... imageArgConverts) throws UploadException {
        return ImageUpload.upload(data, fileName, company, imageArgConverts);
    }

    /**
     * 上传文档文件 eg:wav.doc
     * @param fileItem
     * @param isunzip 如果是zip文件是否需要解压
     * @return
     * @throws UploadException
     */
    public static UploadResult upload(FileItem fileItem,boolean isunzip) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(),isunzip);
    }

    /**
     * 上传文档文件 eg:wav.doc
     * @param data
     * @param fileName
     * @param isunzip  如果是zip文件是否需要解压
     * @return
     * @throws UploadException
     */
    public static UploadResult upload(byte[] data, String fileName, boolean isunzip) throws UploadException {
        String extName = FileUtils.getFileExtName(fileName).toLowerCase();
        if (RECORD_TYPE.contains(extName)) {
            return RecordUpload.upload(data, fileName);
        }
        if (ZIP_TYPE.contains(extName)) {
            return ZipFileUpload.upload(data, fileName, isunzip);
        }
        return DocUpload.upload(data, fileName);
    }

    /**
     * 异步生成缩略图
     *
     * @param picPath          图片路径
     * @param company          公司【水印区分】
     * @param imageArgConverts 缩略图尺寸
     * @throws UploadException
     */
    public static void asyncGeneratePics(String picPath, Company company, List<ImageArgConvert> imageArgConverts) throws UploadException {
        if (imageArgConverts == null || imageArgConverts.size() == 0)
            return;
        ImageUpload.scaleMultiHandle(picPath, company, imageArgConverts);
    }
}
