package com.mc.ooo;

import android.content.Context;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author mc
 * @version 2018/9/21 1.0
 * @describe TODO
 * @see
 * @since
 */
public interface McOooApiService {
    /**
     * 认证接口
    */
    @GET("action/oauth2/authorize")
    Observable<String> getAuthorize(@Query("client_id") String client_id, @Query("response_type") String response_type,@Query("redirect_uri") String redirect_uri, @Query("state") String state);

    @POST("action/openapi/token")
    Observable<String> getAccessToken(@Query("client_id") String client_id,@Query("client_secret") String client_secret,@Query("grant_type") String grant_type,
    @Query("redirect_uri") String redirect_uri,@Query("code") String code,@Query("refresh_token") String refresh_token,@Query("dataType") String dataType,
    @Query("callback") String callback);
    /**
     * 个人信息
     */
    /**
     * 新闻
     */
    @FormUrlEncoded
    @POST("action/openapi/news_list")
    Observable<String> getNewsList(@FieldMap Map<String, String> map);

    /**
     * 帖子
     */
    /**
     * 动弹
     */
    /**
     * 博客
     */

    /**
     * 评论
     */
    /**
     * 收藏
     */
    /**
     * 软件
     */
    /**
     * 私信
     */
    /**
     * 搜索
     */
    /**
     * 通知
     */

    public static class Creator {
        private static McOooApiService mcOooApiService;

        public static McOooApiService Create(String type,final Context mContext) {
            String BASE_URL = "http://www.oschina.net/";
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLogger());
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            Interceptor addQueryParameterInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request;
                    String method = originalRequest.method();
                    Headers headers = originalRequest.headers();
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();
                    return chain.proceed(request);
                }
            };
            //公共参数
            httpBuilder.addInterceptor(addQueryParameterInterceptor);
            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .method(originalRequest.method(), originalRequest.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };
            //设置头
            httpBuilder.addInterceptor(headerInterceptor);
            httpBuilder.addNetworkInterceptor(logging);
            /**
             * 设置超时和重连，代码略
             */
            httpBuilder.readTimeout(3, TimeUnit.MINUTES)
                    .connectTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES); //设置超时
            //错误重连
            httpBuilder.retryOnConnectionFailure(true);
            OkHttpClient okHttpClient = httpBuilder.build();
            if (type.equals("Json")){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                mcOooApiService = retrofit.create(McOooApiService.class);
            }else if (type.equals("String")){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                mcOooApiService = retrofit.create(McOooApiService.class);
            }




            return mcOooApiService;
        }
    }
}

