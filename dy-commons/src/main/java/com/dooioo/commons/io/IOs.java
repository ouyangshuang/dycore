package com.dooioo.commons.io;

import com.dooioo.commons.AssertUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * IO 的帮助类
 * User: kuang
 * Date: 12-8-13
 * Time: 下午2:35
 */
public class IOs extends IOUtils {

    private static int BUFFER_SIZE = 4096;

    /**
     * 把字节输入流写入到字节输出流。
     * @param in 输入流
     * @param out   输出流
     * @return 写入流字节大小
     * @throws java.io.IOException
     */
    public static int copy(InputStream in, OutputStream out) throws IOException {
        AssertUtils.notNull(in, "No InputStream specified");
        AssertUtils.notNull(out, "No OutputStream specified");
        try{
            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer,0,bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        }finally {
            try{in.close();}catch (Exception e){}
            try{out.close();}catch (Exception e){}
        }
    }

    /**
     * 字节输入流读取成字节数组.
     * @param in 字节输入流
     * @return  字节数组
     * @throws java.io.IOException
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in,out);
        return  out.toByteArray();
    }

    /**
     * 把字节数组写入字节输出流.
     * @param in    输入字节数组
     * @param out   字节输出流
     * @throws IOException
     */
    public static void  copy(byte[] in , OutputStream out) throws IOException{
        AssertUtils.notNull(in, "No InputStream specified");
        AssertUtils.notNull(out, "No OutputStream specified");
        try{
            out.write(in);
        }finally {
            out.close();
        }
    }

    /**
     * 把输入字符流写入输出字符流
     * @param in 字符输入流
     * @param out 字符输出流
     * @return 写入字符流的大小
     * @throws IOException
     */
    public static int copy(Reader in,Writer out) throws  IOException{
        AssertUtils.notNull(in, "No Reader specified");
        AssertUtils.notNull(out, "No Writer specified");
        try{
            int byteCount = 0;
            char[] buffer = new char[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer,0,bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        }finally {
            try{in.close();}catch (Exception e){}
            try{out.close();}catch (Exception e){}
        }
    }

    /**
     * 读取输入字符流，并转换成字符串输出
     * @param in 字符输入流
     * @return 输出字符串
     * @throws IOException
     */
    public static String copyToStr(Reader in) throws IOException{
        StringWriter out =new StringWriter();
        copy(in,out);
        return out.toString();
    }

    /**
     * 把字符写入到字符输出流
     * @param in 输入字符串
     * @return 输出流
     * @throws IOException
     */
    public static void copy(String in , Writer out) throws IOException{
        AssertUtils.notNull(in, "No Reader specified");
        AssertUtils.notNull(out, "No Writer specified");
        try{
            out.write(in);
            out.flush();
        }finally {
            out.close();
        }
    }

    /**
     * 单个文件copy
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static int copy(File in, File out) throws IOException {
        AssertUtils.notNull(in, "No input File specified");
        AssertUtils.notNull(out, "No output File specified");
        return copy(new BufferedInputStream(new FileInputStream(in)),new BufferedOutputStream(new FileOutputStream(out)));
    }
}
