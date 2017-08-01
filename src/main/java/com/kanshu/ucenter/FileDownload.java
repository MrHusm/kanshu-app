package com.kanshu.ucenter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author hushengmeng
 * @date 2017/7/7.
 */
public class FileDownload {
    public static void main(String[] args) {
        for(int i = 1998; i < 2019; i++){
            URL url = null;
            try {
                url = new URL("http://wannianli.tianqi.com//Public/Home/js/api/yjs/"+i+".js");
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
            }
            InputStream is = null;
            try {
                is = url.openStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            OutputStream os = null;
            try{
                os = new FileOutputStream("C:\\Users\\hushengmeng\\Desktop\\js\\"+i+".js");
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while((bytesRead = is.read(buffer,0,8192))!=-1){
                    os.write(buffer,0,bytesRead);
                  }
               }catch(FileNotFoundException e){
                } catch (IOException e) {
                e.printStackTrace();
                }
        }
    }
}
