package com.app.boletim.activities;

import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dal.App;
import com.app.boletim.models.Aluno;
import com.app.boletim.models.Login;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroAlunoActivity extends Login {
    @BindView(R.id.edit_nome_aluno) protected EditText editNomeAluno;
    @BindView(R.id.edit_email) protected EditText editEmail;
    @BindView(R.id.edit_senha) protected EditText editSenha;
    @BindView(R.id.edit_instituicao) protected EditText editInstituicao;
    @BindView(R.id.edit_media_institucional) protected EditText editMediaInstitucional;
    @BindView(R.id.edit_media_pessoal) protected EditText editMediaPessoal;
    @BindView(R.id.btn_salvar_aluno) protected Button btnSalvarAluno;

    private Box<Aluno> alunoBox;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);
        ButterKnife.bind(this);

        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Aluno.class);
        long id = getIntent().getLongExtra("alunoId", -1);

        if(id != -1) {
            aluno = alunoBox.get(id);

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
    }

    @OnClick(R.id.btn_salvar_aluno)
    public void salvarAluno(View view) {
        try {
            String mediaInstitucional = editMediaInstitucional.getText().toString();
            String mediaPessoal = editMediaPessoal.getText().toString();
            String nomeAluno = editNomeAluno.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();
            String instituicao = editInstituicao.getText().toString();

            aluno.setNome(nomeAluno);
            aluno.setEmail(email);
            aluno.setSenha(senha);
            aluno.setInstitucao(instituicao);
            aluno.setMediaInstitucional(Double.valueOf(mediaInstitucional));
            aluno.setMediaPessoal(Double.valueOf(mediaPessoal));

            if(nomeAluno.trim().isEmpty()) {
                editNomeAluno.setError("O campo não pode estar vazio!");
            }

            else if(email.trim().isEmpty()) {
                editEmail.setError("O campo não pode estar vazio!");
            }

            else if(senha.trim().isEmpty()) {
                editSenha.setError("O campo não pode estar vazio!");
            }

            else if(instituicao.trim().isEmpty()) {
                editInstituicao.setError("O campo não pode estar vazio!");
            }

            else if(mediaInstitucional.trim().isEmpty()) {
                editMediaInstitucional.setError("O campo não pode estar vazio!");
            }

            else if(mediaPessoal.trim().isEmpty() || mediaInstitucional.trim().isEmpty()) {
                editMediaPessoal.setError("O campo não pode estar vazio!");
            }

            else if(Double.valueOf(mediaPessoal) < Double.valueOf(mediaInstitucional)) {
                editMediaPessoal.setError("Média pessoal não pode ser menor que a média Institucional!");
            }

            else {
                alunoBox.put(aluno);

                finish();

                logar(aluno);
            }
        }

        catch (NumberFormatException e) {
            Snackbar.make(view, "Média pessoal, média institucional e quantidade de provas não podem estar vazios!", Snackbar.LENGTH_LONG).show();
        }
    }
}
