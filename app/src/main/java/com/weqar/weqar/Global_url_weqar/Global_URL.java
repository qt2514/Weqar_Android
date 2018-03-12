package com.weqar.weqar.Global_url_weqar;

import android.bluetooth.BluetoothAssignedNumbers;

/**
 * Created by andriod on 20/2/18.
 */

public class Global_URL {
    public static  String Base_url ="http://weqar.co/webapi/";
    public static  String User_signup= Base_url + "api/user/register";
    public static  String User_signin= Base_url + "api/user/mobilelogin";
    public static String User_insertbasicinfo=Base_url+"api/user/add";
    public static String User_insertprofessinalinfo=Base_url+"api/user/userprofessional";
    public static String Vendor_insertprofessionalinfo=Base_url+"api/vendor/professional";


    public static String User_uploadprofessionalimage=Base_url+"api/file/upload";
    public static String User_subscriptiondet_get=Base_url+"api/userplan/get";


}
