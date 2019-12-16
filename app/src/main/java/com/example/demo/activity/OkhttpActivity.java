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
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        initView();
    }

    private void initView(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button get = findViewById(R.id.get);
        Button post = findViewById(R.id.post);
        get.setOnClickListener(this);
        post.setOnClickListener(this);

        result = findViewById(R.id.result);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.get){
            HttpUtil.get("https://www.baidu.com/", new Callback() {
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
                            result.setText("get请求：" + responseData1);
                        }
                    });
                }
            });
        }
        if(view.getId() == R.id.post){
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
                            result.setText("post请求：" + responseData2);
                        }
                    });
                }
            }, null, map);
        }
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
