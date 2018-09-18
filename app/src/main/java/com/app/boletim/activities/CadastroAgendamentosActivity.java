package com.app.boletim.activities;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.fragments.TimePickerFragment;
import com.app.boletim.models.Agendamento;
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

    private Agendamento agendamento;
    private long agendamentoId;
    private long idAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_agendamentos);
        ButterKnife.bind(this);

        agendamentoId = getIntent().getLongExtra("agendamentoId", -1);
        idAlunoLogado = getIdAlunoLogado();

        if(agendamentoId != -1) {
            materialCalendarView.setSelectedDate(agendamento.getData());
            editTitulo.setText(agendamento.getTitulo());
            editHora.setText(agendamento.getHora());
        }

        else {
            agendamento = new Agendamento();
        }

        configurarCalendario();
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

        if(titulo.trim().isEmpty()) {
            editTitulo.setText("Sem Título");
        }

        else if(hora.trim().isEmpty()) {
            editHora.setError("O campo não pode estar vazio");
        }

        else if(materialCalendarView.getSelectedDate().getCalendar().get(Calendar.DATE) < Calendar.getInstance().get(Calendar.DATE)) {
            Snackbar.make(view, "Você não pode selecionar uma data anterior a data de hoje!", Snackbar.LENGTH_LONG).show();
        }

        else {
            agendamento.setData(data);
            agendamento.setHora(hora);
            agendamento.setTitulo(titulo);

            finish();
        }
    }

    private long getIdAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }
}
