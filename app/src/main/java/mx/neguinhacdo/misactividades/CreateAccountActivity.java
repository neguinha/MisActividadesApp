package mx.neguinhacdo.misactividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;

public class CreateAccountActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        /*mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/
    }
}
