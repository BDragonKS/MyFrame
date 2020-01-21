package com.dragon.mobile.baseframe.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) {
        if (activity instanceof BaseMvpActivity) {
            BaseMvpActivity baseMvpActivity = (BaseMvpActivity) activity;
            if (baseMvpActivity.getPresenter() != null && activity instanceof BaseView) {
                baseMvpActivity.getPresenter().attachView((BaseView) activity);
            }
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (activity instanceof BaseActivity) {
            BaseMvpActivity baseMvpActivity = (BaseMvpActivity) activity;
            if (baseMvpActivity.getPresenter() != null) {
                baseMvpActivity.getPresenter().detachView();
//                Log.d("rainmin","detachView: " + activity.getLocalClassName());
            }
        }
    }
}
