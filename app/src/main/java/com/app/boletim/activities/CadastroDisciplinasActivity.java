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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.app.boletim.dao.DisciplinaDAO;
import com.app.boletim.interfaces.Editavel;
import com.app.boletim.models.Aluno;
import com.app.boletim.models.Disciplina;
import com.app.boletim.utils.ValidacaoCadastroDisciplina;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class CadastroDisciplinasActivity extends AppCompatActivity implements Editavel {
    @BindView(R.id.edit_nome_disciplina) protected EditText editNomeDisciplina;
    @BindView(R.id.edit_professor) protected EditText editProfessor;
    @BindView(R.id.switch_disciplina_extra) protected Switch switchDisciplinaExtra;
    @BindView(R.id.edit_media_minima) protected EditText editMediaMinima;
    @BindView(R.id.edit_media_pessoal) protected EditText editMediaPessoal;
    @BindView(R.id.seek_qtd_provas) protected SeekBar seekQtdProvas;
    @BindView(R.id.txt_label_qtd_provas) protected TextView txtLabelQtdProvas;
    @BindView(R.id.txt_qtd_provas) protected TextView txtQtdProvas;

    private Disciplina disciplina;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplinas);
        ButterKnife.bind(this);

        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
        atualizarValorSeekBar(seekQtdProvas);

        disciplina = (Disciplina) getIntent().getSerializableExtra("disciplina");

        if (ehEditavel(disciplina)) {
            setarValoresEditaveis(disciplina);
            getSupportActionBar().setTitle("Editar Disciplina");
        }
    }

    @OnCheckedChanged(R.id.switch_disciplina_extra)
    public void alternarTipoDisciplina() {
        if (switchDisciplinaExtra.isChecked()) {
            editMediaMinima.setVisibility(View.GONE);
            editMediaPessoal.setVisibility(View.GONE);
            seekQtdProvas.setVisibility(View.GONE);
            txtLabelQtdProvas.setVisibility(View.GONE);
            txtQtdProvas.setVisibility(View.GONE);
        }

        else {
            editMediaMinima.setVisibility(View.VISIBLE);
            editMediaPessoal.setVisibility(View.VISIBLE);
            seekQtdProvas.setVisibility(View.VISIBLE);
            txtLabelQtdProvas.setVisibility(View.VISIBLE);
            txtQtdProvas.setVisibility(View.VISIBLE);
        }
    }

    public void atualizarValorSeekBar(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                txtQtdProvas.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.btn_salvar_disciplina)
    public void salvarDisciplina(View view) {
        String nome = editNomeDisciplina.getText().toString();
        String professor = editProfessor.getText().toString();
        String mediaMinima = editMediaMinima.getText().toString();
        String mediaPessoal = editMediaPessoal.getText().toString();
        String qtdProvas = txtQtdProvas.getText().toString();

        if (editMediaMinima.getText().toString().trim().isEmpty() || editMediaPessoal.getText().toString().trim().isEmpty()) {
            editMediaMinima.setText("0");
            editMediaPessoal.setText("0");
            txtQtdProvas.setText("0");
        }

        boolean disciplinaExtra = switchDisciplinaExtra.isChecked();

        try {
            ValidacaoCadastroDisciplina.validarCampoVazio(editNomeDisciplina, editProfessor);

            if (ehEditavel(disciplina)) {
                DisciplinaDAO.editarDisciplina(disciplina.getId(), nome, professor, disciplinaExtra, Double.valueOf(mediaMinima), Double.valueOf(mediaPessoal), Integer.valueOf(qtdProvas), disciplina.getId(), disciplina.getNotas());
            }

            else {
                DisciplinaDAO.cadastrarDisciplina(nome, professor, disciplinaExtra, auth.getUid(), Double.valueOf(mediaMinima), Double.valueOf(mediaPessoal), Integer.valueOf(qtdProvas));
            }

            finish();
        }

        catch (IllegalArgumentException e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean ehEditavel(Object object) {
        if (object != null) {
            return true;
        }

        return false;
    }


    public void setarValoresEditaveis(Disciplina disciplina) {
        editNomeDisciplina.setText(disciplina.getNome());
        editProfessor.setText(disciplina.getProfessor());
        switchDisciplinaExtra.setChecked(disciplina.getDisciplinaExtra());
        editMediaMinima.setText(String.valueOf(disciplina.getMediaMinima()));
        editMediaPessoal.setText(String.valueOf(disciplina.getMediaPessoal()));
        seekQtdProvas.setProgress(disciplina.getQtdProvas());
        txtQtdProvas.setText(String.valueOf(disciplina.getQtdProvas()));
    }
}
