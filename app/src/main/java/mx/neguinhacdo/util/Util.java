package mx.neguinhacdo.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Neguinha Cdo on 27/06/2016.
 */
public class Util {

    public static void sendAndFinish(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity, clase);
        activity.startActivity(mainIntent);
        activity.finish();
    }

    public static void sendTo(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity, clase);
        activity.startActivity(mainIntent);
    }
}
