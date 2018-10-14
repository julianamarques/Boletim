package com.app.boletim.activities;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dao.AgendamentoDAO;
import com.app.boletim.dao.ConfiguracaoFirebase;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.app.boletim.fragments.TimePickerFragment;
import com.app.boletim.models.Agendamento;
import com.app.boletim.utils.ValidacaoCadastroAgendamento;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroAgendamentosActivity extends AppCompatActivity {
    @BindView(R.id.calendario) protected MaterialCalendarView materialCalendarView;
    @BindView(R.id.edit_titulo) protected EditText editTitulo;
    @BindView(R.id.edit_hora) protected EditText editHora;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_agendamentos);
        ButterKnife.bind(this);

        configurarCalendario();
        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
    }

    public void configurarCalendario() {
        Calendar calendar = Calendar.getInstance();

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(calendar.get(Calendar.YEAR), 0,1))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setSelectedDate(new Date());
    }

    @OnClick(R.id.edit_hora)
    public void abrirTimePicker(View view) {
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "timePicker");
    }

    @OnClick(R.id.btn_salvar_agendamento)
    public void salvarAgendamento(View view) {
        Date data = materialCalendarView.getSelectedDate().getDate();
        String hora = editHora.getText().toString();
        String titulo = editTitulo.getText().toString();

        try {
            ValidacaoCadastroAgendamento.validarCampoVazio(editTitulo, editHora);
            ValidacaoCadastroAgendamento.validarData(materialCalendarView);
            AgendamentoDAO.cadastrarAgendamento(titulo, data, hora, auth.getUid());
            finish();
        }

        catch (IllegalArgumentException e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT);
        }
    }
}
