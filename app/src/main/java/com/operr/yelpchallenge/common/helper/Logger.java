package com.operr.yelpchallenge.common.helper;

import android.util.Log;

import java.io.File;

public class Logger {
    static private Logger _instance = null;

    static public Logger instance() {
        if (_instance == null) _instance = new Logger();
        return _instance;
    }

    private Logger() {

    }


    public void v(String tag, Object msg) {
        v(tag, msg, false);
    }

    public void v(String tag, Object msg, boolean writeToFile) {
		Log.v(tag, msg + "");
    }


    public void e(String tag, Object msg, boolean writeToFile) {
//		Log.e(tag, msg + "");
    }


    public static long getFileSize(File file) {
        if (!file.exists() || !file.isFile()) {
            System.out.println("File doesn\'t exist");
            return -1;
        }
        // Here we get the actual size
        return file.length();
    }


}
