package com.soc.taskaro.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

public class Constants {
    public static final String USERS = "users";
    public static final String TASKARO_PREFERENCES = "TaskaroPrefs";
    public static final String LOGGED_IN_USERNAME = "logged_in_username";
    public static final String Extra_User_Details = "extra_user_details";
    public static final String Extra_Product_ID = "extra_product_details";
    public static final int READ_STORAGE_PERMISSION_CODE = 2;
    public static final int PICK_IMAGE_REQUEST_CODE = 1;
    public static final String NAME = "name";
    public static final String LAST_NAME = "lastName";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String MOBILE = "mobile";
    public static final String GENDER = "gender";
    public static final String IMAGE = "image";
    public static final String USER_PROFILE_IMAGE = "User_Profile_Image";
    public static final String COMPLETE_PROFILE = "profileCompleted";

    //Productsx
    public static final String TASKS = "tasks";
    public static final String PRODUCT_IMAGE = "Product_Image";
    public static final String USER_ID = "user_id";

    public static void showImageChooser(Activity activity) {
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(gallaryIntent, PICK_IMAGE_REQUEST_CODE);
    }

    public static String getFileExtension(Activity activity, Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.getContentResolver().getType(uri));
    }
}
