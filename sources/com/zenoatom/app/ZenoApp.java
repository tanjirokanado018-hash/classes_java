package com.zenoatom.app;

import android.app.Application;
import android.net.http.HttpResponseCache;
import java.net.ResponseCache;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ZenoApp extends Application {
    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        ResponseCache.setDefault(null);
        try {
            HttpResponseCache installed = HttpResponseCache.getInstalled();
            if (installed != null) {
                installed.delete();
            }
        } catch (Exception unused) {
        }
        System.setProperty("http.keepAlive", "false");
        System.setProperty("http.maxConnections", "1");
    }
}
