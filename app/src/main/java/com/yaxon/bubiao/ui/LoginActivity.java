package com.yaxon.bubiao.ui;


import android.content.Intent;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yaxon.bubiao.MainActivity;
import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityLoginBinding;
import com.yaxon.bubiao.databinding.ActivitySplashBinding;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.Lifecycle;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    public void initView() {

        binding.tvEnter.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
    }

    @Override
    public void initData() {
    }


}