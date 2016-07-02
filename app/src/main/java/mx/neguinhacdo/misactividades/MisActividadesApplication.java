package mx.neguinhacdo.misactividades;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;

public class MisActividadesApplication extends Application {

    private static final String APP_PREFERENCES = "APP_PREFERENCES";
    private static SharedPreferences preferences;

    private static final String APP_KEY_IS_LOGIN_APP = "APP_KEY_LOGIN_APP";
    private static final String APP_KEY_IS_LOGIN_FACEBOOK = "APP_KEY_LOGIN_FACEBOOK";
    private static final String APP_KEY_IS_LOGIN_GOOGLE = "APP_KEY_LOGIN_GOOGLE";

    public static final String APP_VALUE_ID = "APP_VALUE_ID";
    public static final String APP_VALUE_NAME = "APP_VALUE_NAME";
    public static final String APP_VALUE_EMAIL = "APP_VALUE_EMAIL";
    public static final String APP_VALUE_PICTURE = "APP_VALUE_PICTURE";



    Permission[] permissions = new Permission[]{
            Permission.EMAIL
    };

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getResources().getString(R.string.facebook_app_id))
                .setNamespace("sromkuapp")
                .setPermissions(permissions)
                .build();

        SimpleFacebook.setConfiguration(configuration);
    }

    public void registerLogInApp() {
        saveValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_APP,true);
    }
    public void registerLogInFacebook(Profile profile) {
        saveValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_FACEBOOK, true);
        saveValuePreferences(MisActividadesApplication.APP_VALUE_ID, profile.getId());
        saveValuePreferences(MisActividadesApplication.APP_VALUE_NAME, profile.getName());
        saveValuePreferences(MisActividadesApplication.APP_VALUE_EMAIL, profile.getEmail());
        saveValuePreferences(MisActividadesApplication.APP_VALUE_PICTURE, profile.getPicture());
    }

    public void registerLogInGoogle(GoogleSignInAccount acct) {
        saveValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_GOOGLE, true);
        /*GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        GoogleSignInAccount acct = result.getSignInAccount();*/
        String personName = acct.getDisplayName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();

        saveValuePreferences(MisActividadesApplication.APP_VALUE_ID, personId);
        saveValuePreferences(MisActividadesApplication.APP_VALUE_NAME, personName);
        saveValuePreferences(MisActividadesApplication.APP_VALUE_EMAIL, personEmail);
        saveValuePreferences(MisActividadesApplication.APP_VALUE_PICTURE, personPhoto.toString());
    }

    public void registerLogOut() {
        saveValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_APP,false);
        saveValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_FACEBOOK,false);
        saveValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_GOOGLE,false);
    }

    public boolean isLoginAppStart(){
        return getBooleanValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_APP);
    }

    public boolean isLoginFacebookStart() {
        return getBooleanValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_FACEBOOK);
    }

    public boolean isLoginGoogleStart() {
        return getBooleanValuePreferences(MisActividadesApplication.APP_KEY_IS_LOGIN_GOOGLE);
    }

    public void saveValuePreferences(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveValuePreferences(String key, Integer value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveValuePreferences(String key, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getStringValuePreferences(String key) {
        return preferences.getString(key,null);
    }

    public Boolean getBooleanValuePreferences(String key) { return preferences.getBoolean(key,false);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        MisActividadesApplication.preferences = preferences;
    }

}
