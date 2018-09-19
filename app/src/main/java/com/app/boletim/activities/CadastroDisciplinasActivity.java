package com.app.boletim.activities;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
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
import com.app.boletim.dao.DisciplinaDAO;
import com.app.boletim.models.Aluno;
import com.app.boletim.models.Disciplina;
import com.app.boletim.utils.ValidacaoCadastroDisciplina;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroDisciplinasActivity extends AppCompatActivity {
    @BindView(R.id.edit_nome_disciplina) protected EditText editNomeDisciplina;
    @BindView(R.id.edit_professor) protected EditText editProfessor;
    @BindView(R.id.switch_disciplina_extra) protected Switch switchDisciplinaExtra;

    private Aluno alunoLogado;
    private Disciplina disciplina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplinas);
        ButterKnife.bind(this);

        long disciplinaId = getIntent().getLongExtra("disciplinaId", -1);

        if(disciplinaId != -1) {

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
        String nome = editNomeDisciplina.getText().toString();
        String professor = editProfessor.getText().toString();
        boolean disciplinaExtra = switchDisciplinaExtra.isChecked();


        try {
            ValidacaoCadastroDisciplina.validarCampoVazio(editNomeDisciplina, editProfessor);
            DisciplinaDAO.cadastrarDisciplina(nome, professor, disciplinaExtra, "");
            finish();
        }

        catch (IllegalArgumentException e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT);
        }
    }

    private String getAlunoIdLogado() {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        return preferences.getString("alunoId", "");

    }
}
