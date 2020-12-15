package cn.myflavor.zjy.util;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    HTTP请求工具
 */
public class HttpUtil {

    private static OkHttpClient okHttpClient;

    /*
        创建一个http客户端
     */
    public static void newClient() {
        okHttpClient = new OkHttpClient();
    }

    /*
        创建保持cookie的http客户端
     */
    public static void newClientWithCookies() {
        okHttpClient = new OkHttpClient().newBuilder().cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();
    }
    /*
        Get请求
        url地址
     */

    public static String getData(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Post请求
        参数 url地址 body请求头
     */
    public static String postData(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
