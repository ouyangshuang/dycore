package com.dooioo.upload.exception;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午3:22.
 *
 * @author 焦义贵
 * @since 1.0
 * 图片上传异常
 */
public class UploadException extends Exception{
    public UploadException() {
    }

    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadException(Throwable cause) {
        super(cause);
    }
}
