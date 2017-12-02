package com.demo.ecommerce.di.modules;

import android.content.Context;

import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 29/11/17.
 */

@Module
public class DatabaseModules {

@Provides
CategoryDataSource provideCategoryDataSource(Context context){
    return new CategoryDataSource(context);
}

@Provides
ProductsDataSource  provideProductsDataSource(Context context){
    return new ProductsDataSource(context);
}


}
