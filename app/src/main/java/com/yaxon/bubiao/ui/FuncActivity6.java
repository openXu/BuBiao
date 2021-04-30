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
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("image/*");
//            intent.setType("text/xml");
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



        /*    String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (rootPath.endsWith("/")) {
            } else {
                rootPath = rootPath + "/";
            }
            FLog.w("sd卡根目录："+rootPath);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {   //判读版本是否在7.0以上Android 7.0
                //AndPermission提供了兼容Android7.0及更高系统生成私有文件的Uri的方法：
                uri = AndPermission.getFileUri(this, new File(rootPath));
//                        mCurrentPhotoUri = FileProvider.getUriForFile(this, BuildConfig.AUTHORITIES, photoFile);
            } else {
                uri = Uri.fromFile(new File(rootPath));
            }
            Intent intent = IntentDocumentView.getXmlFileIntent(uri);
            startActivityForResult(intent,100);*/
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
            FLog.w("选择配置文件："+uri);
            File file = FConversUtils.uri2File(uri);
            if(file==null){
                FToast.warning("导入的文件不合法");
                return;
            }

   /*         String path = null;
            if (URLUtil.isFileUrl(uri.toString())) {
                path = uri.getPath();
            } else {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                }
            }
            FToast.warning("选择配置文件"+path);
            if(TextUtils.isEmpty(path)){
                FToast.warning("导入的文件不合法");
                return;
            }
            File file = new File(path);*/
            FLog.w("选择配置文件"+file);
            try {
                File outFile = spUtil.getSpFile();
                if(outFile.exists())
                    FLog.a("本地已经有配置参数，先删除文件"+outFile.delete());

                copyFile(file, outFile);
                FToast.success("参数导入成功");
            }catch (Exception e){
                FToast.warning("参数导入失败："+e.getMessage());
            }

        }

    }

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


    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append( MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {  MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex( MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex( MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
//            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

}