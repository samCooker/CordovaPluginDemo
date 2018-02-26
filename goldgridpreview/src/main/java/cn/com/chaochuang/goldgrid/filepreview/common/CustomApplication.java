package cn.com.chaochuang.goldgrid.filepreview.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import cn.com.chaochuang.goldgrid.filepreview.utill.Https;
import cn.com.chaochuang.goldgrid.filepreview.utill.OkHttpUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyy on 2017/5/15.
 */

public class CustomApplication extends Application {
    private static Context context;
    private static ArrayList<Activity> list = new ArrayList<Activity>();
    private static CustomApplication instance;
    /**
     * 单例模式中获取唯一的MyApplication实例
     *
     * @return
     */
    public static CustomApplication getInstance() {
        if (null == instance) {
            instance = new CustomApplication();
        }
        return instance;
    }

    @Override
    public void onCreate(){

        super.onCreate();
        context = getApplicationContext();
        init_data();
        initOkHttp();
    }
    public static Context getContext() {
        return context;
    }
    public void init_data(){

       // CustomHttpServer.createAsyncHttpClientInstance();

    }

    public void removeActivity(Activity a){
        list.remove(a);
    }

    public void addActivity(Activity a){
        list.add(a);
    }
    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public static Context getContextObject(){
        return context;
    }
    /**
     * @Description 初始化OkHttp
     */
    private void initOkHttp() {
        File cache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        Https.SSLParams sslParams = Https.getSslSocketFactory(null, null, null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)//连接超时(单位:秒)
                .writeTimeout(60, TimeUnit.SECONDS)//写入超时(单位:秒)
                .readTimeout(60, TimeUnit.SECONDS)//读取超时(单位:秒)
                .pingInterval(60, TimeUnit.SECONDS) //websocket轮训间隔(单位:秒)
                .cache(new Cache(cache.getAbsoluteFile(), cacheSize))//设置缓存
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//https配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
