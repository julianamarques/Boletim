package com.app.boletim.utils;

import android.widget.EditText;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class ValidacaoCadastroAgendamento {
    public static void validarCampoVazio(EditText titulo, EditText hora) {
        if (titulo.getText().toString().isEmpty() || hora.getText().toString().isEmpty()) {
            throw new IllegalArgumentException("O campo não pode estar vazio");
        }
    }

    public static void validarData(MaterialCalendarView materialCalendarView) {
        if (materialCalendarView.getSelectedDate().getCalendar().get(Calendar.DATE) < Calendar.getInstance().get(Calendar.DATE)) {
            throw new IllegalArgumentException("Você não pode selecionar uma data anterior a data de hoje!");
        }
    }
}
