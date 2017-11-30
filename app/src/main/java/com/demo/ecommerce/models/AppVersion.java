package com.demo.ecommerce.models;

/**
 * Created by root on 16/5/17.
 */

public class AppVersion {

    private String uat;
    private String live;
    private String appEnvironment ;


    public AppVersion(){}

    public AppVersion(String uat, String live, String appEnvironment){
        this.uat            = uat;
        this.live           = live;
        this.appEnvironment = appEnvironment;
    }


    public String getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(String appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    public String getUat() {
        return uat;
    }

    public void setUat(String uat) {
        this.uat = uat;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }
}
