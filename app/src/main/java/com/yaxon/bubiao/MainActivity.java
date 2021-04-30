package com.yaxon.bubiao;


import android.graphics.drawable.StateListDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityMainBinding;
import com.yaxon.bubiao.databinding.MainTabLayoutBinding;
import com.yaxon.bubiao.ui.MainFragmentFunc;
import com.yaxon.bubiao.ui.MainFragmentVedio;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private Class[] mFragmentClasses = new Class[]{MainFragmentVedio.class, MainFragmentFunc.class};
    private Fragment[] fragments;
    private String[] tabName = new String[]{"视频预览", "参数配置"};
    private int[] tabIcon = new int[]{R.drawable.selector_main_tab_vedio,
            R.drawable.selector_main_tab_func};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        fragments = new Fragment[2];
        fragments[0] = new MainFragmentVedio();
        fragments[1] = new MainFragmentFunc();
        binding.viewPager.setAdapter(new ViewPagerFragmentStateAdapter(getSupportFragmentManager(), getLifecycle()));
        new TabLayoutMediator(
                binding.tabLayout,
                binding.viewPager,
                (tab, position) -> {
                    tab.setCustomView(makeTabView(tabName[position], tabIcon[position]));
                }
        ).attach();

        LinearLayout linearLayout= (LinearLayout) binding.tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(mContext,R.drawable.main_tab_divider));
        linearLayout.setDividerPadding(8);

        binding.tabLayout.getTabAt(0).select(); //默认选中某项放在加载viewpager之后
    }
    private View makeTabView(String name, int icon){
        MainTabLayoutBinding tabItemBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.main_tab_layout, binding.tabLayout, false);
        tabItemBinding.imageview.setImageResource(icon);
        tabItemBinding.textview.setText(name);
        return tabItemBinding.getRoot();
    }

    @Override
    public void initData() {

    }

    class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        public ViewPagerFragmentStateAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        @Override
        public int getItemCount() {
            return fragments.length;
        }
        @Override
        public Fragment createFragment(int position) {
            return fragments[position];
        }
    }
}