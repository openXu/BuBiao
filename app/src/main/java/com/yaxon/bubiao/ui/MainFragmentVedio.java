package com.yaxon.bubiao.ui;

import android.view.View;
import android.widget.CompoundButton;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.adapter.CommandRecyclerAdapter;
import com.yaxon.bubiao.adapter.ViewHolder;
import com.yaxon.bubiao.base.BaseFragment;
import com.yaxon.bubiao.databinding.FragmentMainVedioBinding;
import com.yaxon.bubiao.dialog.SelectDialog;
import com.yaxon.bubiao.restory.SelectItem;
import com.yaxon.bubiao.restory.SharedData;
import com.yaxon.bubiao.restory.SpKey;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: openXu
 * Time: 2021/4/30 11:08
 * class: MainFragment1
 * Description:
 */
public class MainFragmentVedio extends BaseFragment<FragmentMainVedioBinding> {


    private List<SelectItem> channelList;
    SharedData spUtil;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_vedio;
    }

    @Override
    public void initView() {
        StatusBarUtil.setPaddingSmart(getContext(), binding.rlOnoff);
//        public static final String VEDIO_ONOFF = "vedioOnoff";
//        public static final String VEDIO_CHANNEL = "vedioChannel";
        binding.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            spUtil.saveData(SpKey.VEDIO_ONOFF, b);
        });
        channelList = new ArrayList<>();
        channelList.add(new SelectItem(1,"通道1"));
        channelList.add(new SelectItem(2,"通道2"));
        channelList.add(new SelectItem(3,"通道3"));
        binding.tvSelect.setOnClickListener(v->{
            new SelectDialog<SelectItem>(new CommandRecyclerAdapter<SelectItem>(
                    MainFragmentVedio.this.getContext(), R.layout.item_select, channelList) {
                @Override
                public void convert(ViewHolder holder, SelectItem selectItem, int position) {
                    holder.setText(R.id.tv_name, selectItem.getName());
                    holder.setVisible(R.id.tv_current, selectItem.isSelected()? View.VISIBLE:View.GONE);
                }
                @Override
                public void onItemClick(SelectItem data, int position) {
                    binding.tvSelect.setText(data.getName());
                    spUtil.saveData(SpKey.VEDIO_CHANNEL, data.getId());
                    for(SelectItem item : channelList){
                        item.setSelected(data.getId() == item.getId());
                    }
                    setData(channelList);
                }
            }).show(getChildFragmentManager());
        });
    }

    @Override
    public void initData() {
        spUtil = SharedData.getInstance();
        binding.checkbox.setChecked(spUtil.getData(SpKey.VEDIO_ONOFF, Boolean.class));
        int channel = spUtil.getData(SpKey.VEDIO_CHANNEL, Integer.class);
        if(channel>0){
            for(SelectItem item : channelList){
                if(item.getId() == channel) {
                    item.setSelected(true);
                    binding.tvSelect.setText(item.getName());
                }
            }
        }
    }
}
