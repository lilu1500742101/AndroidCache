package com.example.androidcache;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidcache.cache.BitmapDiskCache;

public class BitmapCacheActivity extends AppCompatActivity {

    private ImageView mIv_bitmap_res;

    private BitmapDiskCache mBitmapDiskCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);
        // 初始化控件
        initView();

       // mBitmapDiskCache = new BitmapDiskCache(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mIv_bitmap_res = (ImageView) findViewById(R.id.iv_drawable_res);
    }

    /**
     * 点击save事件
     *
     * @param v
     */
    public void save(View v) {
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.img_test);
        mBitmapDiskCache.put("testBitmap", bitmap);
    }

    /**
     * 点击read事件
     *
     * @param v
     */
    public void read(View v) {
        Bitmap testBitmap = mBitmapDiskCache.get("testBitmap");
        if (testBitmap == null) {
            Toast.makeText(this, "Bitmap cache is null ...", Toast.LENGTH_SHORT)
                    .show();
            mIv_bitmap_res.setImageBitmap(null);
            return;
        }
        mIv_bitmap_res.setImageBitmap(testBitmap);
    }

    /**
     * 点击clear事件
     *
     * @param v
     */
    public void clear(View v) {
        mBitmapDiskCache.remove("testBitmap");
    }
}
