package com.app.boletim.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.boletim.R;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
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
        auth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
