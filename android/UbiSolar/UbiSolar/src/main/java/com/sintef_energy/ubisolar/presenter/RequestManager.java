package com.sintef_energy.ubisolar.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import java.io.File;
import java.util.concurrent.Executors;

/**
 * Created by Håvard on 25.03.2014.
 */
public class RequestManager {

    private static RequestManager instance;
    private static RequestManager syncInstance;
    private RequestTipProxy mRequestTipProxy;
    private RequestSyncProxy mRequestSyncProxy;
    private RequestQueue mRequestQueue;
    private RequestFriendsProxy mRequestFriendsProxy;

    private RequestManager(Activity activity) {
        mRequestQueue = newRequestQueue(activity.getApplicationContext());
        mRequestTipProxy = new RequestTipProxy(activity, mRequestQueue);
        mRequestSyncProxy = new RequestSyncProxy(mRequestQueue);
        mRequestFriendsProxy = new RequestFriendsProxy(activity, mRequestQueue);
    }

    private RequestManager(Context context) {
        mRequestQueue = newRequestQueue(context);
        mRequestSyncProxy = new RequestSyncProxy(mRequestQueue);
    }

    public RequestTipProxy doTipRequest() {
        return mRequestTipProxy;
    }

    public RequestSyncProxy doSyncRequest(){
        return mRequestSyncProxy;
    }

    public RequestFriendsProxy doFriendRequest() {return mRequestFriendsProxy;}

    // This method should be called first to do singleton initialization
    public static synchronized RequestManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new RequestManager(activity);
        }
        return instance;
    }

    public static synchronized RequestManager getSyncInstance(Context context) {
        if(syncInstance == null)
            syncInstance = new RequestManager(context);
        return  syncInstance;
    }

    public static synchronized RequestManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call getInstance(..) method first.");
        }
        return instance;
    }

    public static RequestQueue newRequestQueue(Context context) {
        File cacheDir = new File(context.getCacheDir(), "def_cahce_dir");
        HttpStack stack;
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 9) {
            stack = new HurlStack();
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }

        Network network = new BasicNetwork(stack);
        int threadPoolSize = 10; // number of network dispatcher threads to create
        // pass Executor to constructor of ResponseDelivery object
        ResponseDelivery delivery = new ExecutorDelivery(Executors.newFixedThreadPool(threadPoolSize));
        // pass ResponseDelivery object as a 4th parameter for RequestQueue constructor
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, threadPoolSize, delivery);
        queue.start();

        return queue;
    }

}
