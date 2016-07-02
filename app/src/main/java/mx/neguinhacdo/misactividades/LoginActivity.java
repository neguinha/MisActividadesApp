package mx.neguinhacdo.misactividades;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mx.neguinhacdo.util.Util;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private MisActividadesApplication app;
    private SimpleFacebook mSimpleFacebook;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (MisActividadesApplication) getApplicationContext();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, onConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.buttonLoginGoogle);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        findViewById(R.id.buttonLoginGoogle).setOnClickListener(onClickListenerSigInGoogle);


    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void doLoginApp(View view) {
        app.registerLogInApp();
        Util.sendAndFinish(this,MainActivity.class);
    }

    public void doLoginFacebook(View view) {
        mSimpleFacebook.login(onLoginListener);
    }

    public void doLoginGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private OnLoginListener onLoginListener = new OnLoginListener() {

        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
            Profile.Properties properties = new Profile.Properties.Builder()
                    .add(Profile.Properties.ID)
                    .add(Profile.Properties.NAME)
                    .add(Profile.Properties.EMAIL)
                    .add(Profile.Properties.PICTURE)
                    .build();
            mSimpleFacebook.getProfile(properties, onProfileListener);
        }

        @Override
        public void onCancel() {
            // user canceled the dialog
        }

        @Override
        public void onFail(String reason) {
            // failed to login
        }

        @Override
        public void onException(Throwable throwable) {
            // exception from facebook
        }

    };

    OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {
            app.registerLogInFacebook(profile);
            Util.sendAndFinish(LoginActivity.this, MainActivity.class);
        }

        @Override
        public  void onThinking(){
        }

        @Override
        public void onFail(String reason){
        }
        @Override
        public void onException(Throwable throwable){
            super.onException(throwable);
        }

    };

    GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }
    };

    private View.OnClickListener onClickListenerSigInGoogle = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doLoginGoogle();
        }
    };

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleLogInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            app.registerLogInGoogle(acct);
            Util.sendAndFinish(this,MainActivity.class);
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

}
