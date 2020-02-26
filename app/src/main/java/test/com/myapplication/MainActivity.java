package test.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.mob.resu.reandroidsdk.IDeepLinkInterface;
import io.mob.resu.reandroidsdk.MRegisterUser;
import io.mob.resu.reandroidsdk.RNotification;
import io.mob.resu.reandroidsdk.ReAndroidSDK;

public class MainActivity extends Activity implements View.OnClickListener {

    String activityName;
    String fragmentName;
    JSONObject customParams;
    ArrayList<RNotification> rNotificationsModel = new ArrayList<>();
    ArrayList<JSONObject> notificationsByObject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        ReAndroidSDK.getInstance(this).getCampaignData(new IDeepLinkInterface() {

            /**
             * App new Install from Play Store
             */
            @Override
            public void onInstallDataReceived(String data) {
                parseData(data);

            }

            /**
             * App Launch Smart link click from Email or SMS
             */
            @Override
            public void onDeepLinkData(String data) {

                parseData(data);

            }

            /**
             *  User tapped the notification
             */
            @Override
            public void onNotificationData(String data) {
                parseData(data);
            }
        });

    }

    private void Init() {
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_custom).setOnClickListener(this);
        findViewById(R.id.btn_get_notification).setOnClickListener(this);
        findViewById(R.id.btn_delete_notification).setOnClickListener(this);
        findViewById(R.id.btn_location).setOnClickListener(this);
    }


    private void parseData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            activityName = jsonObject.optString("activityName");
            fragmentName = jsonObject.optString("fragmentName");
            customParams = new JSONObject(jsonObject.optString("customParams"));

            Log.e("ActivityName", activityName);
            Log.e("fragmentName", fragmentName);
            Log.e("parseDatacustomParams", ""+customParams);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Resulticks User registration
     */
    private void userRegister() {
        MRegisterUser userDetails = new MRegisterUser();
        userDetails.setUserUniqueId("BZ003728"); // * Unique Id is the mandatory
        userDetails.setName("Buvaneswaran P");
        userDetails.setEmail("buvanesh.special@gmail.com"); // * Phone number OR Email Id is the mandatory
        userDetails.setPhone("+91 9786483047"); // *
        userDetails.setAge("30");
        userDetails.setGender("Male");
        userDetails.setProfileUrl("https://pngimg.com/uploads/light/light_PNG14440.png");
        userDetails.setDob("18Jan1990");
        userDetails.setEducation("PG");
        userDetails.setEmployed(true);
        userDetails.setMarried(true);
        userDetails.setDeviceToken("Push FCM Or GCM ");
        ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails);
    }

    /**
     * For Developer understanding
     * Notification Payload  & Custom parameters Sample
     * Same payload format will be come from resulticks platform
     */
    private void addNotification() {
        try {

            // Custom Parameters the screen pre requested parameters list
            JSONObject customParams = new JSONObject();
            customParams.put("customParamKey1", "Value1");
            customParams.put("customParamKey2", 100);
            customParams.put("customParamKey3", 20.90);
            customParams.put("customParamKey4", true);

            String title = "Notification Title";
            String description = "Notification Description";
            // The activity name should be with the package name. otherwise the notification does not work.
            String activityName = "test.com.myapplication.MainActivity";
            String fragmentName = "HomeFragment";
            ReAndroidSDK.getInstance(this).addNewNotification(title, description, activityName, fragmentName, customParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Whatever the event brand want track, use this method to track the events
     * i.e Product purchased, Beacon detected, Add to cart, Removed from cart, Payment declined
     * Add to favorite, Barcode Scanned, etc..
     */
    private void addCustomEvent() {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("productId", 100);
        objectHashMap.put("productName", "iPhone");
        objectHashMap.put("price", 30000);
        objectHashMap.put("offer", true);
        objectHashMap.put("discount", 22.50);
        ReAndroidSDK.getInstance(MainActivity.this).onTrackEvent(objectHashMap, "ProductViewed");
    }

    /**
     * Get resulticks notifications
     */
    private void getNotificationList() {


        // Get Notification JSON object list
        // notificationsByObject = ReAndroidSDK.getInstance(this).getNotificationByObject();

        // Get Notification model object list
        rNotificationsModel = ReAndroidSDK.getInstance(this).getNotifications();


        Log.e("List Count", "" + rNotificationsModel.size());

    }

    /**
     * Delete resulticks notifications
     */
    private void onDelete() {

        getNotificationList();


        if (rNotificationsModel.size() > 0) {

            try {

                // Delete Notification By model object
                // ReAndroidSDK.getInstance(this).deleteNotification(rNotificationsModel.get(0));

                // Delete Notification By Json object
                // ReAndroidSDK.getInstance(this).deleteNotificationByObject(notificationsByObject.get(0));

                // Delete Notification By CampaignId
                // ReAndroidSDK.getInstance(this).deleteNotificationByCampaignId(rNotificationsModel.get(0).getCampaignId());

                // Delete Notification By NotificationId
                // ReAndroidSDK.getInstance(this).deleteNotificationByNotificationId(notificationsByObject.get(0).getString("notificationId"));


                ReAndroidSDK.getInstance(this).deleteNotificationByCampaignId(rNotificationsModel.get(0).getCampaignId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "The notification list is empty", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * User location tracking
     */
    private void onLocationUpdate() {
        //User location
        ReAndroidSDK.getInstance(this).onLocationUpdate(13.0827, 80.2707);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register:
                userRegister();
                break;

            case R.id.btn_add:
                addNotification();
                break;

            case R.id.btn_get_notification:
                getNotificationList();
                break;

            case R.id.btn_delete_notification:
                onDelete();
                break;

            case R.id.btn_custom:
                addCustomEvent();
                break;

            case R.id.btn_location:
                onLocationUpdate();
                break;

            default:
                break;


        }


        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });

        findViewById(R.id.btn_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomEvent();
            }
        });
        findViewById(R.id.btn_get_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotificationList();
            }
        });
        findViewById(R.id.btn_delete_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
    }
}
