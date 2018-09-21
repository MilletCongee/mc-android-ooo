package com.mc.ooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
public class OooActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_get_Code,btn_login,btn_news_list;
    private String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooo);
        btn_get_Code = findViewById(R.id.btn_get_Code);
        btn_login = findViewById(R.id.btn_login);
        btn_news_list = findViewById(R.id.btn_news_list);

        btn_get_Code.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_news_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_Code:
                //取出key 做校验
                code = SpUtil.getString(OooActivity.this,"code");
                //Toast.makeText(OooActivity.this,code,Toast.LENGTH_SHORT).show();
                getAccessToken();
                break;
            case R.id.btn_login:
                startActivity(new Intent(OooActivity.this, LoginActivity.class));
                break;
            case R.id.btn_news_list:
                startActivity(new Intent(OooActivity.this, NewListActivity.class));
                break;
                default:
                    break;
        }
    }

    private void getAccessToken() {
        McOooApiService.Creator.Create("String",OooActivity.this).getAccessToken(
             "aQmOQuR6rRY7WzamqeHi","RTuML00PGLGrMas0sisVtncdAbge8V2D"   ,"refresh_token",
                "millet_congee",code,code,"json",""
        )
                .subscribeOn(Schedulers.io())//在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())//在主线程里面接受返回的数据
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String answer) {
                        Toast.makeText(OooActivity.this,answer,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(OooActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
