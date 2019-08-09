 package com.gr.community.util;

import com.alibaba.fastjson.JSON;
import com.gr.community.dto.GithubPostPojo;
import com.gr.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

 @Component
public class GithubRequest {
    public String getAccessToken(GithubPostPojo githubPostPojo){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = JSON.toJSONString(githubPostPojo);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str1 = response.body().string();
            String[] split = str1.split("&");
            String[] split1 = split[0].split("=");
            String str = split1[1];
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public GithubUser getMag(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
