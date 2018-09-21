package com.mc.ooo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
public class NewListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        getNewsList();
    }

    private void getNewsList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", "aQmOQuR6rRY7WzamqeHi");
        map.put("catalog", "1-所有|2-综合新闻|3-软件更新");
        map.put("page", "页数");
        map.put("pageSize", "每页条数");
        map.put("dataType", "返回数据类型");

        McOooApiService.Creator.Create("Json",NewListActivity.this).getNewsList(map)
                .subscribeOn(Schedulers.io())//在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())//在主线程里面接受返回的数据
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
