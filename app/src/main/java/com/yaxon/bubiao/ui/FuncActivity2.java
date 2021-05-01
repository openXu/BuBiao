package com.yaxon.bubiao.ui;


import android.view.View;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityFunc2Binding;
import com.yaxon.bubiao.restory.SpKey;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.toasty.FToast;
import com.yaxon.bubiao.view.TitleLayout;

/**
 * 功能2 ： 主动安全参数
 */
public class FuncActivity2 extends BaseActivity<ActivityFunc2Binding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_func2;
    }
    @Override
    public void initView() {

    }
    @Override
    public void initData() {
        int func2AdasSelect = spUtil.getData(SpKey.FUNC2_ADAS_SELECT, Integer.class);
        int func2dsmSelect = spUtil.getData(SpKey.FUNC2_DSM_SELECT, Integer.class);
        FLog.w("第1个选择："+func2AdasSelect);
        FLog.w("第2个选择："+func2dsmSelect);
        if(func2AdasSelect>0){
            switch (func2AdasSelect){
                case 1 : binding.radioAdas.radioGroup.check(R.id.rb_1); break;
                case 2 : binding.radioAdas.radioGroup.check(R.id.rb_2); break;
                case 3 : binding.radioAdas.radioGroup.check(R.id.rb_3); break;
            }
        }
        if(func2dsmSelect>0){
            switch (func2dsmSelect){
                case 1 : binding.radioDsm.radioGroup.check(R.id.rb_1); break;
                case 2 : binding.radioDsm.radioGroup.check(R.id.rb_2); break;
                case 3 : binding.radioDsm.radioGroup.check(R.id.rb_3); break;
            }
        }
        binding.checkboxAdas.checkbox.setChecked(spUtil.getData(SpKey.FUNC2_ADAS, Boolean.class));
        binding.checkboxDsm.checkbox.setChecked(spUtil.getData(SpKey.FUNC2_DSM, Boolean.class));
    }
    @Override
    protected void onMenuClick(TitleLayout.MENU_NAME menu, View view) {
        super.onMenuClick(menu, view);
        if(menu== TitleLayout.MENU_NAME.MENU_RIGHT_TEXT){
            //保存
            int select = 0;
            switch (binding.radioAdas.radioGroup.getCheckedRadioButtonId()){
                case R.id.rb_1 : select = 1; break;
                case R.id.rb_2 : select = 2; break;
                case R.id.rb_3 : select = 3; break;
            }
            FLog.w("第1个选择："+select);
            spUtil.saveData(SpKey.FUNC2_ADAS_SELECT, select);
            switch (binding.radioDsm.radioGroup.getCheckedRadioButtonId()){
                case R.id.rb_1 :
                    select = 1; break;
                case R.id.rb_2 :
                    select = 2; break;
                case R.id.rb_3 :
                    select = 3; break;
            }
            FLog.w("第2个选择："+select);
            spUtil.saveData(SpKey.FUNC2_DSM_SELECT, select);
            spUtil.saveData(SpKey.FUNC2_ADAS, binding.checkboxAdas.checkbox.isChecked());
            spUtil.saveData(SpKey.FUNC2_DSM, binding.checkboxDsm.checkbox.isChecked());

            FToast.success("保存成功");
        }


    }
}