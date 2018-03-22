package com.weqar.weqar.Global_url_weqar;

import android.bluetooth.BluetoothAssignedNumbers;

import java.security.PublicKey;

/**
 * Created by andriod on 20/2/18.
 */

public class Global_URL {
    public static  String Base_url ="http://weqar.co/webapi/";
    public static String Image_URL="http://weqar.co/webAPI/api/";

    public static  String User_signup= Base_url + "api/user/register";
    public static  String User_signin= Base_url + "api/user/mobilelogin";
    public static String User_insertbasicinfo=Base_url+"api/user/add";
    public static String User_insertprofessinalinfo=Base_url+"api/user/userprofessional";
    public static String User_uploadprofessionalimage=Base_url+"api/file/upload";
    public static String User_subscriptiondet_get=Base_url+"api/userplan/get";
    public static String user_insert_completedetails=Base_url+"api/usersubscription";
    public static String user_show_discount=Base_url+"api/Discounts/viewall";
    public static String user_show_jobs=Base_url+"api/job/viewall";


    public static String Vendor_insertprofessionalinfo=Base_url+"api/vendor/professional";
    public static String Vendor_complete_chooseplan=Base_url+"api/vendor/discountplan";
    public static String vendor_insert_completedetails=Base_url+"api/Discounts/add";
    public static String Vendor_showown_discounts=Base_url+"api/discounts/get";
    public  static  String Vendor_select_categ=Base_url+"api/vendor/category";
    public  static  String Vendor_insert_second_Discount=Base_url+"api/discounts/add";
    public  static  String Vendor_getjobtype=Base_url+"api/system/jobtypes";
    public  static  String Vendor_getjobfield=Base_url+"api/system/jobfields";
    public  static  String Vendor_addjobs=Base_url+"Api/job/add";
    public  static  String Vendor_showownjobs=Base_url+"api/job/get";



        public static String Image_url_load=Image_URL+"file/view?filename=";

}
