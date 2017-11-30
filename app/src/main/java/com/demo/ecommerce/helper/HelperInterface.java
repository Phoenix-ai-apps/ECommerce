package com.demo.ecommerce.helper;

import com.demo.ecommerce.constants.AppConstants;
import com.demo.ecommerce.constants.DatabaseConstants;
import com.demo.ecommerce.constants.UrlConstants;

/**
 * Created by root on 27/11/17.
 */

public interface HelperInterface extends AppConstants,UrlConstants,DatabaseConstants {

    ApplicationHelper getHelper();

}
