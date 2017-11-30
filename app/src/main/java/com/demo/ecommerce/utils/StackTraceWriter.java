package com.demo.ecommerce.utils;

import com.demo.ecommerce.BuildConfig;

public class StackTraceWriter {

    private static boolean isAllowedToShowLog() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug") || BuildConfig.BUILD_TYPE.equalsIgnoreCase("staging")) {
            return true;
        } else if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {
            return false;
        } else {
            return false;
        }
    }

    public static String printStackTrace(Exception e){
        if(isAllowedToShowLog()){
            e.printStackTrace();
        }
        return e.getMessage();
    }

    public static String printStackTrace(Throwable e) {
        if(isAllowedToShowLog()){
            e.printStackTrace();
        }
        return e.getMessage();
    }
}
