package com.dooioo.commons.io;

import com.dooioo.commons.AssertUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 12-4-24
 * Time: 下午3:48
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class FileUtils {
    /**
     * 文件COPY
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        AssertUtils.isTrue(src != null && (src.isDirectory() || src.isFile()), "Source File must denote a directory or file");
		AssertUtils.notNull(dest, "Destination File must not be null");
		if (src.isDirectory()) {
			dest.mkdir();
			File[] entries = src.listFiles();
			if (entries == null) {
				throw new IOException("Could not list files in directory: " + src);
			}
			for (File entry : entries) {
				copyFile(entry, new File(dest, entry.getName()));
			}
		}
		else if (src.isFile()) {
			try {
				dest.createNewFile();
			}catch (IOException ex) {
				IOException ioex = new IOException("Failed to create file: " + dest);
				ioex.initCause(ex);
				throw ioex;
			}
			IoUtils.copy(src, dest);
		}
	}

    /**
     * 文件COPY
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile(String src, String dest) throws IOException {
        copyFile(new File(src),new File(dest));
	}

    /**
     * 字符数组写入到文件流中
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copy(byte[] in, File out) throws IOException {
		AssertUtils.notNull(in, "No input byte array specified");
		AssertUtils.notNull(out, "No output File specified");
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		IoUtils.copy(inStream, outStream);
	}

    /**
     * 从读取文件流转换成字节数组
     * @param in
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] readToByte(File in) throws IOException {
		AssertUtils.notNull(in, "No input File specified");
		return IoUtils.copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
	}

    /**
     * 从读取文件流转换成字节数组
     * @param fileName
     * @param charset
     * @return
     * @throws IOException
     */
    public static byte[] readToByte(String fileName,String charset) throws IOException {
		AssertUtils.notNull(fileName, "No input File specified");
        AssertUtils.notNull(charset, "charset must not be null.");
		return readToByte(new File(fileName,charset));
	}

    /**
     * 从读取文件流转换成字节数组
     * @param fileName 文件名
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] readToByte(String fileName) throws IOException {
		return readToByte(fileName,"UTF-8");
	}

    /**
     * 读取文件内容
     * @param source  源文件
     * @return 文件内容字符串
     * @throws IOException
     */
    public static String readToStr(String source) throws IOException{
        return readToStr(source,"UTF-8");
    }

    /**
     * 读取文件内容
     * @param source  源文件
     * @param charset 文件编码
     * @return 文件内容字符串
     * @throws IOException
     */
    public static String readToStr(String source,String charset) throws IOException{
        AssertUtils.notNull(source, "No input byte array specified");
        AssertUtils.notNull(charset, "charset must not be null.");
        return readFileToString(new File(source,charset));
    }

     /**
     * 读取文件内容
     * @param source  源文件
     * @return 文件内容字符串
     * @throws IOException
     */
    public static String readFileToString(File source) throws IOException{
        AssertUtils.notNull(source, "No input byte array specified");
        return IoUtils.copyToStr(new FileReader(source));
    }

    /**
     * 字符串写入文件
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void  writeFile(String fileName,String content) throws IOException{
        writeFile(fileName,"UTF-8",content,false);
    }

    /**
     * 字符串写入文件
     * @param fileName 文件名称
     * @param content   添加的内容
     * @param append    是否追加
     * @throws IOException
     */
    public static void  writeFile(String fileName,String content,boolean append) throws IOException{
        writeFile(fileName,"UTF-8",content,append);
    }

    /**
     * 字符串写入文件
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void  writeFile(String fileName,String charset,String content,boolean append) throws IOException {
         writeFile(new File(fileName,charset),content,append);
    }

    /**
     * 字符串写入文件
     * @param file
     * @param content
     * @throws IOException
     */
    public static void  writeFile(File file,String content,boolean append) throws IOException{
        AssertUtils.isTrue((file!=null && file.isFile()),"file must not be null and is a File");
        IoUtils.copy(content,new FileWriter(file,append));
    }

    /**
     * 删除根目录下的所有文件
     * @param root
     * @return
     */
    public static boolean deleteFiles(File root) {
		if (root != null && root.exists()) {
			if (root.isDirectory()) {
				File[] children = root.listFiles();
				if (children != null) {
					for (File child : children) {
						deleteFiles(child);
					}
				}
			}
			return root.delete();
		}
		return false;
	}

    /**
     * 删除根目录下的所有文件
     * @param root
     * @return
     */
    public static boolean deleteFiles(String root) {
		return deleteFiles(new File(root));
	}


    /**
     * 把文件内容转换成List数组，文件的每一行，是List一个对象
     * @param fileName 文件名称
     * @param charsert 文件读取编码方式
     * @return
     * @throws IOException
     */
    public static List<String> readToList(String fileName,String charsert) throws IOException{
       return  readLines(new File(fileName,charsert));
    }

    /**
     * 把文件内容转换成List数组，文件的每一行，是List一个对象
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> readLines(File file) throws IOException{
        AssertUtils.isTrue((file!=null && file.isFile()),"file must not be null and is a File");
        List<String> lines = new ArrayList<String>();
        FileReader fileReader= null ;
        BufferedReader br = null;
        try{
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);
            String line = null;
            while((line = br.readLine()) != null){
                lines.add(line);
            }
        }finally {
            br.close();
            fileReader.close();
        }
        return  lines;
    }
}
