package com.sign.activity.aty_pdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
public class SDCardUtils {
    //判断SD卡是否挂载
    public static boolean isSDCardMounted(){
        //获取SD卡外部存储状态
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }

        return false;
    }
    //获取SD卡的根目录
    public static String getSDCardBaseDir(){
        if (isSDCardMounted()) {
            File dir = Environment.getExternalStorageDirectory();
            return dir.getAbsolutePath();
        }
        return null;
    }
    //获取SD卡全部内存空间大小
    public static long getSDCardSize(){
        if (isSDCardMounted()) {
            String dir = getSDCardBaseDir();
            //StatFs是从C语言引过来的
            StatFs statFs = new StatFs(dir);
            long blockCount = statFs.getBlockCountLong();//有多少块
            long blockSize = statFs.getBlockSizeLong();//每块有多大
            return blockCount*blockSize/1024/1024; //总大小
        }
        return 0;
    }
    //获取SD卡空闲空间大小(有多少空间还没被占用)
    public static long getSDCardFreeSize(){
        if (isSDCardMounted()) {
            String dir = getSDCardBaseDir();
            //StatFs是从C语言引过来的
            StatFs statFs = new StatFs(dir);
            long freeBlockCount = statFs.getFreeBlocksLong();//有多少块
            long blockSize = statFs.getBlockSizeLong();//每块有多大
            return freeBlockCount*blockSize/1024/1024; //总大小
        }
        return 0;
    }
    //获取SD卡可用剩余空间大小(还剩下多少空间)
    public static long getSDCardAvailableSize(){
        if (isSDCardMounted()) {
            String dir = getSDCardBaseDir();
            //StatFs是从C语言引过来的
            StatFs statFs = new StatFs(dir);
            long availableBlockCount = statFs.getAvailableBlocksLong();//有多少块
            long blockSize = statFs.getBlockSizeLong();//每块有多大
            return availableBlockCount*blockSize/1024/1024; //总大小
        }
        return 0;
    }
    //往SD卡九大目录保存文件
    public static boolean saveData2SDCardpublicDir(byte[] bys,String type,String filename){
        if (isSDCardMounted()) {
            //获取SD卡的根目录
            String baseDir = getSDCardBaseDir();
            //获取文件的路径
            String file = baseDir+File.separator+type+File.separator+filename;
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(bys);
                os.close();
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //往SDCard公有目录下保存文件 (九大公有目录中的一个，具体由type指定)
    public static boolean saveData2SDCardCustomDir(byte[] bys,String dir,String filename){
        if (isSDCardMounted()) {
            //获取SD卡的根目录
            String baseDir = getSDCardBaseDir();
            String path =baseDir+File.separator+dir;
            //获取自建文件夹的路径
            File file1 = new File(path);
            //判断文件夹是否存在，不存在的话就得先创建
            if (!file1.exists()) {
                file1.mkdir();
            }
            //获取文件的路径
            String file = baseDir+File.separator+dir+File.separator+filename;
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(bys);
                os.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //往SDCard的私有File目录下保存文件
    public static boolean saveData2SDCardPrivateFileDir(byte[] data, String type, String filename, Context context){
        if (isSDCardMounted()) {
            File path = context.getExternalFilesDir(type);
            if (!path.exists()) {
                path.mkdir();
            }
            String file = path.getAbsolutePath()+File.separator+filename;
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(data);
                os.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //往SDCard的私有Cache目录下保存文件
    public static boolean saveData2SDCardPrivateCacheDir(byte[] data, String filename, Context context){
        if (isSDCardMounted()) {
            File path = context.getExternalCacheDir();
            if (!path.exists()) {
                path.mkdir();
            }
            String file = path.getAbsolutePath()+File.separator+filename;
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(data);
                os.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //往SDCard的私有Cache目录下保存图像
    public static boolean saveBitmap2SDCardPrivateCacheDir(Bitmap bitmap, String filename, Context context){
        if (isSDCardMounted()) {
            File path = context.getExternalCacheDir();
            if (!path.exists()) {
                path.mkdir();
            }
            String file = path.getAbsolutePath()+File.separator+filename;
            try {
                OutputStream os = new FileOutputStream(file);
                //把图片转成byte[]
                //os.write();
                //判断图片格式
                if(filename.endsWith(".JPG")||filename.endsWith(".jap")){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                }else if(filename.endsWith(".PNG")||filename.endsWith(".png")){
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                }
                os.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //从SDCard读取指定文件
    public static byte[] loadFileFromSDCard(String filePath){
        if (isSDCardMounted()) {
            String path = getSDCardBaseDir();
            String file = path+File.separator+filePath;
            try {
                InputStream is = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bys = new byte[1024];
                int len = 0;
                while((len=is.read(bys))!=-1){
                    baos.write(bys, 0, len);
                }
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //从SDCard读取Bitmap并返回
    public static Bitmap loadBitmapFromSDCard(String filePath){
        if (isSDCardMounted()) {
            //filePath就是全路径
            try {
                InputStream is = new FileInputStream(filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bys = new byte[1024];
                int len = 0;
                while((len=is.read(bys))!=-1){
                    baos.write(bys, 0, len);
                }
                byte[] data = baos.toByteArray();
                Bitmap bitmap =BitmapFactory.decodeByteArray(data, 0, data.length);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //获取SD卡公有目录路径
    public static String getSDCardPublicDir(String type){
        if (isSDCardMounted()) {
            File dir = Environment.getExternalStoragePublicDirectory(type);
            return dir.getAbsolutePath();
        }
        return null;
    }
    //获取SDCard私有Cache目录路径
    public static String getSDCardPrivateCacheDir(Context context){
        if (isSDCardMounted()) {
            File dir = context.getExternalCacheDir();
            return dir.getAbsolutePath();
        }
        return null;
    }
    //获取SDCard私有File目录路径
    public static String getSDCardPrivateFilesDir(Context context, String type){

        if (isSDCardMounted()) {
            File dir =context.getExternalFilesDir(type);
            return dir.getAbsolutePath();
        }

        return null;
    }
    //判断一个文件是否存在
    public static boolean isFileExists(String filePath){
        if (isSDCardMounted()) {
            File file = new File(filePath);
            return file.exists();
        }
        return false;
    }
    //删除一个文件
    public static boolean removeFileFromSDCard(String filePath){
        if (isSDCardMounted()) {
            File file =new File(filePath);
            return file.delete();
        }
        return false;
    }
}