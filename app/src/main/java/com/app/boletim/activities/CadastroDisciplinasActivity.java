package com.app.boletim.activities;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.app.boletim.R;
import com.app.boletim.dal.App;
import com.app.boletim.models.Aluno;
import com.app.boletim.models.Disciplina;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroDisciplinasActivity extends AppCompatActivity {
    @BindView(R.id.edit_nome_disciplina) protected EditText editNomeDisciplina;
    @BindView(R.id.edit_professor) protected EditText editProfessor;
    @BindView(R.id.switch_disciplina_extra) protected Switch switchDisciplinaExtra;

    private Box<Disciplina> disciplinaBox;
    private Box<Aluno> alunoBox;
    private Aluno alunoLogado;
    private Disciplina disciplina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplinas);
        ButterKnife.bind(this);

        disciplinaBox = ((App)getApplication()).getBoxStore().boxFor(Disciplina.class);
        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Aluno.class);
        alunoLogado = getAlunoLogado();

        long disciplinaId = getIntent().getLongExtra("disciplinaId", -1);

        if(disciplinaId != -1) {
            disciplina = disciplinaBox.get(disciplinaId);

            editNomeDisciplina.setText(disciplina.getNome());
            editProfessor.setText(disciplina.getProfessor());
            switchDisciplinaExtra.setChecked(disciplina.getDisciplinaExtra());
        }

        else {
            disciplina = new Disciplina();
        }
    }

    @OnClick(R.id.btn_salvar_disciplina)
    public void salvarDisciplina(View view) {
        String nomeDisciplina = editNomeDisciplina.getText().toString();
        String professor = editProfessor.getText().toString();
        boolean ehDisciplinaExtra = switchDisciplinaExtra.isChecked();

        if(nomeDisciplina.trim().isEmpty()) {
            editNomeDisciplina.setError("O campo não pode estar vazio!");
        }

        else if(professor.trim().isEmpty()) {
            editProfessor.setError("O campo não pode estar vazio!");
        }

        else {
            disciplina.setNome(nomeDisciplina);
            disciplina.setProfessor(professor);
            disciplina.setDisciplinaExtra(ehDisciplinaExtra);
            disciplina.getAluno().setTarget(alunoLogado);

            disciplinaBox.put(disciplina);
            finish();
        }
    }

    private Aluno getAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);
        return alunoBox.get(id);
    }
}
