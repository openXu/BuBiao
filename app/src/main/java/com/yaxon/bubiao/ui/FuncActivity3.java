package com.yaxon.bubiao.ui;


import android.view.View;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.adapter.CommandRecyclerAdapter;
import com.yaxon.bubiao.adapter.ViewHolder;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityFunc3Binding;
import com.yaxon.bubiao.dialog.SelectDialog;
import com.yaxon.bubiao.restory.SelectItem;
import com.yaxon.bubiao.restory.SpKey;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.toasty.FToast;
import com.yaxon.bubiao.view.TitleLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能3 ： 外接屏预览配置
 */
public class FuncActivity3 extends BaseActivity<ActivityFunc3Binding> {


    private List<SelectItem> channelList;
    private List<SelectItem> numberList;
    @Override
    public int getLayoutId() {
        return R.layout.activity_func3;
    }
    @Override
    public void initView() {
        channelList = new ArrayList<>();
        channelList.add(new SelectItem(1,"通道1"));
        channelList.add(new SelectItem(2,"通道2"));
        channelList.add(new SelectItem(3,"通道3"));
        numberList = new ArrayList<>();
        numberList.add(new SelectItem(1,"1路"));
        numberList.add(new SelectItem(2,"2路"));
        numberList.add(new SelectItem(3,"3路"));
        binding.selectChannel.tvSelect.setOnClickListener(v->{
            new SelectDialog<SelectItem>(new CommandRecyclerAdapter<SelectItem>(
                    FuncActivity3.this, R.layout.item_select, channelList) {
                @Override
                public void convert(ViewHolder holder, SelectItem selectItem, int position) {
                    holder.setText(R.id.tv_name, selectItem.getName());
                    holder.setVisible(R.id.tv_current, selectItem.isSelected()? View.VISIBLE:View.GONE);
                }
                @Override
                public void onItemClick(SelectItem data, int position) {
                    binding.selectChannel.tvSelect.setText(data.getName());
                    for(SelectItem item : channelList){
                        item.setSelected(data.getId() == item.getId());
                    }
                    setData(channelList);
                }
            }).show(getSupportFragmentManager());
        });
        binding.selectNumber.tvSelect.setOnClickListener(v->{
            new SelectDialog<SelectItem>(new CommandRecyclerAdapter<SelectItem>(
                    FuncActivity3.this, R.layout.item_select, numberList) {
                @Override
                public void convert(ViewHolder holder, SelectItem selectItem, int position) {
                    holder.setText(R.id.tv_name, selectItem.getName());
                    holder.setVisible(R.id.tv_current, selectItem.isSelected()? View.VISIBLE:View.GONE);
                }
                @Override
                public void onItemClick(SelectItem data, int position) {
                    binding.selectNumber.tvSelect.setText(data.getName());
                    for(SelectItem item : numberList){
                        item.setSelected(data.getId() == item.getId());
                    }
                    setData(numberList);
                }
            }).show(getSupportFragmentManager());
        });

    }
    @Override
    public void initData() {

        int channel = spUtil.getData(SpKey.FUNC3_CHANNEL, Integer.class);
        int number = spUtil.getData(SpKey.FUNC3_NUMBER, Integer.class);
        FLog.w("通道："+channel);
        FLog.w("数量："+number);
        if(channel>0){
            for(SelectItem item : channelList){
                if(item.getId() == channel) {
                    item.setSelected(true);
                    binding.selectChannel.tvSelect.setText(item.getName());
                }
            }
        }
        if(number>0){
            for(SelectItem item : numberList){
                if(item.getId() == channel) {
                    item.setSelected(true);
                    binding.selectNumber.tvSelect.setText(item.getName());
                }
            }
        }
    }

    @Override
    protected void onMenuClick(TitleLayout.MENU_NAME menu, View view) {
        super.onMenuClick(menu, view);
        if(menu== TitleLayout.MENU_NAME.MENU_RIGHT_TEXT){
            //保存
            for(SelectItem item : channelList){
                if(item.isSelected()) {
                    FLog.w("保存FUNC3_CHANNEL ： "+item.getId());
                    spUtil.saveData(SpKey.FUNC3_CHANNEL, item.getId());
                }
            }
            for(SelectItem item : numberList){
                if(item.isSelected()) {
                    FLog.w("保存FUNC3_NUMBER ： "+item.getId());
                    spUtil.saveData(SpKey.FUNC3_NUMBER, item.getId());
                }
            }

            FToast.success("保存成功");
        }


    }
}