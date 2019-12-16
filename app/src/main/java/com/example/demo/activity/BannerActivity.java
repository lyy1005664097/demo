package com.example.demo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    List<String> paths;
    List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initData();
        initView();
    }

    private void initData(){
        paths = new ArrayList<>();
        paths.add("http://img0.imgtn.bdimg.com/it/u=4133970237,1551773401&fm=11&gp=0.jpg");
        paths.add("http://img2.imgtn.bdimg.com/it/u=1747277526,4027229022&fm=26&gp=0.jpg");
        paths.add("http://img4.imgtn.bdimg.com/it/u=1548659975,2111531&fm=26&gp=0.jpg");
        paths.add("http://img5.imgtn.bdimg.com/it/u=2863504786,3776649173&fm=26&gp=0.jpg");
        paths.add("http://img0.imgtn.bdimg.com/it/u=96180300,3008843172&fm=26&gp=0.jpg");
        titles = new ArrayList<>();
        titles.add("图片1");
        titles.add("图片2");
        titles.add("图片3");
        titles.add("图片4");
        titles.add("图片5");
    }

    private void initView(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView title = findViewById(R.id.title);
        Banner banner = findViewById(R.id.banner);
        banner.setDelayTime(2000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setIndicatorGravity(BannerConfig.CENTER)
                .isAutoPlay(true)
                .setBannerTitles(titles)
                .setImages(paths)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(context.getApplicationContext())
                                .load(path)
                                .into(imageView);
                    }
                })
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(final int position) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                title.setText(titles.get(position));
                            }
                        });
                    }
                })
                .start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
