package com.dragon.mobile.baseframe.base;

import android.os.Bundle;

import dagger.android.AndroidInjection;

/**
 * MVP架构baseActivity
 * 支持dagger依赖注入
 */
public abstract class BaseMvpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        // 为presenter绑定视图
        if (getPresenter() != null && this instanceof BaseView) {
            getPresenter().attachView((BaseView) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
    }

    public abstract AbsBasePresenter getPresenter();
}
