package com.example.androidcache.view.simplecachedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidcache.R;

public class SimpleCacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_cache);
    }

    public void string(View v) {
        startActivity(new Intent().setClass(this, SaveStringActivity.class));
    }



    public void jsonarray(View v) {
        startActivity(new Intent().setClass(this, SaveJsonArrayActivity.class));
    }
    public void jsonobject(View v) {
        startActivity(new Intent().setClass(this, SaveJsonObjectActivity.class));
    }

    public void bitmap(View v) {
        startActivity(new Intent().setClass(this, SaveBitmapActivity.class));
    }


    public void drawable(View v) {
        startActivity(new Intent().setClass(this, SaveDrawableActivity.class));
    }

    public void object(View v) {
        startActivity(new Intent().setClass(this, SaveObjectActivity.class));
    }

    public void about(View v) {
        startActivity(new Intent().setClass(this, AboutActivity.class));
    }

}
