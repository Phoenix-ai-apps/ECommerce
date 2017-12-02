package com.demo.ecommerce.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;


/**
 * Created by root on 28/11/17.
 */

public class ApplicationUtils {


    private static final String TAG = ApplicationUtils.class.getSimpleName();


    public static Toast showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();
        return toast;
    }

    public static Toast showToastSmall(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();
        return toast;
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static boolean isValidJson(String jsonString) {
        Gson gson = new Gson();
        try {
            gson.fromJson(jsonString, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }


}
