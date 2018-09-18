package com.app.boletim.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Login {
    @BindView(R.id.edit_login_email) protected EditText editLoginEmail;
    @BindView(R.id.edit_login_senha) protected EditText editLoginSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_logar)
    public void entrar(View view) {
        String email = editLoginEmail.getText().toString();
        String senha = editLoginSenha.getText().toString();
    }

    @OnClick(R.id.txt_cadastre_se)
    public void cadastrar(View view) {
        startActivity(new Intent(this, CadastroAlunoActivity.class));
    }
}
