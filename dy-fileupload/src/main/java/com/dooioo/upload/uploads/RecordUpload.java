package com.dooioo.upload.uploads;

import com.dooioo.upload.UploadResult;
import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午3:54.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class RecordUpload {
    private static final Logger LOGGER = Logger.getLogger(DocUpload.class);

    /**
     * 上传文件
     */
    public static UploadResult upload(byte[] fileBytes , String origiFileName) throws UploadException {
        try {
            String datePath =  FileUtils.createDatePath();
            String recordName= FileUtils.genrateFileName() + FileUtils.FILE_EXT + FileUtils.getFileExtName(origiFileName);

            FileUtils.existsAndCreate( UploadConfig.getInstance().getRecordDirectory() + FileUtils.FILE_SEPARATOR + datePath + FileUtils.FILE_SEPARATOR );
            String targetFileName = UploadConfig.getInstance().getRecordDirectory() + FileUtils.FILE_SEPARATOR + datePath + FileUtils.FILE_SEPARATOR + recordName;
            FileUtils.writeByteToFile(fileBytes,targetFileName);
            return new UploadResult().setOrigiName(origiFileName).setTargetName(UploadConfig.getInstance().getRecordPath() + datePath + FileUtils.FILE_SEPARATOR + recordName);
        } catch (Exception e) {
            LOGGER.error(e);
            throw  new UploadException(e);
        }
    }

    /**
     * 上传文件
     */
    public static UploadResult upload(FileItem fileItem) throws UploadException {
        try {
            String origiFileName = fileItem.getName();
            String datePath =  FileUtils.createDatePath();
            String recordName= FileUtils.genrateFileName() + FileUtils.FILE_EXT + FileUtils.getFileExtName(origiFileName);
            FileUtils.existsAndCreate( UploadConfig.getInstance().getRecordDirectory() + FileUtils.FILE_SEPARATOR + datePath + FileUtils.FILE_SEPARATOR );
            String targetFileName = UploadConfig.getInstance().getRecordDirectory() + FileUtils.FILE_SEPARATOR + datePath + FileUtils.FILE_SEPARATOR + recordName;
            fileItem.write(new File(targetFileName));
            return new UploadResult().setOrigiName(origiFileName).setTargetName(UploadConfig.getInstance().getRecordPath() + datePath + FileUtils.FILE_SEPARATOR + recordName);
        } catch (Exception e) {
            LOGGER.error(e);
            throw  new UploadException(e);
        }
    }
}
