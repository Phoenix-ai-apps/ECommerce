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
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.utils.ApplicationUtils;
import com.demo.ecommerce.utils.DeserializeUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 10/4/17.
 */

public class ProductsDataSource implements HelperInterface {

    private static final String TAG = "ProductsDataSource";

    private SQLiteDatabase database;
    RunTimeSqLiteHelper dbHelper;

    private String[] allColumns = {
            PRODUCT_CATEGORYID   ,
            PRODUCT_RANKINGID   ,
            PRODUCT_NAME        ,
            PRODUCT_DATE_ADDED  ,
            PRODUCT_VARIANTS    ,
            PRODUCT_TAX         ,
            PRODUCT_VIEWID      ,
            PRODUCT_ORDERID     ,
            PRODUCT_SHAREID

    };


    public ProductsDataSource(Context context) {
        dbHelper = RunTimeSqLiteHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
       dbHelper.close();
    }


    public long createProducts(Products products) {

        ContentValues values = new ContentValues();

        values.put(PRODUCT_CATEGORYID , products.getCategoryID());
        values.put(PRODUCT_RANKINGID  , products.getRankingID());
        values.put(PRODUCT_NAME       , products.getName());
        values.put(PRODUCT_DATE_ADDED , products.getDate_added());
        values.put(PRODUCT_VARIANTS   , ApplicationUtils.toJson(products.getVariants()));
        values.put(PRODUCT_TAX        , ApplicationUtils.toJson(products.getTax()));
        values.put(PRODUCT_VIEWID     , products.getViewId());
        values.put(PRODUCT_ORDERID    , products.getOrderId());
        values.put(PRODUCT_SHAREID    , products.getShareId());


        long insertId = database.insert(TABLE_PRODUCTS, null, values);
        Log.i(TAG, "created products with Ranking id : " + products.getRankingID()+" and Category Id : "+products.getCategoryID());
        return insertId;
    }

    public int updateProducts(Products products) {
        ContentValues values = new ContentValues();

        values.put(PRODUCT_CATEGORYID , products.getCategoryID());
        values.put(PRODUCT_RANKINGID  , products.getRankingID());
        values.put(PRODUCT_NAME       , products.getName());
        values.put(PRODUCT_DATE_ADDED , products.getDate_added());
        values.put(PRODUCT_VARIANTS   , ApplicationUtils.toJson(products.getVariants()));
        values.put(PRODUCT_TAX        , ApplicationUtils.toJson(products.getTax()));
        values.put(PRODUCT_VIEWID     , products.getViewId());
        values.put(PRODUCT_ORDERID    , products.getOrderId());
        values.put(PRODUCT_SHAREID    , products.getShareId());

        int id = database.update(TABLE_PRODUCTS, values, null , null);
        Log.i(TAG, "updated products");
        return id;
    }



    public int deleteProducts() {
        int deleteId = database.delete(TABLE_PRODUCTS, null, null);
        System.out.println("All TABLE_PRODUCTS deleted with id: " + deleteId);
        return deleteId;

    }


    public Products getProducts() {
        Cursor cursor = database.query(TABLE_PRODUCTS, allColumns, null,null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Products products = cursorToProducts(cursor);
            return products;
        } else {
            return null;
        }
    }

    public Products getProductById(String id) {

        Cursor cursor = database.query(TABLE_PRODUCTS, allColumns, PRODUCT_RANKINGID + "=?",
                new String[]{id}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Products products = cursorToProducts(cursor);
            return products;
        } else {
            return null;
        }
    }

    public List<Products> getAllProducstsByName(String name) {
        List<Products> productses = new ArrayList<Products>();

        Cursor cursor = null;

        if(name != null && name.trim().length() > 0){
            cursor = database.query(TABLE_PRODUCTS, allColumns, PRODUCT_NAME + " LIKE ?",
                    new String[]{name+"%"}, null, null, PRODUCT_NAME + " ASC");

        }else {
            cursor = database.query(TABLE_PRODUCTS, allColumns,
                    null, null, null, null, null);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Products city = cursorToProducts(cursor);
            productses.add(city);
            cursor.moveToNext();
        }
        Log.i(TAG, "total products found : " + cursor.getCount() + " products list size : " + productses.size());
        cursor.close();

        return productses;
    }

    public List<Products> getAllProductsByCatergoryId(String id) {
        List<Products> productsList = new ArrayList<Products>();

        Cursor cursor = database.query(TABLE_PRODUCTS, allColumns, PRODUCT_CATEGORYID + "=?",
                new String[]{id}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Products products = cursorToProducts(cursor);
            productsList.add(products);
            cursor.moveToNext();
        }
        Log.i(TAG, "total Products found : " + cursor.getCount() + " Products list size : " + productsList.size());
        cursor.close();
        return productsList;
    }


    public List<Products> getAllProducts() {
        List<Products> productsList = new ArrayList<Products>();

        Cursor cursor = database.query(TABLE_PRODUCTS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Products products = cursorToProducts(cursor);
            productsList.add(products);
            cursor.moveToNext();
        }
        Log.i(TAG, "total Products found : " + cursor.getCount() + " Products list size : " + productsList.size());
        cursor.close();
        return productsList;
    }



    private Products cursorToProducts(Cursor cursor) {
        Products products = new Products();

        products.setCategoryID(cursor.getInt(0));
        products.setRankingID(cursor.getInt(1));
        products.setName(cursor.getString(2));
        products.setDate_added(cursor.getString(3));
        products.setVariants(DeserializeUtils.deserializeVariants(cursor.getString(4)));
        products.setTax(DeserializeUtils.deserializeTax(cursor.getString(5)));
        products.setViewId(cursor.getLong(6));
        products.setOrderId(cursor.getLong(7));
        products.setShareId(cursor.getLong(8));

        return products;
    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
