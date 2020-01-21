package com.dragon.mobile.baseframe.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dragon.mobile.baseframe.constant.Constant;
import com.longruan.mobile.appframe.R;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 本项目所有activity的直接父类
 * <dl>
 * Class Description
 * <dd>项目名称：AppFrame
 * <dd>类名称：BaseActivity
 * <dd>类描述： 本项目所有activity的直接父类，此类抽取了所有activity的共性，并进行了一些初始化操作。
 *              注意：1.如果子类需要展现菜单栏，需要子类Activity去实现onCreateOptionsMenu方法；
 *                    2.如果菜单栏只有一个操作，需要显示文字，则在Menu文件中<item/>不设置android:icon属性，设置此属性则显示该操作的Icon。
 * <dd>创建时间：2017年6月6日10:29:58
 * <dd>修改人：无
 * <dd>修改时间：无
 * <dd>修改备注：无
 * </dl>
 *
 * @author lujing
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    //    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.tv_title)
    TextView tv_title;
    //    @BindView(R.id.f_content)
    FrameLayout f_content;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContent(R.layout.activity_demo, true, "test");
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.EXIT_APP);
        this.registerReceiver(this.broadcastReceiver, filter);
    }

    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    /**
     * 子类Activity继承BaseActivity都需调用此方法，设置布局以及标题栏属性,默认显示返回按钮
     *
     * @param resId 布局文件ID
     * @param title 标题栏的标题
     */
    public void initToolbar(int resId, String title) {
        setContentView(R.layout.activity_base);
        f_content = findViewById(R.id.f_content);
        mToolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.tv_title);

        //Fragment添加布局
        View contentView = View.inflate(this, resId, null);
        f_content.addView(contentView);
        //添加注解框架
        mUnbinder = ButterKnife.bind(this);
        //toolbar的相关设置
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        //设置标题
        tv_title.setText(title);
    }

    /**
     * 子类Activity继承BaseActivity都需调用此方法，设置布局以及标题栏属性
     *
     * @param resId    布局文件ID
     * @param showBack 是否显示返回按钮，true：显示；false：不显示
     * @param title    标题栏的标题
     */
    public void initToolbar(int resId, boolean showBack, String title) {
        setContentView(R.layout.activity_base);
        f_content = findViewById(R.id.f_content);
        mToolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.tv_title);

        //Fragment添加布局
        View contentView = View.inflate(this, resId, null);
        f_content.addView(contentView);

        //添加注解框架
        mUnbinder = ButterKnife.bind(this);
        //toolbar的相关设置
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false);
            if (!showBack) {
                bar.setDisplayHomeAsUpEnabled(false);
            } else {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }

        //设置标题
        tv_title.setText(title);
    }

    /**
     * 设置布局，隐藏toolbar
     *
     * @param resId 布局文件ID
     */
    public void initNoToolbar(int resId) {
        setContentView(R.layout.activity_base);
        f_content = findViewById(R.id.f_content);
        mToolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.tv_title);
        mToolbar.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);

        //Fragment添加布局
        View contentView = View.inflate(this, resId, null);
        f_content.addView(contentView);

        //添加注解框架
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        this.unregisterReceiver(this.broadcastReceiver);
    }

    // 点击键盘外区域自动隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(v).getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);

    }


    /**
     * 是否保留点击EditText的事件
     *
     * @param v     组件
     * @param event 点击事件
     * @return boolean false：保留，true：不保留。
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    public void setBaseTitle(String title) {
        if (tv_title != null) {
            tv_title.setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        BaseActivity.this.overridePendingTransition(
                R.anim.push_right_in, R.anim.push_right_out);
    }
}
