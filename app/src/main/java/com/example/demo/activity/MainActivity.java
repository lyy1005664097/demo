package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        Button b1 = findViewById(R.id.b1);
        b1.setOnClickListener(this);
        Button b2 = findViewById(R.id.b2);
        b2.setOnClickListener(this);
        Button b3 = findViewById(R.id.b3);
        b3.setOnClickListener(this);
        Button b4 = findViewById(R.id.b4);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.b1){
            Intent intent = new Intent(this, OkhttpActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.b2){
            Intent intent = new Intent(this, BannerActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.b3){
            Intent intent = new Intent(this, RefreshAndLoadActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.b4){
            Intent intent = new Intent(this, FrameActivity.class);
            startActivity(intent);
        }
    }
}
