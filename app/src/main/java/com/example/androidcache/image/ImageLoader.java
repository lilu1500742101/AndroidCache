package com.example.androidcache.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.androidcache.cache.ImageCache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*图片加载类
* 1、根据URL，使用HTTP请求获取图片
* 2、将图片缓存至内存
* 注意：网络请求需要用到线程，此类采用线程池的方法*/
public class ImageLoader {
    /*创建一个数量为5的可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程,当提交的任务数量大于线程池
    * 数量时，会顺序执行提交的任务，当线程池中执行的任务都处于活动状态时，新提交的任务都会加入队列等待其他任务
    * 运行结束*/
    ExecutorService mExecutorService = Executors.newFixedThreadPool(5);
    ImageCache imageCache = new ImageCache();
    Bitmap bmp = null;

    /*下载图片
    * 根据url下载图片，然后将图片缓存至内存*/
    public void dowlandImage(String strURL){
        //获取内存缓存中的图片缓存
        bmp = imageCache.get(strURL);
        if(bmp!= null){//内存缓存中的图片不为空
            return;
        }else{//内存缓存中没有存储该图片，下载图片
            //创建一个任务
            DowlandImage mDowlandImage = new DowlandImage(strURL);
            //执行任务
            mExecutorService.submit(mDowlandImage);
        }
    }

    //实现下载图片任务
    class DowlandImage implements Runnable{
        String strURL = null;

        public DowlandImage(String strURL){
            this.strURL = strURL;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(strURL);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                bmp = BitmapFactory.decodeStream(conn.getInputStream());
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将下载的图片放置内存缓存中
            if(bmp != null){
                imageCache.put(strURL,bmp);
            }
        }
    }


    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
