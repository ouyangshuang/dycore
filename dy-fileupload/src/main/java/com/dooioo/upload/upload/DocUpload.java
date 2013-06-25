package com.dooioo.upload.upload;

import com.dooioo.commons.Dates;
import com.dooioo.commons.Randoms;
import com.dooioo.upload.Upload;
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
    private static final String DATE_STYLE  = "yyyyMMdd";
    private static final String FILE_SEPARATOR = File.separator;
    private static final String FILE_EXT = ".";

    /**
     * 上传文件
     */
    public static Upload upload(byte[] fileBytes , String origiFileName) throws UploadException {
        try {
            FileUtils.existsAndCreate(UploadConfig.getInstance().getDocDirectory() + File.separator + Dates.getDateTime(DATE_STYLE) + FILE_SEPARATOR);
            String targetFileName = UploadConfig.getInstance().getDocDirectory() + File.separator + Dates.getDateTime(DATE_STYLE) + FILE_SEPARATOR + Randoms.getPrimaryKey() + FILE_EXT + FileUtils.getFileExtName(origiFileName);
            FileUtils.writeByteToFile(fileBytes,targetFileName);
            return new Upload().setOrigiName(origiFileName).setTargetName(targetFileName);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new UploadException(e);
        }
    }

    /**
     * 上传文件
     */
    public static Upload upload(FileItem fileItem) throws UploadException {
        return upload(fileItem.get(), fileItem.getName());
    }
}
