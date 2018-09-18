package com.app.boletim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.models.Nota;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroNotasActivity extends AppCompatActivity {
    @BindView(R.id.edit_nota) protected EditText editNota;

    private Nota nota;
    private long notaId;
    private long disciplinaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_notas);
        ButterKnife.bind(this);
        disciplinaId = getIntent().getLongExtra("disciplinaId", -1);
        notaId = getIntent().getLongExtra("notaId", -1);

        if(notaId != -1) {
            editNota.setText(String.valueOf(nota.getNotaBimestral()));
        }

        else {
            nota = new Nota();
        }
    }

    @OnClick(R.id.btn_salvar_nota)
    public void salvarNota(View view) {
        String notaBim = editNota.getText().toString();

        if(editNota.getText().length() == 0) {
            editNota.setError("O campo não pode estar vazio!");
        }

        else if(Double.valueOf(notaBim) < 0 || Double.valueOf(notaBim) > 10) {
            editNota.setError("Somente valores de 0 a 10 são permitidos!");
        }

        else {
            nota.setNotaBimestral(Double.valueOf(notaBim));
            nota.getDisciplina();

            finish();
        }
    }

    @OnClick(R.id.btn_cancelar_nota)
    public void cancelar(View view) {
        finish();
    }
}
