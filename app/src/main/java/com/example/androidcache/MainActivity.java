package com.example.androidcache;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidcache.image.ImageLoader;
import com.example.androidcache.view.simplecachedemo.SimpleCacheActivity;

public class MainActivity extends Activity {
    ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
    }

    public void getImage(View v) {
        ImageLoader mImageLoader = new ImageLoader();
        //根据URL下载图片
        mImageLoader.dowlandImage("http://a0.att.hudong.com/78/52/01200000123847134434529793168.jpg");
        Bitmap bmp = mImageLoader.getBmp();
        if(bmp != null){
            imageView.setImageBitmap(bmp);
        }
    }

    //跳转至自定义SimpleCache
    public void SimpleCache(View v) {
        startActivity(new Intent().setClass(this, SimpleCacheActivity.class));
    }

    //跳转至自定义SimpleCache
    public void BitmapCache(View v) {
        startActivity(new Intent().setClass(this, BitmapCacheActivity.class));
    }
}
