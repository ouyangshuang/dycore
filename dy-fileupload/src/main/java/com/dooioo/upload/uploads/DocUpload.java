package com.dooioo.upload.uploads;

import com.dooioo.commons.Dates;
import com.dooioo.commons.Randoms;
import com.dooioo.upload.UploadResult;
import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

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
    private static final String FILE_SEPARATOR = "/";
    private static final String FILE_EXT = ".";

    /**
     * 上传文件
     */
    public static UploadResult upload(byte[] fileBytes , String origiFileName) throws UploadException {
        try {
            String path = Dates.getDateTime(DATE_STYLE);
            String fileName = Randoms.getPrimaryKey() + FILE_EXT + FileUtils.getFileExtName(origiFileName);
            FileUtils.existsAndCreate(UploadConfig.getInstance().getDocDirectory() + FILE_SEPARATOR + path);
            String targetFileName = UploadConfig.getInstance().getDocDirectory() + FILE_SEPARATOR + path + FILE_SEPARATOR + fileName;
            FileUtils.writeByteToFile(fileBytes,targetFileName);
            return new UploadResult().setOrigiName(origiFileName).setTargetName(UploadConfig.getInstance().getDocPath() + path + FILE_SEPARATOR + fileName);
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
