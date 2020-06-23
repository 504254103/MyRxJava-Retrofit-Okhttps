package com.frame.myrxjava_retrofit_okhttps.netWork;
 
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.frame.myrxjava_retrofit_okhttps.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    //设置缓存目录
    private static File cacheDirectory = new File(MyApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    /**
     * 获取OkHttpClient对象
     *
     * @return
     */

    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //设置一个自动管理cookies的管理器
                    .cookieJar(new CookiesManager())
                    //添加拦截器
                    .addInterceptor(new LoggingInterceptor())
                    .addNetworkInterceptor(new MyInterceptor())
                    //设置请求读写的超时时间
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .cache(cache)
                    .build();
        }

        return mOkHttpClient;
    }

    /**
     * 拦截器
     */

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request.Builder builder = chain.request().newBuilder();
            if (!isNetworkReachable(MyApplication.getInstance().getApplicationContext())) {
                //无网下强制缓存
                builder.cacheControl(CacheControl.FORCE_CACHE); //等同于添加only-if-cache
            }
            Request request = builder.build();

            long t1 = System.nanoTime();//请求发起的时间
            Log.i("dt", String.format("发送请求 %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            BufferedSource source = responseBody.source();
            String respString = source.buffer().clone().readString(Charset.defaultCharset());
            JSONObject j = null;
            try {
                j = new JSONObject(respString);
               //处理方式 全局变量等
            } catch (JSONException e) {
                if(response.code()==401){
                    //处理方式
                }else if(response.code()==504) {
                    //处理方式
                }//更多异常处理
            }

            if (j != null && j.optInt("MsgCode") != 0) {
                final JSONObject finalJ = j;
                //返回json字符串处理
            }

            Log.i("dt", String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));

            return response;

        }
    }

    private static class MyInterceptor implements Interceptor {

        private Map<String, String> headers;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetworkReachable(MyApplication.getInstance().getApplicationContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)//无网络时只从缓存中读取
                        .build();
                Response response = chain.proceed(request);
                return response;

            } else {
                int maxAge = 60 * 60 * 24; // 有网络时 设置缓存超时时间24个小时
                Response response = chain.proceed(request);
                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .addHeader("Cache-Control", "public, max-age=" + maxAge)
                        .build();
                return response;
            }

            Request.Builder builder = request.newBuilder();
            builder.addHeader("Authorization","Basic "+" ");
            return chain.proceed(builder.build());

            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String headerKey : keys) {
                    builder.addHeader(headerKey,headers.get(headerKey)).build();
                }
            }
            builder.addHeader("Authorization","token");
        }
    }

    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

}
