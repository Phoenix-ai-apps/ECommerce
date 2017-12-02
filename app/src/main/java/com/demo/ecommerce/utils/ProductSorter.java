package com.demo.ecommerce.utils;

import com.demo.ecommerce.models.product.Products;

import java.util.Comparator;

public class ProductSorter implements Comparator<Products> {

    String order, share, viewed;

    public ProductSorter(String order, String share, String viewed){
        this.order = order;
        this.share = share;
        this.viewed= viewed;
    }

    @Override
    public int compare(Products one, Products another){
        int returnVal = 0;

        if(order.trim().length() > 0 && share.trim().length() > 0 && viewed.trim().length() > 0){

            if(one.getOrderId() < another.getOrderId() && one.getShareId() < another.getShareId() &&
                    one.getViewId() < another.getViewId()){
                returnVal =  1;
            }else if(one.getOrderId() > another.getOrderId() && one.getShareId() > another.getShareId() &&
                    one.getViewId() > another.getViewId()){
                returnVal =  -1;
            }else if(one.getOrderId() == another.getOrderId() && one.getShareId() == another.getShareId() &&
                    one.getViewId() == another.getViewId()){
                returnVal =  0;
            }
            return returnVal;

        }else if(order.trim().length() > 0 && share.trim().length() > 0){

            if(one.getOrderId() < another.getOrderId() && one.getShareId() < another.getShareId()){
                returnVal =  1;
            }else if(one.getOrderId() > another.getOrderId() && one.getShareId() > another.getShareId() ){
                returnVal =  -1;
            }else if(one.getOrderId() == another.getOrderId() && one.getShareId() == another.getShareId() ){
                returnVal =  0;
            }
            return returnVal;

        }else if(order.trim().length() > 0  && viewed.trim().length() > 0){

            if(one.getOrderId() < another.getOrderId() && one.getViewId() < another.getViewId()){
                returnVal =  1;
            }else if(one.getOrderId() > another.getOrderId() && one.getViewId() > another.getViewId()){
                returnVal =  -1;
            }else if(one.getOrderId() == another.getOrderId() && one.getViewId() == another.getViewId()){
                returnVal =  0;
            }
            return returnVal;

        }else if(share.trim().length() > 0  && viewed.trim().length() > 0){

            if(one.getShareId() < another.getShareId() && one.getViewId() < another.getViewId()){
                returnVal =  1;
            }else if(one.getShareId() > another.getShareId() && one.getViewId() > another.getViewId()){
                returnVal =  -1;
            }else if(one.getShareId() == another.getShareId() && one.getViewId() == another.getViewId()){
                returnVal =  0;
            }
            return returnVal;

        }else if(order.trim().length() > 0){

            if(one.getOrderId() < another.getOrderId()){
                returnVal =  1;
            }else if(one.getOrderId() > another.getOrderId()){
                returnVal =  -1;
            }else if(one.getOrderId() == another.getOrderId()){
                returnVal =  0;
            }
            return returnVal;

        }else if(share.trim().length() > 0){

            if(one.getShareId() < another.getShareId()){
                returnVal =  1;
            }else if(one.getShareId() > another.getShareId()){
                returnVal =  -1;
            }else if(one.getShareId() == another.getShareId()){
                returnVal =  0;
            }
            return returnVal;

        }else if(viewed.trim().length() > 0){

            if(one.getViewId() < another.getViewId()){
                returnVal =  1;
            }else if(one.getViewId() > another.getViewId()){
                returnVal =  -1;
            }else if(one.getViewId() == another.getViewId()){
                returnVal =  0;
            }
            return returnVal;

        }

        return returnVal;

    }


}