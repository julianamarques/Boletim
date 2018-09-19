package com.app.boletim.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.app.boletim.R;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edit_login_email) protected EditText editLoginEmail;
    @BindView(R.id.edit_login_senha) protected EditText editLoginSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
    }

    @OnClick(R.id.btn_logar)
    public void entrar(View view) {
        String email = editLoginEmail.getText().toString();
        String senha = editLoginSenha.getText().toString();

        Snackbar.make(view, "Logando...", Snackbar.LENGTH_SHORT).show();

        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view, "Login realizado!", Snackbar.LENGTH_SHORT).show();
                            finish();
                        }

                        else {
                            Snackbar.make(view, "Não foi possível logar!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static boolean verificarLogin(FirebaseUser user) {
        if (user != null) {
            return true;
        }

        else {
            return false;
        }
    }

    @OnClick(R.id.txt_cadastre_se)
    public void cadastrar(View view) {
        startActivity(new Intent(this, CadastroAlunoActivity.class));
    }
}
