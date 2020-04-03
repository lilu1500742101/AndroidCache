package com.example.androidcache.view.simplecachedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcache.R;
import com.example.androidcache.model.simplecache.ACache;

import java.io.Serializable;

public class SaveObjectActivity extends AppCompatActivity {


    private TextView mTv_object_original, mTv_object_res;
    private UserBean userBean;

    private ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_object);
        // 初始化控件
        initView();

        mCache = ACache.get(this);
        userBean = new UserBean();
        userBean.setAge("18");
        userBean.setName("HaoYoucai");
        mTv_object_original.setText(userBean.toString());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTv_object_original = (TextView) findViewById(R.id.tv_object_original12);
        mTv_object_res = (TextView) findViewById(R.id.tv_object_res12);
    }

    /**
     * 点击save事件
     *
     * @param v
     */
    public void save(View v) {
        mCache.put("testObject", userBean);
    }

    /**
     * 点击read事件
     *
     * @param v
     */
    public void read(View v) {
        UserBean testObject = (UserBean) mCache.getAsObject("testObject");
        if (testObject == null) {
            Toast.makeText(this, "Object cache is null ...", Toast.LENGTH_SHORT)
                    .show();
            mTv_object_res.setText(null);
            return;
        }
        mTv_object_res.setText(testObject.toString());
    }

    /**
     * 点击clear事件
     *
     * @param v
     */
    public void clear(View v) {
        mCache.remove("testObject");
    }


    class UserBean implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserBean [name=" + name + ", age=" + age + "]";
        }

    }

}
