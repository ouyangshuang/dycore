package com.dooioo.upload.image.factory;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.UploadResult;
import com.dooioo.upload.image.ImageSize;
import com.dooioo.upload.image.Logo;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import magick.MagickException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午1:45.
 *
 * @author 焦义贵
 * @since 1.0
 *        图片接口
 */
public abstract class AbstractImage {
    protected static final Logger LOGGER = Logger.getLogger(AbstractImage.class);
    /**
     * 文件路径分隔符
     */
    protected static final String FILE_SEPARATOR = FileUtils.FILE_SEPARATOR;
    /**
     * 上传图片根目录
     */
    protected static final String originalDirectory = UploadConfig.getInstance().getOriginalDirectory();
    /**
     * 压缩后，图片根目录
     */
    protected static final String tragetDirectory = UploadConfig.getInstance().getTargetDirectory();

    /**
     * 生成单张缩略图
     *
     * @param fileName        图片路径
     * @param imageArgConvert 生成规格
     */
    public abstract void generatesImageHandle(String fileName, ImageArgConvert imageArgConvert) throws Exception;

    /**
     * 生成多张缩略图
     *
     * @param fileName         图片路径
     * @param imageArgConverts 生成规格
     */
    public abstract void generatesImageHandle(String fileName, List<ImageArgConvert> imageArgConverts) throws Exception;

    /**
     * 生成原图
     *
     * @param data     图片字节流
     * @param savePath 保存路径
     * @param
     */
    public abstract UploadResult upload(byte[] data, String savePath) throws UploadException, MagickException, IOException, FileUploadException;

    public abstract UploadResult write(FileItem fileItem, String savePath) throws MagickException, FileUploadException;

    /**
     * 图片等比例缩放
     *
     * @param sourceImageSize
     * @param targetImageSize
     * @return
     */
    protected ImageSize scaleSize(ImageSize sourceImageSize, ImageSize targetImageSize) {
        ImageSize scaleImageSize = new ImageSize();

        if (isUseOriginal(sourceImageSize, targetImageSize)) {
            scaleImageSize.setHeight(sourceImageSize.getHeight());
            scaleImageSize.setWidth(sourceImageSize.getWidth());
            return scaleImageSize;
        }

        if (compareScaleSize(sourceImageSize, targetImageSize)) {
            scaleImageSize.setHeight(targetImageSize.getHeight());
            scaleImageSize.setWidth((sourceImageSize.getWidth() * targetImageSize.getHeight()) / sourceImageSize.getHeight());
            scaleImageSize.setX((scaleImageSize.getWidth() - targetImageSize.getWidth()) / 2);

            if(targetImageSize.getWidth() > 300 && scaleImageSize.getWidth() > targetImageSize.getWidth()) {
                scaleImageSize.setWidth(targetImageSize.getWidth());
                scaleImageSize.setHeight((int) ((scaleImageSize.getWidth() * sourceImageSize.getHeight()) / sourceImageSize.getWidth()));
            }
        } else {
            scaleImageSize.setWidth(targetImageSize.getWidth());
            scaleImageSize.setHeight((targetImageSize.getWidth() * sourceImageSize.getHeight()) / sourceImageSize.getWidth());
            scaleImageSize.setY((scaleImageSize.getHeight() - targetImageSize.getHeight()) / 2);

            if( targetImageSize.getHeight() > 225 &&  scaleImageSize.getHeight() > targetImageSize.getHeight()) {
                scaleImageSize.setHeight(targetImageSize.getHeight());
                scaleImageSize.setWidth((int) (scaleImageSize.getHeight() * sourceImageSize.getWidth() / sourceImageSize.getHeight()));
            }
        }
        return scaleImageSize;
    }

    /**
     * 判断是否使用原图尺寸
     * -1x-1规格图片
     * 0x0规格图片
     * 原图高小于目标图片高且原图宽小于目标图片宽
     *
     * @return
     */
    private boolean isUseOriginal(ImageSize sourceImageSize, ImageSize targetImageSize) {
        return targetImageSize.getHeight() == -1 || targetImageSize.getWidth() == -1
                || targetImageSize.getHeight() == 0 || targetImageSize.getWidth() == 0
                || (targetImageSize.getHeight() >= sourceImageSize.getHeight()
                && targetImageSize.getWidth() >= sourceImageSize.getWidth());
    }

    /**
     * 获取图片文件的扩展名
     *
     * @param size
     * @return
     * @throws StringIndexOutOfBoundsException
     *
     */
    protected static String generalImageExtName(ImageSize size, String fileName) throws UploadException {
        if (size.getHeight() == -1 || size.getWidth() == -1 || fileName.length() == 0) {
            return fileName;
        }
        return fileName + "_" + size.getWidth() + "x" + size.getHeight() + "." + FileUtils.getFileExtName(fileName);
    }

    /**
     * 根据图片与目标图片的长宽对比度得出最终图片尺寸的一切相应参数
     *
     * @return 判断是否是宽比例缩放
     */
    private boolean compareScaleSize(ImageSize sourceSize, ImageSize targetSize) {
        return sourceSize.getWidth() / targetSize.getWidth() >= sourceSize.getHeight() / targetSize.getHeight();
    }

    /**
     * 图片是否需要打水印
     *
     * @param imageArgConvert
     * @return
     */
    protected boolean hasWaterMaker(ImageArgConvert imageArgConvert) {
        return imageArgConvert.getLogo() != Logo.None;
    }
}
