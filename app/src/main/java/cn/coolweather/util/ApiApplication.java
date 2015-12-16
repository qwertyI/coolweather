package cn.coolweather.util;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by wei on 2015/12/16.
 */
public class ApiApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        ApiStoreSDK.init(this, "67bde62e8f02232be9224c9d67ac6b0d");
    }
}
