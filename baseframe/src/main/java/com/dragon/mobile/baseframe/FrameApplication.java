package com.dragon.mobile.baseframe;


import com.dragon.mobile.baseframe.utils.CrashHandler;

import org.litepal.LitePalApplication;

public class FrameApplication extends LitePalApplication {

    private static FrameApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // 初始化异常处理类，出现异常保存日志到sd卡上
        CrashHandler.getInstance().init(getApplicationContext());
    }

    public static FrameApplication getInstance() {
        return mInstance;
    }
}
