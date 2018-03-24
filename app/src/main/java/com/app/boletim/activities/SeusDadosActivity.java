package com.app.boletim.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.dal.App;
import com.app.boletim.models.Aluno;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

public class SeusDadosActivity extends AppCompatActivity {
    @BindView(R.id.txt_ver_nome) TextView txtVerNome;
    @BindView(R.id.txt_ver_email) TextView txtVerEmail;
    @BindView(R.id.txt_ver_instituicao) TextView txtVerInstituicao;
    @BindView(R.id.txt_ver_media_instituicao) TextView txtVerMediaMediaInstituicao;
    @BindView(R.id.txt_ver_media_pessoal) TextView txtVerMediaPessoal;
    @BindView(R.id.txt_ver_qtd_provas) TextView txtVerQtdProvas;

    private Box<Aluno> alunoBox;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seus_dados);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Aluno.class);
        aluno = obterAlunoLogado();

        txtVerNome.setText(aluno.getNome());
        txtVerEmail.setText(aluno.getEmail());
        txtVerInstituicao.setText(aluno.getInstitucao());
        txtVerMediaMediaInstituicao.setText(String.valueOf(aluno.getMediaInstitucional()));
        txtVerMediaPessoal.setText(String.valueOf(aluno.getMediaPessoal()));
        txtVerQtdProvas.setText(String.valueOf(aluno.getQtdProvas()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_aluno, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editar_aluno) {
            editar();
        }

        return false;
    }

    private void editar() {
        Intent intent = new Intent(this, CadastroAlunoActivity.class);
        intent.putExtra("alunoId", obterAlunoLogado().getId());

        startActivity(intent);
    }

    private Aluno obterAlunoLogado(){
        final SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return alunoBox.get(id);
    }
}
