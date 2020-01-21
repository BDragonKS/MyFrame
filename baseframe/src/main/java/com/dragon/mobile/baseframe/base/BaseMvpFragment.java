package com.dragon.mobile.baseframe.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseMvpFragment extends BaseFragment {

    public abstract AbsBasePresenter getPresenter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this instanceof BaseView && getPresenter() != null) {
            getPresenter().attachView((BaseView) this);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this instanceof BaseView && getPresenter() != null) {
            getPresenter().detachView();
        }
    }
}
