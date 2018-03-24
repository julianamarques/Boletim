package com.app.boletim.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import com.app.boletim.R;

import java.util.Calendar;


/**
 * Created by Juliana on 17/03/2018.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private EditText editHora;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hora, minuto, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int horaSelecionada, int minutoSelecionado) {
        String hora = String.valueOf(horaSelecionada);
        String minuto = String.valueOf(minutoSelecionado);

        editHora = getActivity().findViewById(R.id.edit_hora);

        if(minutoSelecionado < 10) {
            editHora.setText(hora + ":0" + minuto);
        }

        else {
            editHora.setText(hora + ":" + minuto);
        }

    }
}
