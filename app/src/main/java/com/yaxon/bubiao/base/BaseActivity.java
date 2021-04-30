package com.yaxon.bubiao.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yanzhenjie.permission.AndPermission;
import com.yaxon.bubiao.R;
import com.yaxon.bubiao.dialog.TimeConsumDialog;
import com.yaxon.bubiao.restory.SharedData;
import com.yaxon.bubiao.utils.AndroidBug5497Workaround;
import com.yaxon.bubiao.utils.FBarUtils;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.StatusBarUtil;
import com.yaxon.bubiao.utils.permission.PermissionCallBack;
import com.yaxon.bubiao.utils.permission.PermissionUtile;
import com.yaxon.bubiao.view.TitleLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Author: openXu
 * Time: 2019/2/27 15:51
 * class: BaseActivity
 * Description:
 */
public abstract class BaseActivity<V extends ViewDataBinding>
        extends AppCompatActivity implements IBaseView {

    protected String TAG;
    protected Context mContext;
    protected boolean shoulFixKeyboard = true;

    protected V binding;
    protected TimeConsumDialog dialog;
    protected TitleLayout titleLayout;

    protected SharedData spUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FLog.w("开启activity:" + this);
        TAG = getClass().getSimpleName();
        mContext = this;
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, getLayoutId());

        //状态栏透明和间距处理
//            StatusBarUtil.darkMode(this);   //状态栏字体变黑色（透明状态栏）
        StatusBarUtil.immersive(this);//全透明状态栏(状态栏字体默认白色)
//            StatusBarUtil.immersive(this, getResources().getColor(R.color.colorPrimary), 1);
        //导航栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && FBarUtils.isSupportNavBar()) {
            FBarUtils.setNavBarVisibility(this, true);
            FBarUtils.setNavBarColor(this, Color.parseColor("#dddddd"));
        }
        titleLayout = findViewById(R.id.titleLayout);
        if (null != titleLayout) {
            StatusBarUtil.setPaddingSmart(this, titleLayout);
            titleLayout.setOnMenuClickListener((menu, view) -> {
                onMenuClick(menu, view);
            });
        }
        if (shoulFixKeyboard) {
            //解决键盘挡住输入框问题（全屏模式或者带WebView的界面）
            AndroidBug5497Workaround.assistActivity(this, true);
        }
        spUtil = SharedData.getInstance();
        initView();
        initData();
    }

    protected void onMenuClick(TitleLayout.MENU_NAME menu, View view) {
    }

    /***************************************权限相关*********************************************/
    protected String[] permissions;

    /**
     * 如果页面需要相关权限，请调用此方法申请，并覆盖onPermissionGranted()执行权限通过后的逻辑
     */
    protected void requestPermission(String... permissions) {
        this.permissions = permissions;
        PermissionUtile.requestPermission(this, new PermissionCallBack() {
            @Override
            public void onGranted() {
                onPermissionGranted();
            }

            @Override
            public void onDenied() {
                finish();
            }
        }, permissions);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        FLog.i("onActivityResult  reqCode=" + reqCode + "    resCode:" + resCode + "   data:" + data);
        switch (reqCode) {
            case PermissionUtile.PERMISSION_REQUEST_CODE: {
                if (AndPermission.hasPermissions(this, permissions)) {
                    // 有对应的权限
                    FLog.i("有对应的权限");
                    onPermissionGranted();
                } else {
                    // 没有对应的权限
                    FLog.e("没有对应的权限，继续申请");
                    requestPermission(permissions);
                }
                break;
            }
        }
    }

    /**
     * 权限通过后调用
     */
    protected void onPermissionGranted() {
    }
    /***************************************权限相关*********************************************/

    public void showProgressDialog() {
        if (dialog == null)
            dialog = new TimeConsumDialog();
        if (!dialog.isAdded())
            dialog.show(getSupportFragmentManager());
    }

    public void dismissProgressDialog() {
        FLog.w("------------------yincang   dialog       ");
        if (dialog != null && dialog.isVisible())
            dialog.dismissAllowingStateLoss();
    }

    /**
     * 隐藏输入法
     */
    public void hideSoftInputFromWindow() {
        try {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            FLog.e(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        if (binding != null)
            binding.unbind();
    }


}
