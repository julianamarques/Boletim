package com.app.boletim.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dao.AgendamentoDAO;
import com.app.boletim.models.Agendamento;
import com.app.boletim.models.Disciplina;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroAnotacoesActivity extends AppCompatActivity {
    @BindView(R.id.edit_anotacao) protected EditText editAnotacao;
    private Agendamento agendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anotacoes);
        ButterKnife.bind(this);

        agendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
    }

    @OnClick(R.id.btn_salvar_anotacao)
    public void salvarAnotacao(View view) {
        String anotacao = editAnotacao.getText().toString();
        AgendamentoDAO.cadastrarAnotacao(agendamento, anotacao);

        finish();
    }

    @OnClick(R.id.btn_cancelar_anotacao)
    public void cancelar(View view) {
        finish();
    }
}
