package com.yaxon.bubiao.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaxon.bubiao.MainActivity;
import com.yaxon.bubiao.R;
import com.yaxon.bubiao.adapter.CommandRecyclerAdapter;
import com.yaxon.bubiao.adapter.ViewHolder;
import com.yaxon.bubiao.base.BaseFragment;
import com.yaxon.bubiao.databinding.FragmentMainFuncBinding;
import com.yaxon.bubiao.databinding.FragmentMainVedioBinding;
import com.yaxon.bubiao.dialog.SelectDialog;
import com.yaxon.bubiao.restory.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: openXu
 * Time: 2021/4/30 11:08
 * class: MainFragment1
 * Description:
 */
public class MainFragmentFunc extends BaseFragment<FragmentMainFuncBinding> {



    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_func;
    }

    @Override
    public void initView() {

        //主动安全开关
        binding.tvOnoff.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), FuncActivity1.class));
        });
        //主动安全参数
        binding.tvZdaqcs.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), FuncActivity2.class));
        });
        //外接屏预览配置
        binding.tvWjpylpz.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), FuncActivity3.class));
        });
        //部标参数配置
        binding.tvBbcspz.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), FuncActivity4.class));
        });

        //软件版本
        binding.tvVersion.setOnClickListener(v->{
//            startActivity(new Intent(getActivity(), FuncActivity5.class));
            new SelectDialog<SelectItem>(new CommandRecyclerAdapter<SelectItem>(
                    MainFragmentFunc.this.getContext(), R.layout.item_select, versionList) {
                @Override
                public void convert(ViewHolder holder, SelectItem selectItem, int position) {
                    holder.setText(R.id.tv_name, selectItem.getName());
                    holder.setVisible(R.id.tv_current, selectItem.isSelected()? View.VISIBLE:View.GONE);
                }
                @Override
                public void onItemClick(SelectItem data, int position) {
                    for(SelectItem item : versionList){
                        item.setSelected(data.getId() == item.getId());
                    }
                    setData(versionList);
                }
            }).show(getChildFragmentManager());


        });
        //参数配置导入导出
        binding.tvCspzdrdc.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), FuncActivity6.class));
        });
    }
    private List<SelectItem> versionList;
    @Override
    public void initData() {
        versionList = new ArrayList<>();
        versionList.add(new SelectItem(3,"通道3.0"));
        versionList.add(new SelectItem(2,"通道2.0"));
        versionList.add(new SelectItem(1,"通道1.0"));
        versionList.get(0).setSelected(true);
    }
}
