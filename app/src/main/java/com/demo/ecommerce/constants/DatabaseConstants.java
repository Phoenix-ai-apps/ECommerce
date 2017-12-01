package com.demo.ecommerce.constants;

import com.demo.ecommerce.BuildConfig;

/**
 * Created by root on 28/11/17.
 */

public interface DatabaseConstants {

    String DB_PATH                = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    String DATABASE_NAME_RUNTIME  = "ecommerce_runtime.sqlite";

    int DATABASE_VERSION_RUNTIME  = 1;



    String TABLE_APP_VERSION         = "AppVersion";

    String APP_VERSION_UAT           = "Uat";
    String APP_VERSION_LIVE          = "Live";
    String APP_ENVIRONMENT           = "AppEnvironment";

    String CREATE_TABLE_APP_VERSION   =   "CREATE TABLE IF NOT EXISTS "+TABLE_APP_VERSION+" (" +
            APP_VERSION_UAT+" TEXT," +
            APP_VERSION_LIVE+" TEXT, " +
            APP_ENVIRONMENT+" TEXT " + " ) ;";


    String TABLE_CATEGORY           = "Category";

    String CATEGORY_ID              = "Id";
    String CATEGORY_NAME            = "Name";
    String CATEGORY_CHILD_CATEGORY  = "ChildCategory";

    String CREATE_TABLE_CATEGORY   =   "CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORY+" (" +
            CATEGORY_ID             +" INTEGER," +
            CATEGORY_NAME           +" TEXT, " +
            CATEGORY_CHILD_CATEGORY +" TEXT " + " ) ;";



    String TABLE_PRODUCTS           = "Products";

    String PRODUCT_RANKINGID        = "rankingID";
    String PRODUCT_NAME             = "name";
    String PRODUCT_DATE_ADDED       = "date_added";
    String PRODUCT_VARIANTS         = "variants";
    String PRODUCT_TAX              = "tax";
    String PRODUCT_VIEWID           = "viewId";
    String PRODUCT_ORDERID          = "orderId";
    String PRODUCT_SHAREID          = "shareId";

    String CREATE_TABLE_PRODUCT   =   "CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTS+" (" +
            PRODUCT_RANKINGID  +" INTEGER," +
            PRODUCT_NAME       +" TEXT, " +
            PRODUCT_DATE_ADDED +" TEXT, " +
            PRODUCT_VARIANTS   +" TEXT, " +
            PRODUCT_TAX        +" TEXT, " +
            PRODUCT_VIEWID     +" INTEGER, " +
            PRODUCT_ORDERID    +" INTEGER, " +
            PRODUCT_SHAREID    +" INTEGER " + ");";



}
