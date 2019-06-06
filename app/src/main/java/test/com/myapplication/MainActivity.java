package test.com.myapplication;

import android.app.Activity;
import android.os.Bundle;

import io.mob.resu.reandroidsdk.MRegisterUser;
import io.mob.resu.reandroidsdk.ReAndroidSDK;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void onUserRegistration() {
        MRegisterUser userDetails = new MRegisterUser();
        userDetails.setEmail(" User Email  ");
        userDetails.setDeviceToken(" User Device token FCM Or GCM ");
        userDetails.setName(" User Name  ");
        userDetails.setGender(" User Gender ");
        userDetails.setAge(" User Age ");
        userDetails.setPhone(" User Phone ");
        ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails);

    }

    private void onCustomEvents() {
        ReAndroidSDK.getInstance(this).onTrackEvent("EventName");
    }

    private void onLocationUpdate() {
        //User location
        ReAndroidSDK.getInstance(this).onLocationUpdate(13.0827, 80.2707);
    }


}
