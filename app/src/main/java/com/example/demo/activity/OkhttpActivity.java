package com.example.demo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;
import com.wulianwang.lsp.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView result1;
    private TextView result2;
    private TextView result3;
    private TextView result4;
    private TextView result5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        initView();
    }

    private void initView(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button syncget = findViewById(R.id.syncget);
        Button asyncget = findViewById(R.id.asyncget);
        Button post = findViewById(R.id.post);
        Button egget = findViewById(R.id.egget);
        Button egpost = findViewById(R.id.egpost);
        syncget.setOnClickListener(this);
        asyncget.setOnClickListener(this);
        post.setOnClickListener(this);
        egget.setOnClickListener(this);
        egpost.setOnClickListener(this);

        result1 = findViewById(R.id.result1);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        result4 = findViewById(R.id.result4);
        result5 = findViewById(R.id.result5);
    }

    @Override
    public void onClick(View view) {
        String getUrl = "https://www.baidu.com/";
        String postUrl = "http://61.163.34.143:8088/grid/rest/logining/selectUserInfo.cs";
        if(view.getId() == R.id.syncget){
            //同步get
            syncget(getUrl);
        }else if(view.getId() == R.id.asyncget){
            //同步get
            asyncget(getUrl);
        }else if(view.getId() == R.id.post){
            post(postUrl);
        }else if(view.getId() == R.id.egget){
            HttpUtil.get(getUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String responseData1 = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result4.setText("get请求：" + responseData1);
                        }
                    });
                }
            });
        }else if(view.getId() == R.id.egpost){
            Map<String, String> map = new HashMap<>();
            String data = "{'username': '13137613129'}";
            map.put("data", data);
            HttpUtil.post("http://61.163.34.143:8088/grid/rest/logining/selectUserInfo.cs", null, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String responseData2 = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result5.setText("post请求：" + responseData2);
                        }
                    });
                }
            }, null, map);
        }
    }

    //同步get请求
    private void syncget(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = client.newCall(request);
                try {
                    final Response response = call.execute();
                    final String text = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result1.setText(text);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //异步get请求
    private void asyncget(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String text = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result2.setText(text);
                    }
                });
            }
        });
    }

    //post请求
    private void post(String url){
        String data = "{'username': '13137613129'}";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("data", data)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String text = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result3.setText(text);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
