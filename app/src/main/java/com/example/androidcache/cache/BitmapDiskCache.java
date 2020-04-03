package com.example.androidcache.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
* author lilu 2020-4-2
* 硬盘缓存图片
* 1、硬盘缓存路径、缓存最大容量可自定义、可默认，默认缓存路径为SD卡缓存路径和内部存储缓存路径
* SD卡默认缓存路径为： /sdcard/Android/data/<application package>/cache
* 内部存储默认缓存路径为：/data/data/<application package>/cache
* 2、缓存key使用md5加密
* 3、本类使用DiskLruCache缓存框架，对其做了二次封装
* 2、写入缓存
* 3、读取缓存
* 4、移除缓存
*
* 问题：
* 1、如何让别处引用相同的缓存文件夹只有一个该类的对象，减少变量（内存占用），如果有两个使用中会出现问题吗？
* 2、将context引用内容分离出去
* 3、在初始化时可能会出现文件夹未打开成功，会影响其他函数的调用，如何保证函数正常调用，在调用时判断是否为空过于麻烦，不判断可能会留下bug
* 4、函数传入的参数未判断是否为null，没判断是否符合*/
public class BitmapDiskCache {

    DiskLruCache mDiskLruCache = null;
    static long defaultMaxSize = 10*1024*1024;
    static String defaultCahcheFolderName = "cache";

    //所有参数均默认
    public BitmapDiskCache(Context context){
        this(context,getDiskCacheDir(context,defaultCahcheFolderName),defaultMaxSize);
    }

    //自定义缓存文件夹名称
    public BitmapDiskCache(Context context,String cacheFolderName){
        this(context,getDiskCacheDir(context,cacheFolderName),defaultMaxSize);
    }

    //自定义缓存路径
    public BitmapDiskCache(Context context,File cacheDir){
        this(context,cacheDir,defaultMaxSize);
    }


    //自定义缓存容量
    public BitmapDiskCache(Context context,long maxSize){
        this(context,getDiskCacheDir(context,defaultCahcheFolderName),defaultMaxSize);
    }

    //自定义缓存文件夹和容量
    public BitmapDiskCache(Context context,String cacheFolderName,long maxSize){
        this(context,getDiskCacheDir(context,cacheFolderName),maxSize);
    }

    //自定义缓存路径和缓存容量
    public BitmapDiskCache(Context context,File cacheDir, long maxSize){
        DiskLruCache mDiskLruCache = null;
        try {
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, maxSize);
            this.mDiskLruCache = mDiskLruCache;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将图片写入缓存
    public void put(String imageUrl, Bitmap bitmap){
        if(mDiskLruCache != null){
            String key = stringToHashKey(imageUrl);
            try {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
                    editor.commit();
                }
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取图片缓存
    public Bitmap get(String imageUrl){
        Bitmap bitmap = null;
        String key = stringToHashKey(imageUrl);
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //移除图片缓存
    public void remove(String imageUrl){
        String key = stringToHashKey(imageUrl);
        try {
            mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //获取app版本号
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //获取默认缓存路径
    public static File getDiskCacheDir(Context context,String uniqueName) {
        String cachePath;
        cachePath = context.getCacheDir().getPath();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //对字符串进行MD5加密获取key（磁盘存储文件名）
    public String stringToHashKey( String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
