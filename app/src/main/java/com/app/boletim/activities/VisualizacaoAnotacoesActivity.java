package com.app.boletim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.dal.App;
import com.app.boletim.models.Agendamento;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

public class VisualizacaoAnotacoesActivity extends AppCompatActivity {
    @BindView(R.id.txt_ver_anotacao) TextView txtVerAnotacao;

    private Box<Agendamento> agendamentoBox;
    private Agendamento agendamento;
    private long agendamentoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_anotacoes);
        ButterKnife.bind(this);

        agendamentoBox = ((App)getApplication()).getBoxStore().boxFor(Agendamento.class);
        agendamentoId = getIntent().getLongExtra("agendamentoId", -1);

        if(agendamentoId != -1) {
            agendamento = agendamentoBox.get(agendamentoId);

            getSupportActionBar().setTitle(agendamento.getTitulo());
            txtVerAnotacao.setText(agendamento.getAnotacao());
        }
    }
}
