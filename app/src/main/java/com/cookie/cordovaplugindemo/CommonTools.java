package com.cookie.cordovaplugindemo;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 2018-6-13
 *
 * @author Shicx
 */

public class CommonTools {

    private static final int BUFF_SIZE = 1024;

    /**
     * 获取文件的MD5码
     *
     * @param file 要获取MD5码的文件
     * @return
     * @throws IOException
     */
    public static String getFileMd5Code(File file) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            md.update(byteBuffer);
            return bufferToHex(md.digest());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 算出字节数组的MD5码
     *
     * @param bytes
     * @return
     */
    private static String bufferToHex(byte bytes[]) {
        int m = 0, n = bytes.length;
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * @param bt
     * @param stringbuffer
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }


    /**
     * 递归删除文件
     *
     * @param dir
     * @return
     */
    public static void deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    deleteDir(new File(dir, children[i]));
                }
            }

        }
        if(dir != null){
            dir.delete();
        }
    }

    /**
     * 删除指定的文件
     * @param dir
     * @param remainDays 当前时间-文件修改时间>remainDays(天数)，则删除
     * @return
     */
    public static void deleteDir(File dir,int remainDays) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    deleteDir(new File(dir, children[i]),remainDays);
                }
            }
        }
        if(dir != null){
            long _days = (System.currentTimeMillis() - dir.lastModified())/1000/60/60/24;
            if(_days>remainDays){
                dir.delete();
            }
        }
    }

    /**
     * 获取文件的大小(包括所有子文件)
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] fileList = file.listFiles();
                    if (fileList != null) {
                        for (File subFile : fileList) {
                            // 如果下面还有文件
                            if (subFile.isDirectory()) {
                                size = size + getFolderSize(subFile);
                            } else {
                                size = size + subFile.length();
                            }
                        }
                    }
                } else {
                    size = size + file.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 拷贝文件
     */
    public static void copyFile(File fileIn, File fileOut) {
        try {
            if(fileIn==null||!fileIn.exists()||fileOut==null){
                return;
            }
            if(!fileOut.getParentFile().exists()){
                fileOut.getParentFile().mkdirs();
            }
            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }
            FileInputStream in1 = new FileInputStream(fileIn);
            FileOutputStream out1 = new FileOutputStream(fileOut);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in1.read(bytes)) != -1) {
                out1.write(bytes, 0, c);
            }
            in1.close();
            out1.flush();
            out1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制指定目录下文件
     *
     * @param sFile 源目录
     * @param tFile 目标目录
     */
    public static void copyFolder(File sFile, File tFile) {
        try {
            if (sFile==null||!sFile.exists()||tFile==null) {
                return;
            }
            if (!tFile.exists()) {
                tFile.mkdirs();
            }
            tFile = new File(tFile,sFile.getName());
            if (!tFile.exists()) {
                tFile.mkdirs();
            }
            for (int i = 0; i < sFile.listFiles().length; i++) {
                File temp = sFile.listFiles()[i];
                //假如是目录要递归复制文件
                if (temp.isDirectory()) {
                    copyFolder(temp, new File(tFile,temp.getName()));
                } else {
                    //实体文件则进行复制和删除操作
                    copyFile(temp, new File(tFile,temp.getName()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 将zip压缩文件解压。
     *
     */
    public static void unzip(File zipFile, String outputPath){
        BufferedInputStream bi = null;

        try {
            ZipFile zf = new ZipFile(zipFile, "GBK");
            Enumeration e = zf.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();
                String entryName = ze2.getName();
                String path = outputPath + "/" + getLimitFileName(entryName);
                if (ze2.isDirectory()) {
                    File decompressDirFile = new File(path);
                    if (!decompressDirFile.exists()) {
                        decompressDirFile.mkdirs();
                    }
                } else {
                    String fileDir = path.substring(0, path.lastIndexOf("/"));
                    File fileDirFile = new File(fileDir);
                    if (!fileDirFile.exists()) {
                        fileDirFile.mkdirs();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath + "/" + entryName));
                    bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                    bos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bi!=null){
                try {
                    bi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * tar解包
     *
     * @param srcFile 待压缩的文件或文件夹
     * @param dstDir  压缩至该目录，保持原文件名
     */
    public static void untarZip(File srcFile, String dstDir) {
        if(srcFile==null||!srcFile.exists()){
            return;
        }
        //需要判断该文件存在，且是文件夹
        File file = new File(dstDir);
        if (!file.exists() || !file.isDirectory()) file.mkdirs();
        byte[] buffer = new byte[BUFF_SIZE];
        FileInputStream fis = null;
        TarArchiveInputStream tais = null;
        try {
            fis = new FileInputStream(srcFile);
            tais = new TarArchiveInputStream(fis);
            TarArchiveEntry tarArchiveEntry;
            int len = 0;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                File f = new File(dstDir + File.separator + getLimitFileName(tarArchiveEntry.getName()));
                if (f.isDirectory()) f.mkdirs();
                else {
                    File parent = f.getParentFile();
                    if (!parent.exists()) parent.mkdirs();
                    FileOutputStream fos = new FileOutputStream(f);
                    while ((len = tais.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null) fis.close();
                //关闭数据流的时候要先关闭外层，否则会报Stream Closed的错误
                if(tais != null) tais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * rar 解压
     * @param srcFile
     *            rar文件
     *
     * @param savePath
     *            要解压的路径
     *
     */
    public static void unRar(File srcFile, String savePath) {
        FileOutputStream fos = null;
        if(srcFile==null||!srcFile.exists()){
            return;
        }
        try {
            // 创建一个rar档案文件
            Archive rarArchive = new Archive(new FileVolumeManager(srcFile));
            // 判断是否有加密
            if (rarArchive.isEncrypted()) {
                rarArchive.close();// 关闭资源
                return;
            }
            FileHeader fileHeader = rarArchive.nextFileHeader();
            while (fileHeader != null) {
                if (!fileHeader.isDirectory()) {
                    String name = fileHeader.getFileNameW().isEmpty() ? fileHeader
                            .getFileNameString() : fileHeader.getFileNameW();
                    String outPutFileName = savePath + "/" + getLimitFileName(name);
                    // 创建目录
                    File dir = new File(savePath);
                    // 创建文件夹
                    if (!dir.exists() || !dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    fos = new FileOutputStream(new File(outPutFileName));
                    // 保存解压的文件
                    rarArchive.extractFile(fileHeader, fos);
                }
                fileHeader = rarArchive.nextFileHeader();
            }
            rarArchive.close();// 关闭资源
        } catch (RarException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getLimitFileName(String fileName){
        if(fileName!=null){
            if(fileName.length()>90) {
                return fileName.substring(0, 15) + "..." + fileName.substring(fileName.length() - 15);
            }else{
                return fileName;
            }
        }
        return null;
    }
}
