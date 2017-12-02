package com.demo.ecommerce.utils;

import com.demo.ecommerce.gsonAdapters.DoubleTypeAdapter;
import com.demo.ecommerce.gsonAdapters.FloatTypeAdapter;
import com.demo.ecommerce.gsonAdapters.IntegerTypeAdapter;
import com.demo.ecommerce.gsonAdapters.LongTypeAdapter;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.helper.HelperInterface;
import com.demo.ecommerce.models.category.Categories;
import com.demo.ecommerce.models.EcommerceModel;
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.models.product.Tax;
import com.demo.ecommerce.models.product.Variants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by root on 28/11/17.
 */

public class DeserializeUtils implements HelperInterface {


    private Gson gson;

    @Inject
    public DeserializeUtils(Gson gson) {
        this.gson = gson;
    }


    public static Gson getGsonObject() {
        return new GsonBuilder()
                .registerTypeAdapter(long.class, new LongTypeAdapter())
                .registerTypeAdapter(double.class, new DoubleTypeAdapter())
                .registerTypeAdapter(float.class, new FloatTypeAdapter())
                .registerTypeAdapter(int.class, new IntegerTypeAdapter())
                .create();
    }


    public static EcommerceModel deserializeEcommerceResponse(String json){

        EcommerceModel object = null;
        try {
            object = getGsonObject().fromJson(json, EcommerceModel.class);
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }


    public static String[] deserializeStringJsonArray(String json){

        String[] object = null;
        try {
            object = getGsonObject().fromJson(json, String[].class);
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }


    public static Categories deserializeCategories(String json){

        Categories object = null;
        try {
            object = getGsonObject().fromJson(json, Categories.class);
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }


    public static Products deserializeProducts(String json){

        Products object = null;
        try {
            object = getGsonObject().fromJson(json, Products.class);
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }

    public static List<Products> deserializeProductsList(String json){

        List<Products> object = null;
        try {
            object = getGsonObject().fromJson(json, new TypeToken<List<Products>>() {}.getType());
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }


    public static List<Variants> deserializeVariants(String json){

        List<Variants> object = null;
        try {
            object = getGsonObject().fromJson(json, new TypeToken<List<Variants>>() {}.getType());
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }

    public static Tax deserializeTax(String json){

        Tax object = null;
        try {
            object = getGsonObject().fromJson(json, Tax.class);
        } catch (Exception e) {
            StackTraceWriter.printStackTrace(e);
        }
        return object;
    }



    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
