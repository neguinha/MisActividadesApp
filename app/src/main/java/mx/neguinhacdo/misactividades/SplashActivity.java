package mx.neguinhacdo.misactividades;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.neguinhacdo.util.Util;

public class SplashActivity extends AppCompatActivity {

    private MisActividadesApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        app = (MisActividadesApplication) getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(app.isLoginAppStart() || app.isLoginFacebookStart()) {
                    Util.sendAndFinish(SplashActivity.this, MainActivity.class);
                } else {
                    Util.sendAndFinish(SplashActivity.this, LoginActivity.class);
                }
            }
        }, 2000);
    }
}
