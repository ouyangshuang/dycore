package com.dooioo.upload.utils;

import com.dooioo.commons.Strings;
import com.dooioo.commons.io.IoUtils;
import com.dooioo.upload.exception.UploadException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午3:28.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class FileUtils {
    /**
     * 获取文件扩展名
     * @return 文件扩展名
     */
    public static String getFileExtName(String fileName) throws UploadException {
        if(Strings.isEmpty(fileName)){
            throw new UploadException("Utils -> getFileExtName [" + fileName + "] 参数错误");
        }
        int idx = fileName.lastIndexOf(".");
        if(idx == -1){
            throw new UploadException("Utils -> getFileExtName [" + fileName + "] 参数错误");
        }
        return fileName.substring(idx + 1);
    }

    /**
     * 检查文件夹是否存在
     */
    public static void existsAndCreate(String path){
        if(new File(path).exists()) {
            return;
        }
        new File(path).mkdirs();
    }

    /**
     * 字节流写入文件
     * @param fileBytes
     * @param filePath
     * @throws IOException
     */
    public static void writeByteToFile(byte[] fileBytes,String filePath) throws IOException {
        FileChannel channel = new FileOutputStream(filePath).getChannel();
        channel.write(ByteBuffer.wrap(fileBytes));
        channel.close();
    }

    /**
     * 文件读取成字节流
     */
    public static byte[] readFileToByte(String path) throws IOException {
        return IoUtils.copyToByteArray(new FileInputStream(path));
    }
}
