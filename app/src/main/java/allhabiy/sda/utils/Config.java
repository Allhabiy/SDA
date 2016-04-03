package allhabiy.sda.utils;


public interface Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://m7sn.com/sda/app/login.php";
    public static final String GET_ALL_USER1_URL = "http://m7sn.com/sda/app/get_all_needy.php";
    public static final String UPDATE_USER1_LOCATION_URL = "http://m7sn.com/sda/app/update_needy_location.php";
    public static final String UPDATE_USER2_LOCATION_URL = "http://m7sn.com/sda/app/update_Donator_location.php";
    public static final String GET_ALL_USER_LOCATIONS_URL = "http://m7sn.com/sda/app/get_all_needy_locations.php";
    public static final String GET_ALL_BOX_URL = "http://m7sn.com/sda/app/get_all_box.php";
    public static final String UPDATE_BOX_LOCATION_URL = "http://m7sn.com/sda/app/update_box_location.php";


    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_NATIONAL_ID = "nationlid";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "nationlid";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //TEST@ if whether the person who login in is the needy
    public static final String LOGIN_NEEDY = "needy";
    //TEST@ if whether the person who login in is the doner
    public static final String LOGIN_DONER = "doner";
    //TEST@ if whether the person who login in is the doner
    public static final String LOGIN_EMP = "employee";


}