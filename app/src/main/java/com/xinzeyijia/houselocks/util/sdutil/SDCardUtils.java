package com.xinzeyijia.houselocks.util.sdutil;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import com.xinzeyijia.houselocks.App;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.xinzeyijia.houselocks.util.sdutil.Path.SD_CARD_EXCEPTION_PATH;

/**
 * Created by ATt on 2016/11/22.
 */
public class SDCardUtils {
    private final static String TAG = SDCardUtils.class.getSimpleName();
    private static String state;
    private ArrayList<String> paths;

    public static void saveException(String fileTxt) {

//        try {
//
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                String path = SD_CARD_EXCEPTION_PATH;
//                File dir = new File(path);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                FileOutputStream fos = new FileOutputStream(path + "exeception.txt");
//                fos.write(fileTxt.getBytes());
//                fos.close();
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "an error occured while writing file...", e);
//        }
        FileWriter writer = null;
        try {
            String path = SD_CARD_EXCEPTION_PATH;

            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(path + "exeception.txt", true);
            writer.write(fileTxt);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SingSDCard {
        private static final SDCardUtils SD_CARD_UTILS = new SDCardUtils();
    }

    public static SDCardUtils getInstance() {
        return SingSDCard.SD_CARD_UTILS;
    }

    public boolean is_clean_sd() {
        state = Environment.getExternalStorageState();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //如果存储卡存在，则获取存储文件的路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getAbsolutePath());//创建StatFs对象
            long blockSize = sf.getBlockSize();//获得blockSize
            long totalBlock = sf.getBlockCount();//获得全部block
            long availableBlock = sf.getAvailableBlocks();//获取可用的block
            //用String数组来存放Block信息
            String[] total = fileSize(totalBlock * blockSize);
            String[] available = fileSize(availableBlock * blockSize);
            //在ProgressBar中显示可用空间的大小
            String s = "SD卡中空间总共有：" + total[0] + total[1] + "\n";
            s += "剩余空间大小：" + available[0] + available[1];
            available[0] = available[0].replaceAll("[,]", "");
            //还是
            try {
                return Integer.parseInt(available[0]) > 200 * 1024;
            } catch (Exception e) {


            }

        } else if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
            return false;
        }
        return false;
    }

    //用来定义存储空间显示格式
    public String[] fileSize(long size) {
        String s = "";
        if (size > 1024) {
            s = "KB";
            size /= 1024;
        }
        DecimalFormat df = new DecimalFormat();
        df.setGroupingSize(3);
        String[] result = new String[3];
        result[0] = df.format(size);
        result[1] = s;
        return result;
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //Log.i("Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 创建文件夹
     *
     * @param folderPath folderPath
     * @throws
     */
    public boolean createFolder(String folderPath) {
        if (!isSDCardMounted()) {
            return false;
        }
        File folder = new File(folderPath);
        if (!folder.exists() || folder.isFile()) {
            return folder.mkdirs();
        }
        return true;
    }

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }

    /**
     * sdcard是否挂载
     */
    public boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public void deleteImage(Context context, String imgPath) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media.DATA + "= \"" + new File(imgPath).getPath() + "", null);
    }

    /**
     * 递归删除文件和文件夹-------保留根文件夹
     */


//    public void DeleteFile(File file) {
//        if (BuildConfig.LOG_DEBUG) {
//            //Log.d("delete cache file is " + file.getAbsolutePath());
//            //Log.d("delete cache file type is dir? " + file.isDirectory());
//        }
//        try {
//            if (file.isDirectory()) {
//                final File[] childFile = file.listFiles();
//                if (childFile != null) {
//                    for (final File f : childFile) {
//                        DeleteFile(f);
//                    }
//                }
//            } else if (file.isFile()) {
//                boolean result = file.delete();
//                if (BuildConfig.LOG_DEBUG) {
//                    //Log.d("delete cache file result is " + result);
//                }
//            }
//            // 最后通知图库更新
//            App.getAppContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                    Uri.fromFile(new File(file.getPath()))));
//        } catch (Exception e) {
//            if (BuildConfig.LOG_DEBUG) {
//                //Log.e("delete cache file e is " + e.toString());
//            }
//        }
//    }

//    /**
//     * 删除文件
//     *
//     * @param filePath
//     */
//    public void deleteFile(String filePath) {
//        try {
//            File file = new File(filePath);
//            if (file.isFile()) {
//                boolean result = file.delete();
//                if (BuildConfig.LOG_DEBUG) {
//                    //Log.d("delete file result is " + result);
//                }
//            }
//        } catch (Exception e) {
//            if (BuildConfig.LOG_DEBUG) {
//                //Log.e("delete file e is " + e.toString());
//            }
//        }
//    }


//
    //将相机里的图片写入本地文件
    public String saveImageToCacheWithBytes(String activity_title, byte[] bytes) {
        String path = "";
        try {//文件名activity_id
            String fileName = activity_title + "/" + UUID.randomUUID().toString() + ".jpeg";
            path = Path.DEFAULT_IMAGE_CACHE_PATH + "/" + fileName;

            File file = checkTargetCacheDir(path);
            File cacheDir = createFile(file, "", ".jpg");
            boolean created = false;//是否创建成功,默认没有创建
            if (!cacheDir.exists()) created = cacheDir.createNewFile();
            if (created)//将图片写入目标file,100表示不压缩,Note:png是默认忽略这个参数的
            {
                //这里修改成了高效流
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
//            FileOutputStream out = new FileOutputStream(file);
                out.write(bytes, 0, bytes.length);
                out.flush();
                out.close();
                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(App.getAppContext().getContentResolver(),
                        file.getAbsolutePath(), fileName, "FileDescriptor must not be null");
                // 最后通知图库更新
                App.getAppContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(new File(file.getPath()))));
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 根据系统时间、前缀、后缀产生一个文件
     */
    public static File createFile(File folder, String prefix, String suffix) {
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename);
    }

    /**
     * 检查目标缓存目录是否存在，如果存在则返回这个目录，如果不存在则新建这个目录
     *
     * @return
     */
    public static File checkTargetCacheDir(String storageDir) {

        File file = null;
        file = new File(storageDir);

        if (!file.exists()) {
            file.mkdirs();//创建目录
        }

        if (file != null && file.exists())
            return file;//文件已经被成功创建
        else {
            return null;//即时经过以上检查，文件还是没有被准确的创建
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     * <code>false</code> otherwise
     */
    public int copyFile(String oldPath$Name) {

        try {
            File oldFile = new File(oldPath$Name);
            File appDir = new File(Environment.getExternalStorageDirectory(), "图播保存");
            if (!oldFile.exists()) {
                return 2;
            }
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File newfile = new File(appDir, fileName);
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(oldPath$Name));
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(newfile));
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
                //用来更新进度条
                int i = (int) ((101 * newfile.length() / oldFile.length()));//100
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(App.getAppContext().getContentResolver(),
                        newfile.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            App.getAppContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(newfile.getPath()))));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     * <code>false</code> otherwise
     */
    public boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    //Log.e("copyFolder: cannot create directory.");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }

                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    //Log.e("copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    //Log.e("copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    //Log.e("copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

                /* 如果不需要打log，可以使用下面的语句
                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (temp.exists() && temp.isFile() && temp.canRead()) {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                 */
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long 单位为M
     * @throws
     */
    public static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size / 1048576;
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long 单位为M
     * @throws
     */
    public static long getFolderSize1(File file) throws Exception {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public boolean isForeground(Context context, String myPackage) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);

        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equals(myPackage)) return true;
        return false;
    }
}
