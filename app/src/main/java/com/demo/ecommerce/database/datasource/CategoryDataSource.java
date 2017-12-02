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
import com.demo.ecommerce.models.category.Categories;
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.utils.ApplicationUtils;
import com.demo.ecommerce.utils.DeserializeUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 10/4/17.
 */

public class CategoryDataSource implements HelperInterface {

    private static final String TAG = "CategoryDataSource";

    private SQLiteDatabase database;
    RunTimeSqLiteHelper dbHelper;

    private String[] allColumns = {
                                   CATEGORY_ID    ,
                                   CATEGORY_NAME  ,
                                   CATEGORY_CHILD_CATEGORY,
                                   CATEGORY_PRODUCTS
                                  };


    public CategoryDataSource(Context context) {
        dbHelper = RunTimeSqLiteHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
       dbHelper.close();
    }


    public long createCategory(Categories categories) {

        ContentValues values = new ContentValues();

        values.put(CATEGORY_ID             , categories.getId());
        values.put(CATEGORY_NAME           , categories.getName());
        values.put(CATEGORY_CHILD_CATEGORY , ApplicationUtils.toJson(categories.getChild_categories()));
        values.put(CATEGORY_PRODUCTS       , ApplicationUtils.toJson(categories.getProducts()));


        long insertId = database.insert(TABLE_CATEGORY, null, values);
        Log.i(TAG, "created Category id : " + insertId + " insert id : " + insertId);
        return insertId;
    }

    public int updateCategory(Categories categories) {
        ContentValues values = new ContentValues();

        values.put(CATEGORY_ID             , categories.getId());
        values.put(CATEGORY_NAME           , categories.getName());
        values.put(CATEGORY_CHILD_CATEGORY , ApplicationUtils.toJson(categories.getChild_categories()));
        values.put(CATEGORY_PRODUCTS       , ApplicationUtils.toJson(categories.getProducts()));

        int id = database.update(TABLE_CATEGORY, values, null , null);
        Log.i(TAG, "updated category");
        return id;
    }



    public int deleteCategory() {
        int deleteId = database.delete(TABLE_CATEGORY, null, null);
        System.out.println("All TABLE_CATEGORY deleted with id: " + deleteId);
        return deleteId;

    }


    public Categories getCategoryById(String id) {
        Cursor cursor = database.query(TABLE_CATEGORY, allColumns, CATEGORY_ID + "=?",
                new String[]{id}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Categories categories = cursorToCategories(cursor);
            return categories;
        } else {
            return null;
        }
    }

    public List<Products> getAllProductsByCatergoryId(String id) {
        List<Products> productsList = new ArrayList<Products>();

        Cursor cursor = database.query(TABLE_CATEGORY, allColumns, CATEGORY_ID + "=?",
                new String[]{id}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Categories products = cursorToCategories(cursor);
            productsList.addAll(products.getProducts());
            cursor.moveToNext();
        }
        Log.i(TAG, "total Products found : " + cursor.getCount() + " Products list size : " + productsList.size());
        cursor.close();
        return productsList;
    }


    public List<Categories> getAllCategories() {
        List<Categories> categoriesList = new ArrayList<Categories>();

        Cursor cursor = database.query(TABLE_CATEGORY, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Categories categories = cursorToCategories(cursor);
            categoriesList.add(categories);
            cursor.moveToNext();
        }
        Log.i(TAG, "total Category found : " + cursor.getCount() + " Category list size : " + categoriesList.size());
        cursor.close();
        return categoriesList;
    }



    private Categories cursorToCategories(Cursor cursor) {
        Categories categories = new Categories();

        categories.setId(cursor.getInt(0));
        categories.setName(cursor.getString(1));
        categories.setChild_categories(DeserializeUtils.deserializeStringJsonArray(cursor.getString(2)));
        categories.setProducts(DeserializeUtils.deserializeProductsList(cursor.getString(3)));

        return categories;
    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
