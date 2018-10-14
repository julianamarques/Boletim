package com.app.boletim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.dao.AlunoDAO;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeusDadosActivity extends AppCompatActivity {
    @BindView(R.id.txt_ver_nome) protected TextView txtVerNome;
    @BindView(R.id.txt_ver_email) protected TextView txtVerEmail;
    @BindView(R.id.txt_ver_instituicao) protected TextView txtVerInstituicao;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seus_dados);
        ButterKnife.bind(this);

        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
        AlunoDAO.exibirAlunoLogado(auth, txtVerNome, txtVerEmail, txtVerInstituicao);
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

        }

        return false;
    }

}
