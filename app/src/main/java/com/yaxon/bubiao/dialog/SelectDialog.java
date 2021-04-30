package com.yaxon.bubiao.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.adapter.CommandRecyclerAdapter;
import com.yaxon.bubiao.adapter.ViewHolder;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectDialog<T> extends DialogFragment {


    TextView tv_cancel, tv_ok;
    RecyclerView recyclerView;

    CommandRecyclerAdapter<T> adapter;

    public SelectDialog(CommandRecyclerAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.select_dialog, container, false);
        tv_cancel = root.findViewById(R.id.tv_cancel);
        recyclerView = root.findViewById(R.id.recyclerView);
        tv_ok = root.findViewById(R.id.tv_ok);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_cancel.setOnClickListener(v->{
            dismiss();
        });
        tv_ok.setOnClickListener(v->{
            dismiss();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;//设置宽度为铺满
        params.height = getContext().getResources().getDimensionPixelSize(R.dimen.dp_320);
        params.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));


    }

    public void show(FragmentManager manager) {
        setCancelable(false);
        show(manager, "TimeConsumDialog");
    }

}
