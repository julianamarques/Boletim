package com.app.boletim.activities;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dal.App;
import com.app.boletim.models.Disciplina;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroNotaFinalActivity extends AppCompatActivity {
    @BindView(R.id.edit_nota_prova_final) protected EditText editNotaProvaFinal;

    private Box<Disciplina> disciplinaBox;
    private Disciplina disciplina;
    private long disciplinaId;
    private long idAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nota_final);
        ButterKnife.bind(this);

        disciplinaBox = ((App)getApplication()).getBoxStore().boxFor(Disciplina.class);
        idAlunoLogado = getIdAlunoLogado();
        disciplinaId = getIntent().getLongExtra("disciplinaId", -1);

        if(disciplinaId != -1) {
            disciplina = disciplinaBox.get(disciplinaId);
            editNotaProvaFinal.setText(String.valueOf(disciplina.getProvaFinal()));
        }
    }

    @OnClick(R.id.btn_salvar_prova_final)
    public void salvarProvaFinal(View view) {
        String notaProvaFinal = editNotaProvaFinal.getText().toString();

        if(editNotaProvaFinal.getText().length() == 0) {
            editNotaProvaFinal.setError("O campo não pode estar vazio!");
        }

        else if(Double.valueOf(notaProvaFinal) < 0 || Double.valueOf(notaProvaFinal) > 10) {
            editNotaProvaFinal.setError("Somente valores de 0 a 10 são permitidos!");
        }

        else {
            disciplina.setProvaFinal(Double.valueOf(notaProvaFinal));
            disciplina.getAluno().setTargetId(idAlunoLogado);
            disciplinaBox.put(disciplina);

            finish();
        }
    }

    @OnClick(R.id.btn_cancelar_prova_final)
    public void cancelar(View view) {
        finish();
    }

    private long getIdAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }
}
