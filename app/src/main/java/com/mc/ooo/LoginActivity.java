package com.mc.ooo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mc.ooo.utils.SpUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
public class LoginActivity extends AppCompatActivity{
    private WebView webView;
    private String LOGIN_URL = "https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=aQmOQuR6rRY7WzamqeHi&redirect_uri=millet_congee";

    String code = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        //设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //覆盖shouldOverrideUrlLoading 方法
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                String test =  "https://www.oschina.net/action/oauth2/millet_congee?code=BIBNBz&state=";
                 int index = url.indexOf("code=");
                 int ldex = url.indexOf("&state");
                 String test3before=test.substring(index+5,ldex);
                code = url.substring(index+5,ldex);
                Toast.makeText(LoginActivity.this,code,Toast.LENGTH_LONG).show();
                SpUtil.putString(LoginActivity.this,"code",code);
                finish();
                return true;
            }
        });

        webView.loadUrl(LOGIN_URL);
        //initData();
        //getAccessToken();
    }



    private void initData() {

        McOooApiService.Creator.Create("String",LoginActivity.this).getAuthorize("aQmOQuR6rRY7WzamqeHi","code","millet_congee","xyz")
                .subscribeOn(Schedulers.io())//在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())//在主线程里面接受返回的数据
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String answer) {
                        Toast.makeText(LoginActivity.this,answer,Toast.LENGTH_SHORT).show();
                        Log.e("McOooApiService",answer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
