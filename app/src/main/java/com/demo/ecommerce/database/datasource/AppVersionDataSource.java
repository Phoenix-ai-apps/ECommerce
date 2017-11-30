package com.demo.ecommerce.database.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.demo.ecommerce.database.RunTimeSqLiteHelper;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.helper.HelperInterface;
import com.demo.ecommerce.models.AppVersion;


/**
 * Created by root on 10/4/17.
 */

public class AppVersionDataSource implements HelperInterface {

    private static final String TAG = "AppVersionDataSource";

    private SQLiteDatabase database;
   // private SQLiteHelper dbHelper;

    RunTimeSqLiteHelper dbHelper;

    private String[] allColumns = {
                                   APP_VERSION_UAT,
                                   APP_VERSION_LIVE,
                                   APP_ENVIRONMENT
                                  };


    public AppVersionDataSource(Context context) {
        dbHelper = RunTimeSqLiteHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
       dbHelper.close();
    }


    public long createAppVersion(AppVersion appVersion) {

        ContentValues values = new ContentValues();

        values.put(APP_VERSION_UAT ,    appVersion.getUat());
        values.put(APP_VERSION_LIVE,    appVersion.getLive());
        values.put(APP_ENVIRONMENT ,    appVersion.getAppEnvironment());


        long insertId = database.insert(TABLE_APP_VERSION, null, values);
        Log.i(TAG, "created app version id : " + insertId + " insert id : " + insertId);
        return insertId;
    }

    public int updateAppVersion(AppVersion appVersion) {
        ContentValues values = new ContentValues();

        values.put(APP_VERSION_UAT,    appVersion.getUat());
        values.put(APP_VERSION_LIVE,    appVersion.getLive());
        values.put(APP_ENVIRONMENT ,    appVersion.getAppEnvironment());

        int id = database.update(TABLE_APP_VERSION, values, null , null);
        Log.i(TAG, "update annexure with UAT : " + appVersion.getUat() + " and live " + appVersion.getLive());
        return id;
    }



    public int deleteAppVersion() {
        int deleteId = database.delete(TABLE_APP_VERSION, null, null);
        System.out.println("All Annexure deleted with id: " + deleteId);
        return deleteId;

    }


    public AppVersion getAppVersion() {
        Cursor cursor = database.query(TABLE_APP_VERSION, allColumns, null,null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            AppVersion appVersion = cursorToClient(cursor);
            return appVersion;
        } else {
            return null;
        }
    }


    private AppVersion cursorToClient(Cursor cursor) {
        AppVersion annexure = new AppVersion();

        annexure.setUat(cursor.getString(0));
        annexure.setLive(cursor.getString(1));
        annexure.setAppEnvironment(cursor.getString(2));

        return annexure;
    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
