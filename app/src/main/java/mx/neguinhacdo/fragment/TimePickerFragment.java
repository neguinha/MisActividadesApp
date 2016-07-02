package mx.neguinhacdo.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Neguinha Cdo on 27/06/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceStatefinal Calendar c = Calendar.getInstance();
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        // Crear una nueva instancia de DatePickerDialog y devolverlo
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        String texto = "la hora "+hour+":"+minute;
        Toast.makeText(getActivity(), texto,Toast.LENGTH_SHORT).show();
    }
}
