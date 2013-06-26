package com.dooioo.upload.upload;

import com.dooioo.commons.Dates;
import com.dooioo.commons.Randoms;
import com.dooioo.upload.Upload;
import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
* Created with IntelliJ IDEA at 13-6-24 下午3:56.
*
* @author 焦义贵
* @since 1.0
* TODO
*/
public class ZipFileUpload {
    private static final Logger LOGGER = Logger.getLogger(DocUpload.class);
    private static final String DATE_STYLE  = "yyyyMMdd";
    private static final String FILE_SEPARATOR = File.separator;
    private static final String FILE_EXT = ".";

    /**
     * 上传ZIP文件 ,并解压
     * @param data
     * @param origiFileName
     * @return
     * @throws Exception
     */
    public static Upload upload(byte[] data , String origiFileName,boolean isunzip) throws UploadException {
        if(!origiFileName.toLowerCase().endsWith("zip") || !origiFileName.toLowerCase().endsWith("rar")){
            throw new UploadException("请上传ZIP文件");
        }
        try {
            //上传ZIP
            String targetFilePath = UploadConfig.getInstance().getFlashDirectory() + File.separator + Dates.getDateTime(DATE_STYLE) + FILE_SEPARATOR ;
            FileUtils.existsAndCreate(targetFilePath);
            String  targetPath =   Randoms.getPrimaryKey();
            String  targetFileName =   targetFilePath + targetPath + FILE_EXT +  FileUtils.getFileExtName(origiFileName);
            FileUtils.writeByteToFile(data,targetFileName);
            Upload upload = new Upload().setOrigiName(origiFileName).setTargetName(targetFileName);
            if(!isunzip){
                return upload;
            }

            //解压ZIP
            //定义返回路径
            FileInputStream fs =null;
            ZipInputStream zis=null;
            ZipEntry ze;
            String _path =  targetFilePath + targetPath + "/";
            String newFile = targetFilePath + targetPath +  "/";
            try{
                fs	= new FileInputStream(targetFileName);
                zis = new ZipInputStream(new BufferedInputStream(fs));
                while ((ze = zis.getNextEntry()) != null) {
                    int count;
                    byte[] buffer = new byte[2048];
                    if (ze.isDirectory()) {
                        continue;
                    }
                    //获取文件名字
                    String fileName = ze.getName();
                    BufferedOutputStream bos = null;

                    try{
                        bos = new BufferedOutputStream(new FileOutputStream(getRealFileName(newFile, fileName)));
                        //如果为.htm 结尾的 代表是入口
                        if(fileName.endsWith(".htm") && upload.getHtmPath() == null){
                            upload.setHtmPath(_path + fileName);
                        }else if(fileName.indexOf("index.htm") != -1){
                            upload.setHtmPath(_path + fileName);
                        }else if(fileName.endsWith(".jpg") || fileName.endsWith(".icon")){
                            upload.setIconPath(_path + fileName);
                        }else if(fileName.endsWith("_2.jpg")){
                            upload.setIconPath(_path + fileName);
                        }
                        while ((count = zis.read(data, 0, 2048)) != -1) {
                            bos.write(data, 0, count);
                        }
                        bos.flush();
                    }finally{
                        if(bos!=null){
                            bos.close();
                        }
                    }
                }
            }catch(IOException e){
               throw new UploadException(e);
            }finally{
                try{
                    if(zis!=null){
                        zis.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                if(fs!=null){
                    fs.close();
                }
            }

            return upload;
        } catch (Exception e) {
            LOGGER.error(e);
            throw  new UploadException(e);
        }
    }

    /**
     * 上传文件
     */
    public static Upload upload(FileItem fileItem ,boolean isunzip) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(),isunzip);
    }

    private static File getRealFileName(String path1, String path2) {
        String[] dirs = path2.split("/", path2.length());
        File file = new File(path1);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (dirs.length > 0) {
            for (int i = 0; i < dirs.length - 1; i++) {
                file = new File(file, dirs[i]);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            file = new File(file, dirs[dirs.length - 1]);
        }
        return file;
    }
}
