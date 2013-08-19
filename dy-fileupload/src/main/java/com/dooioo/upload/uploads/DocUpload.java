package com.dooioo.upload.uploads;

import com.dooioo.upload.UploadResult;
import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午3:49.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public final class DocUpload{
    private static final Logger LOGGER = Logger.getLogger(DocUpload.class);

    /**
     * 上传文件
     */
    public static UploadResult upload(byte[] fileBytes , String origiFileName) throws UploadException {
        try {
            String path = FileUtils.createDatePath();
            String fileName = FileUtils.genrateFileName() + FileUtils.FILE_EXT + FileUtils.getFileExtName(origiFileName);

            FileUtils.existsAndCreate(UploadConfig.getInstance().getDocDirectory() + FileUtils.FILE_SEPARATOR + path);
            String targetFileName = UploadConfig.getInstance().getDocDirectory() + FileUtils.FILE_SEPARATOR + path + FileUtils.FILE_SEPARATOR + fileName;
            FileUtils.writeByteToFile(fileBytes,targetFileName);
            return new UploadResult().setOrigiName(origiFileName).setTargetName(UploadConfig.getInstance().getDocPath() + path + FileUtils.FILE_SEPARATOR + fileName);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new UploadException(e);
        }
    }

    /**
     * 上传文件
     */
    public static UploadResult write(FileItem fileItem) throws UploadException {
        try {
            String origiFileName = fileItem.getName();
            String path = FileUtils.createDatePath();
            String fileName = FileUtils.genrateFileName() + FileUtils.FILE_EXT + FileUtils.getFileExtName(origiFileName);
            FileUtils.existsAndCreate(UploadConfig.getInstance().getDocDirectory() + FileUtils.FILE_SEPARATOR + path);
            String targetFileName = UploadConfig.getInstance().getDocDirectory() + FileUtils.FILE_SEPARATOR + path + FileUtils.FILE_SEPARATOR + fileName;
            fileItem.write(new File(targetFileName));
            return new UploadResult().setOrigiName(origiFileName).setTargetName(UploadConfig.getInstance().getDocPath() + path + FileUtils.FILE_SEPARATOR + fileName);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new UploadException(e);
        }
    }

    /**
     * 上传文件
     */
    public static UploadResult upload(FileItem fileItem) throws UploadException {
        return upload(fileItem.get(), fileItem.getName());
    }
}
