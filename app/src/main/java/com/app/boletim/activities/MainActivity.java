package com.app.boletim.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.boletim.R;
import com.app.boletim.models.Agendamento;
import com.app.boletim.models.Agendamento_;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Disciplina_;
import com.app.boletim.models.Nota;
import com.app.boletim.models.Nota_;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_disciplinas)
    public void abrirDisciplinas(View view) {
        startActivity(new Intent(this, DiscplinasActivity.class));
    }

    @OnClick(R.id.btn_agendamentos)
    public void abrirAgendamentos(View view) {
        startActivity(new Intent(this, AgendamentosActivity.class));
    }

    @OnClick(R.id.btn_seus_dados)
    public void abrirSeusDados(View view) {
        startActivity(new Intent(this, SeusDadosActivity.class));
    }

    @OnClick(R.id.btn_sair)
    public void sair(View view) {
        getSharedPreferences("boletim.file", MODE_PRIVATE).edit().clear().commit();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
