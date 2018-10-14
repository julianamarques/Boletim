package com.app.boletim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.models.Agendamento;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VisualizacaoAnotacoesActivity extends AppCompatActivity {
    @BindView(R.id.txt_ver_anotacao) protected TextView txtVerAnotacao;

    private Agendamento agendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_anotacoes);
        ButterKnife.bind(this);

        agendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(agendamento.getTitulo());

        if (agendamento.getAnotacao().isEmpty()) {
            txtVerAnotacao.setText("");
        }

        else {
            txtVerAnotacao.setText(agendamento.getAnotacao());
        }
    }
}
