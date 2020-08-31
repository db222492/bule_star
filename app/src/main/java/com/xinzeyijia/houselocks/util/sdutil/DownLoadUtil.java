package com.xinzeyijia.houselocks.util.sdutil;

import android.content.Intent;
import android.net.Uri;
import com.xinzeyijia.houselocks.App;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.xinzeyijia.houselocks.util.LogUtils.loge;

/**
 * author : DiBo
 * e-mail : db222492@163.com
 * date   : 2019/5/1313:51
 * desc   :
 */
public class DownLoadUtil {


    private static final class SingLeTon {
        private static final DownLoadUtil DOWN_LOAD_UTIL = new DownLoadUtil();
    }

    public static DownLoadUtil getInstance() {
        return SingLeTon.DOWN_LOAD_UTIL;
    }

    public void downLoad(String filePath, String fileName, ResponseBody responseBody) {


        InputStream ins = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            ins = responseBody.byteStream();
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = ins.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            App.getAppContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            //onCompleted();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) ins.close();
                if (fos != null) fos.close();


            } catch (IOException e) {
                loge("saveFile", e.getMessage());
            }
        }
    }


}
