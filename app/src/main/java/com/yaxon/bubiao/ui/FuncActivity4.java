package com.yaxon.bubiao.ui;

import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityFunc4Binding;
import com.yaxon.bubiao.restory.SelectItem;
import com.yaxon.bubiao.restory.SpKey;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.toasty.FToast;
import com.yaxon.bubiao.view.Spinner;
import com.yaxon.bubiao.view.TitleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能4 ： 部标参数配置
 */
public class FuncActivity4 extends BaseActivity<ActivityFunc4Binding> {

    private List<SelectItem> carColorList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_func4;
    }
    @Override
    public void initView() {
     /*   case 1:    //字符串
        et_value.setInputType(InputType.TYPE_CLASS_TEXT);
        et_value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});  //输入长度
        break;
        case 2:     //整数
        InputFilter[] filters = {new DigitInputFilter(FormConstant.INPUT_NUM_MAX, FormConstant.INPUT_NUM_MIN)};
        et_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
//                et_value.setKeyListener(DigitsKeyListener.getInstance("0123456789"));//限制输入固定的某些字符（0123456789）
        et_value.setFilters(filters);
        break;
        case 3:     //小数
        et_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL*//* | InputType.TYPE_NUMBER_FLAG_SIGNED*//*);
        //此处最小值设为0，会导致如果首位输入0后不能输入.（不能输入0-1的小数），但输入.能自动补全0.
        InputFilter[] filters1 = {new DigitInputFilter(FormConstant.INPUT_NUM_MAX, -1.0f, 2)}; //数值精度(小数位数)
        et_value.setFilters(filters1);
        break;*/

        binding.set1Ip1.edit1.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        binding.set1Ip1.edit2.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        binding.set1Ip2.edit1.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        binding.set1Ip2.edit2.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        binding.set1Ip3.edit1.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        binding.set1Ip3.edit2.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        binding.set2Phone.edit.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        binding.set2Id.edit.setInputType(InputType.TYPE_CLASS_TEXT);
        binding.set2Num.edit.setInputType(InputType.TYPE_CLASS_TEXT);

        binding.set3Vin.edit.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        binding.set3Carnum.edit.setInputType(InputType.TYPE_CLASS_TEXT);

        carColorList = new ArrayList<>();
        carColorList.add(new SelectItem(1,"蓝色"));
        carColorList.add(new SelectItem(2,"白色"));
        carColorList.add(new SelectItem(3,"黄色"));
        carColorList.add(new SelectItem(4,"其他"));
        Spinner.MySpinnerAdapter<SelectItem> adapter = new Spinner.MySpinnerAdapter(this,
                R.layout.item_layout_spinner_item, R.layout.item_layout_spinner_item, carColorList);
        binding.set3Carcolor.spinner.setAdapter(adapter, new Spinner.SpinnersAction<SelectItem>() {
            @Override
            public String getItemStr(SelectItem data) {
                return data == null?"":data.getName();
            }
            @Override
            public void onItemSelected(View view, int pos, SelectItem data) {
            }
        });

        binding.set3Capital.edit.setInputType(InputType.TYPE_CLASS_TEXT);
        binding.set3City.edit.setInputType(InputType.TYPE_CLASS_TEXT);
    }
    @Override
    public void initData() {

        binding.set1Ip1.edit1.setText(spUtil.getData(SpKey.FUNC4_IP1, String.class));
        binding.set1Ip1.edit2.setText(spUtil.getData(SpKey.FUNC4_PORT1, String.class));
        binding.set1Ip2.edit1.setText(spUtil.getData(SpKey.FUNC4_IP2, String.class));
        binding.set1Ip2.edit2.setText(spUtil.getData(SpKey.FUNC4_PORT2, String.class));
        binding.set1Ip3.edit1.setText(spUtil.getData(SpKey.FUNC4_IP3, String.class));
        binding.set1Ip3.edit2.setText(spUtil.getData(SpKey.FUNC4_PORT3, String.class));

        binding.set2Phone.edit.setText(spUtil.getData(SpKey.FUNC4_PHONE, String.class));
        binding.set2Id.edit.setText(spUtil.getData(SpKey.FUNC4_ID, String.class));
        binding.set2Num.edit.setText(spUtil.getData(SpKey.FUNC4_NUMBER, String.class));

        binding.set3Vin.edit.setText(spUtil.getData(SpKey.FUNC4_VIN, String.class));
        binding.set3Carnum.edit.setText(spUtil.getData(SpKey.FUNC4_CAR_NUMBER, String.class));

        int carColor = spUtil.getData(SpKey.FUNC4_CAR_COLOR, Integer.class);
        boolean selectedColor = false;
        for(int i = 0; i< carColorList.size(); i++){
            if(carColor == carColorList.get(i).getId()){
                FLog.w(i+"设置选择车辆颜色："+carColor);
                binding.set3Carcolor.spinner.setSelection(i);
                selectedColor = true;
            }
        }
        if (!selectedColor){
            binding.set3Carcolor.spinner.getSelectedView().setVisibility(View.INVISIBLE);
        }

        binding.set3Capital.edit.setText(spUtil.getData(SpKey.FUNC4_CAPITAL, String.class));
        binding.set3City.edit.setText(spUtil.getData(SpKey.FUNC4_CITY, String.class));
    }
    @Override
    protected void onMenuClick(TitleLayout.MENU_NAME menu, View view) {
        super.onMenuClick(menu, view);
        if(menu== TitleLayout.MENU_NAME.MENU_RIGHT_TEXT){
            //保存
            spUtil.saveData(SpKey.FUNC4_IP1, binding.set1Ip1.edit1.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_PORT1, binding.set1Ip1.edit2.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_IP2, binding.set1Ip2.edit1.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_PORT2, binding.set1Ip2.edit2.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_IP3, binding.set1Ip3.edit1.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_PORT3, binding.set1Ip3.edit2.getText().toString().trim());

            spUtil.saveData(SpKey.FUNC4_PHONE, binding.set2Phone.edit.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_ID, binding.set2Id.edit.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_NUMBER, binding.set2Num.edit.getText().toString().trim());

            spUtil.saveData(SpKey.FUNC4_VIN, binding.set3Vin.edit.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_CAR_NUMBER, binding.set3Carnum.edit.getText().toString().trim());

            Spinner.MySpinnerAdapter<SelectItem> adapter = ((Spinner.MySpinnerAdapter<SelectItem>)binding.set3Carcolor.spinner.getAdapter());
            Object obj = binding.set3Carcolor.spinner.getSelectedItem();
            if(obj!=null){
                spUtil.saveData(SpKey.FUNC4_CAR_COLOR, ((SelectItem)obj).getId());
            }

            spUtil.saveData(SpKey.FUNC4_CAPITAL, binding.set3Capital.edit.getText().toString().trim());
            spUtil.saveData(SpKey.FUNC4_CITY, binding.set3City.edit.getText().toString().trim());


            FToast.success("保存成功");
        }


    }

}