package com.yaxon.bubiao.ui;

import android.content.Intent;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityFunc5Binding;
import com.yaxon.bubiao.databinding.ActivityFunc6Binding;
import com.yaxon.bubiao.restory.SelectItem;
import com.yaxon.bubiao.utils.FLog;

import java.util.ArrayList;
import java.util.List;


public class FuncActivity5 extends BaseActivity<ActivityFunc5Binding> {


    private List<SelectItem> versionList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_func5;
    }
    @Override
    public void initView() {
        versionList = new ArrayList<>();
        versionList.add(new SelectItem(3,"通道3.0"));
        versionList.add(new SelectItem(2,"通道2.0"));
        versionList.add(new SelectItem(1,"通道1.0"));
    }
    @Override
    public void initData() {
    }

}