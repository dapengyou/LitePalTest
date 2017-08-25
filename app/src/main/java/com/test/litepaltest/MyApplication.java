package com.test.litepaltest;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by lady_zhou on 2017/8/23.
 */

public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
