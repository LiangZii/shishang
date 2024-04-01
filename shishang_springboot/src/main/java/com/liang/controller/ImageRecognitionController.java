package com.liang.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liang.config.State;
import com.liang.exception.BusinessException;
import com.liang.lang.Result;
import com.liang.utils.GsonUtils;
import com.liang.utils.HttpUtil;
import com.liang.utils.JwtUtils;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/baiduAI")
public class ImageRecognitionController {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    private final String API_KEY = "FKdl88pzG6DFjzgcAddSMWgm";
    private final String SECRET_KEY = "7AXDu2IgKoXLaCgn1ikQS4yOof6E8ZrP";
    private long time_now = 0;
    private long time_expire = 0;
    private String accessToken = null;

    @PostMapping()
    public String ImageRecognition(MultipartFile photo, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/ImageRecognition/");

        String time_now = String.valueOf(System.currentTimeMillis());
        saveFile(time_now, photo, path);

        System.out.println(path + time_now + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")));

        String result = baiduAiOperate("http://8.130.102.219:8180/ImageRecognition/" + time_now + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")));
//        String result = baiduAiOperate("http://8.130.102.219:8180/ImageRecognition/1680677458329.png");

        return result;
    }

    public String baiduAiOperate(String imgUrl){
        time_now = System.currentTimeMillis();

        // 请求url
        String url = "https://aip.baidubce.com/api/v1/solution/direct/imagerecognition/combination";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("imgUrl", imgUrl);
            List<Object> scenes = new ArrayList<>();
            scenes.add("ingredient");
            scenes.add("dishs");
            map.put("scenes", scenes);

            String param = GsonUtils.toJson(map);

            if (time_now > time_expire || accessToken == null){
                getAccessToken();
            }

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id=" + API_KEY + "&client_secret=" + SECRET_KEY + "&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String result = response.body().string();
        System.out.println(result);

//        获取json对象
        JSONObject jsonObject = JSON.parseObject(result);

//        记录时间用于判断access_token是否到期
        time_now = System.currentTimeMillis();
        time_expire = time_now + jsonObject.getLong("expires_in");
//        记录access_token
        accessToken = jsonObject.getString("access_token");

    }

    public void saveFile(String time, MultipartFile photo, String path) throws IOException {
//        判断目录是否存在，不存在创建目录
        File dir = new File(path);
        if (!dir.exists()) {
//            创建目录
            dir.mkdir();
        }

        File file = new File(path + time + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")));
        photo.transferTo(file);

    }


}
