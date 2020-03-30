package com.example.androidcache;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidcache.image.ImageLoader;

public class MainActivity extends AppCompatActivity {
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
}
