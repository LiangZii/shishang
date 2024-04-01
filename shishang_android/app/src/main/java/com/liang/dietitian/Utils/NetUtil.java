package com.liang.dietitian.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    public static String doGet(String urlPath){
        String data = "";
        HttpURLConnection conn;
        try{
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
//            设置请求方式
            conn.setRequestMethod("GET");
//            设置超时时间
            conn.setConnectTimeout(5000);
            if(conn.getResponseCode() == 200){
//                网络请求成功
                InputStream is = conn.getInputStream();
                data = StreamToString(is);
            }else {
//                网络请求失败，提示用户
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    public static String StreamToString(InputStream is){
        String str="";
        StringBuilder builder =new StringBuilder();
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String con;
            while ((con=br.readLine())!=null){
                builder.append(con);
            }
            br.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
