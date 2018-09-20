package com.app.boletim.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.models.Agendamento;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroAnotacoesActivity extends AppCompatActivity {
    @BindView(R.id.edit_anotacao) protected EditText editAnotacao;

    private Agendamento agendamento;
    private long idAlunoLogado;
    private long agendamentoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anotacoes);
        ButterKnife.bind(this);

        idAlunoLogado = getIdAlunoLogado();
        agendamentoId = getIntent().getLongExtra("agendamentoId", -1);

        if(agendamentoId != -1) {
            editAnotacao.setText(agendamento.getAnotacao());
        }
    }

    @OnClick(R.id.btn_salvar_anotacao)
    public void salvarAnotacao(View view) {
        String anotacao = editAnotacao.getText().toString();
        agendamento.setAnotacao(anotacao);

        finish();
    }

    @OnClick(R.id.btn_cancelar_anotacao)
    public void cancelar(View view) {
        finish();
    }

    private long getIdAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }
}
