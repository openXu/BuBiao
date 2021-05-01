package com.yaxon.bubiao;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;


import com.yaxon.bubiao.base.BaseActivity;

import java.util.List;
import java.util.Stack;

/**
 * Author: openX
 * Time: 2019/2/23 14:49
 * class: BaseApplication
 * Description: Application请继承该基类
 */
public class BuBiaoApplication extends Application {

    private static Stack<Activity> activityStack = new Stack<>();
    private static Context mcontext;
    protected static BuBiaoApplication application;

    @Override
    public void onCreate() {
        application = this;
        mcontext = getApplicationContext();
        super.onCreate();
        registerActivityLifecycleCallbacks(activityLifecycle);
    }

    public static BuBiaoApplication getApplication() {
        return application;
    }
    public static Context getContext() {
        return mcontext;
    }
    /**
     * 获取当前进程名 
     * @return
     */
    protected String getCurrentProcessName(){
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
                if (processInfo.pid == pid) {
                   return processInfo.processName;
                }
            }
        }
        return "";
    }


    /**
     * 获取栈顶的Activity对象
     */
    public BaseActivity getCurrentActivity() {
        if (activityStack.isEmpty())
            return null;
        try {
            BaseActivity activity = (BaseActivity) activityStack.peek();
            return activity;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 退出应用关闭所有Activity
     */
    public static void finishAllActivity() {
        if (activityStack.isEmpty())
            return;
        do {
            Activity activity = activityStack.pop();
//            FLog.w("关闭activity："+activity);
            activity.finish();
        } while (!activityStack.isEmpty());

    }

    private ActivityLifecycleCallbacks activityLifecycle = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activityStack.push(activity);
//            FLog.i("activity入栈：" + activityStack.size() + " " + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (null == activity || activityStack.isEmpty())
                return;
            if (activityStack.contains(activity)) {
                activityStack.remove(activity);
//                FLog.w("activity出栈：" + activityStack.size() + " " + activity);
            }
        }
    };


}
