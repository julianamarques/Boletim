package com.app.boletim.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dao.AlunoDAO;
import com.app.boletim.dao.ConfiguracaoFirebase;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.app.boletim.models.Aluno;
import com.app.boletim.utils.ValidacaoCadastroAluno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroAlunoActivity extends AppCompatActivity {
    @BindView(R.id.edit_nome_aluno) protected EditText editNomeAluno;
    @BindView(R.id.edit_email) protected EditText editEmail;
    @BindView(R.id.edit_senha) protected EditText editSenha;
    @BindView(R.id.edit_instituicao) protected EditText editInstituicao;
    @BindView(R.id.edit_media_institucional) protected EditText editMediaInstitucional;
    @BindView(R.id.edit_media_pessoal) protected EditText editMediaPessoal;
    @BindView(R.id.btn_salvar_aluno) protected Button btnSalvarAluno;

    private Aluno aluno;
    private FirebaseAuth auth;
    private String alunoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);
        ButterKnife.bind(this);
        long id = getIntent().getLongExtra("alunoId", -1);

        if(id != -1) {
            editNomeAluno.setText(aluno.getNome());
            editEmail.setText(aluno.getEmail());
            editSenha.setText(aluno.getSenha());
            editInstituicao.setText(aluno.getInstitucao());
            editMediaInstitucional.setText(String.valueOf(aluno.getMediaInstitucional()));
            editMediaPessoal.setText(String.valueOf(aluno.getMediaPessoal()));
        }

        else {
            aluno = new Aluno();
        }

        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
    }

    @OnClick(R.id.btn_salvar_aluno)
    public void salvarAluno(View view) {
        String nome = editNomeAluno.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String instituicao = editInstituicao.getText().toString();
        String mediaInstitucional = editMediaInstitucional.getText().toString();
        String mediaPessoal = editMediaPessoal.getText().toString();

        try {
            auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(CadastroAlunoActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                alunoId = auth.getUid();
                                ValidacaoCadastroAluno.validarCampoVazio(editNomeAluno, editEmail, editSenha, editInstituicao, editMediaInstitucional, editMediaPessoal);
                                AlunoDAO.cadastrarAluno(nome, email, senha, instituicao, Double.valueOf(mediaInstitucional), Double.valueOf(mediaPessoal), alunoId);
                                finish();
                            }

                            else {
                                Snackbar.make(view, "Não foi possível cadastrar!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        catch (IllegalArgumentException e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT);
        }
    }
}
