package com.yaxon.bubiao.dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yaxon.bubiao.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Author: openX
 * Time: 2019/4/12 11:36
 * class: TimeConsumDialog
 * Description: 耗时对话框（网络请求，图片处理....）
 * Update: 
 */
public class TimeConsumDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_progress_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void show(FragmentManager manager) {
        show(manager, false);
    }
    public void show(FragmentManager manager, boolean isCancelable) {
        setCancelable(isCancelable);
        show(manager, "TimeConsumDialog");
    }

}
