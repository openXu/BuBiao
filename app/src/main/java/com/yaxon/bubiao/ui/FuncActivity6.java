package com.yaxon.bubiao.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yaxon.bubiao.R;
import com.yaxon.bubiao.base.BaseActivity;
import com.yaxon.bubiao.databinding.ActivityFunc6Binding;
import com.yaxon.bubiao.restory.SharedData;
import com.yaxon.bubiao.utils.FConversUtils;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.toasty.FToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 功能6 ： 参数配置导入导出
 */
public class FuncActivity6 extends BaseActivity<ActivityFunc6Binding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_func6;
    }
    @Override
    public void initView() {
        //1、申请权限
        requestPermission(
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE
        );
    }

    /**存储权限通过后*/
    @Override
    protected void onPermissionGranted() {
        super.onPermissionGranted();

        //参数导出
        binding.tvExport.setOnClickListener(v->{
            File file = spUtil.getSpFile();
            if(!file.exists()){
                FToast.warning("当前没有配置参数");
                return;
            }
            File dir = Environment.getExternalStorageDirectory().getAbsoluteFile();
            dir = new File(dir, "1BuBiao");
            dir.mkdir();
            File outFile = new File(dir, spUtil.getSpFileName());
            try {
                copyFile(file, outFile);
                FToast.success("参数已导出到："+outFile.getAbsolutePath());
                FLog.w("参数已导出到："+outFile.getAbsolutePath());
            }catch (Exception e){
                FToast.warning("参数导出失败："+e.getMessage());
            }
        });

        //参数导入
        binding.tvImport.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //不能在新的堆栈开启，否则回调不回来
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setType("image/*");
            intent.setType("text/xml");
            intent = Intent.createChooser(intent, "title");
            startActivityForResult(intent, 100);

           // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("file");
//            intent.setType("text/xml");//图片
            //intent.setType(“audio/*”); //音频
            //intent.setType(“video/*”); //视频
            //intent.setType(“video/*;image/*”);//视频＋图片
//            intent.setType("*/*");//无类型限制
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            try {
//                startActivityForResult(Intent.createChooser(intent, "选择文件"), 1);
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(mContext, "未找到文件管理应用，请安装文件管理应用后再试",Toast.LENGTH_SHORT).show();
//            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if(reqCode == 100 && resCode==RESULT_OK){
            // onActivityResult  reqCode=1    resCode:-1   data:Intent { dat=content://com.huawei.hidisk.fileprovider/root/storage/emulated/0/BuBiao.xml
            Uri uri = data.getData();
            FLog.w("选择返回Uri = "+uri);
            //TODO 直接选择导出的文件返回的Uri转换为File失败，文件系统数据库查不到，需要在系统文件夹app复制或者移动到另一个目录才能导入
            File file = FConversUtils.uri2File(uri);
            FLog.w("转换为File = "+file);
            if(file==null || !file.exists()){
                FToast.warning("未查询到参数文件");
                return;
            }
            FLog.w("文件名 = "+file.getName());
            if(!file.getName().equals(spUtil.getSpFileName())){
                FToast.warning("解析文件错误，请选择名称为"+spUtil.getSpFileName()+"的文件");
                return;
            }
            try {
                File outFile = spUtil.getSpFile();
                if(outFile.exists()) {
                    spUtil.clear();
                    FLog.a("本地已经有参数文件，清除缓存后会重新加载");
                    spUtil.getSpFile();
                }else{
                    //xml不存在，用户第一次使用，需要手动创建shared_prefs文件夹，避免导入复制xml文件时没有对应文件夹
                    // /data/data/com.yaxon.bubiao/shared_prefs/
                    spUtil.mkSpDir();
                }
                copyFile(file, outFile);
                FToast.success("参数导入成功");
            }catch (Exception e){
                FToast.warning("参数导入失败："+e.getMessage());
            }
        }
    }

    /**复制文件*/
    private void copyFile(File oldFile, File outFile) throws Exception{
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(oldFile);
            byte[] data = new byte[1024];
            outputStream =new FileOutputStream(outFile);
            while (inputStream.read(data) != -1) {
                outputStream.write(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            try {
                if(inputStream!=null)
                    inputStream.close();
                if(outputStream!=null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}