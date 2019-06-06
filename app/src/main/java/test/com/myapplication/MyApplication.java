package test.com.myapplication;

import android.app.Application;

import io.mob.resu.reandroidsdk.ReAndroidSDK;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ReAndroidSDK.getInstance(this);
    }


}
