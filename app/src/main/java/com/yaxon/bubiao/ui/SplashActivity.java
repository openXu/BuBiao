package com.yaxon.bubiao.ui;


import android.content.Intent;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yaxon.bubiao.MainActivity;
import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivitySplashBinding;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.Lifecycle;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(aLong ->{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                });
    }


}