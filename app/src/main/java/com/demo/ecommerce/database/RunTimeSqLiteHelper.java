package com.demo.ecommerce.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.helper.HelperInterface;


/**
 * Created by root on 28/7/17.
 */


public class RunTimeSqLiteHelper extends SQLiteOpenHelper implements HelperInterface {

    private static final String TAG = RunTimeSqLiteHelper.class.getSimpleName();


    private Context context;
    private static RunTimeSqLiteHelper instance;




    public static RunTimeSqLiteHelper getInstance(Context context) {
        if (null == instance) {
            instance = new RunTimeSqLiteHelper(context);
            instance.getWritableDatabase();
        }
        return instance;
    }



    public RunTimeSqLiteHelper(Context context) {
        super(context, DATABASE_NAME_RUNTIME, null, DATABASE_VERSION_RUNTIME);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);

        Log.e(TAG, "TABLES CREATED ON RUNTIME");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDB, int newDB) {
        if (newDB > oldDB) {
            Cursor cursor = null;

            // Category Table

            try {

                cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_CATEGORY + " limit 1", null);
                int index = cursor.getColumnIndex(CATEGORY_PRODUCTS);

                if (index == -1) {
                    sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_CATEGORY + " ADD COLUMN " +
                            CATEGORY_PRODUCTS + " TEXT");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            cursor = null;


            // Products Table
            try {

                cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_PRODUCTS + " limit 1", null);
                int index = cursor.getColumnIndex(PRODUCT_SHAREID);

                if (index == -1) {
                    sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_PRODUCTS + " ADD COLUMN " +
                            PRODUCT_SHAREID + " TEXT");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            cursor = null;





            Log.e(TAG, "NEW DB AVAILABLE, TABLES DROPED");
            onCreate(sqLiteDatabase);

        } else {
            Log.e(TAG, "RUNTIME DB ALREADY EXISTS");
        }
    }


    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}