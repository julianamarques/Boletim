package com.app.boletim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dao.NotaDAO;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroNotasActivity extends AppCompatActivity {
    @BindView(R.id.edit_nota) protected EditText editNota;

    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_notas);
        ButterKnife.bind(this);

        disciplina = (Disciplina) getIntent().getSerializableExtra("disciplina");
    }

    @OnClick(R.id.btn_salvar_nota)
    public void salvarNota(View view) {
        String notaBim = editNota.getText().toString();

        NotaDAO.cadastrarNota(Double.valueOf(notaBim), disciplina);

        finish();
    }

    @OnClick(R.id.btn_cancelar_nota)
    public void cancelar(View view) {
        finish();
    }
}
