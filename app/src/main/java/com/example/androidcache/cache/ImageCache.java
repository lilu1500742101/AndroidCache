package com.example.androidcache.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.ArrayList;

/*
* 20200305 图片内存缓存
* 1、获取进程可用内存大小
* 2、初始化缓存空间大小，进程可用内存的1/8，使用key value键值对进行缓存，缓存格式为Bitmap位图
* 3、添加根据图片url获取和存储缓存函数
* */
public class ImageCache {

    LruCache<String, Bitmap>  mImageCache  = null;

    public ImageCache(){
        initImageCache();
    }


    /*初始化内存缓存API，设置缓存的大小*/
    public void initImageCache(){
        int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        mImageCache = new LruCache<String, Bitmap>(maxMemory/8){
            //实现此函数的作用？
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //获取图片的大小
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }
    /*保存图片缓存*/
    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }

    /*根据url获取图片缓存*/
    public Bitmap get(String url){
         return mImageCache.get(url);
    }
}
