package com.yaxon.bubiao.ui;

import android.view.View;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityFunc1Binding;
import com.yaxon.bubiao.restory.SharedData;
import com.yaxon.bubiao.restory.SpKey;
import com.yaxon.bubiao.utils.toasty.FToast;
import com.yaxon.bubiao.view.TitleLayout;

/**
 * 功能1 ： 主动安全开关
 */
public class FuncActivity1 extends BaseActivity<ActivityFunc1Binding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_func1;
    }
    @Override
    public void initView() {
    }
    @Override
    public void initData() {
        spUtil.getSpFile();

        binding.checkboxAdas.checkbox.setChecked(spUtil.getData(SpKey.FUNC1_ADAS, Boolean.class));
        binding.checkboxDsm.checkbox.setChecked(spUtil.getData(SpKey.FUNC1_DSM, Boolean.class));
        binding.checkboxModel.checkbox.setChecked(spUtil.getData(SpKey.FUNC1_MODEL, Boolean.class));
        binding.seekbarAdas.seekbar.setProgress(spUtil.getData(SpKey.FUNC1_ADAS_VALUE, Integer.class));
        binding.seekbarDsm.seekbar.setProgress(spUtil.getData(SpKey.FUNC1_DSM_VALUE, Integer.class));

        spUtil.getSpFile();
    }

    @Override
    protected void onMenuClick(TitleLayout.MENU_NAME menu, View view) {
        super.onMenuClick(menu, view);
        if(menu== TitleLayout.MENU_NAME.MENU_RIGHT_TEXT){
            //保存
            spUtil.saveData(SpKey.FUNC1_ADAS, binding.checkboxAdas.checkbox.isChecked());
            spUtil.saveData(SpKey.FUNC1_DSM, binding.checkboxDsm.checkbox.isChecked());
            spUtil.saveData(SpKey.FUNC1_MODEL, binding.checkboxModel.checkbox.isChecked());
            spUtil.saveData(SpKey.FUNC1_ADAS_VALUE, binding.seekbarAdas.seekbar.getProgress());
            spUtil.saveData(SpKey.FUNC1_DSM_VALUE, binding.seekbarDsm.seekbar.getProgress());

            spUtil.getSpFile();

            FToast.success("保存成功");

        }


    }
}