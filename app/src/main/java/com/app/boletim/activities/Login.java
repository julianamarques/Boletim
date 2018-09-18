package com.app.boletim.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.app.boletim.activities.MainActivity;
import com.app.boletim.models.Aluno;

/**
 * Created by juliana on 15/03/18.
 */

public class Login extends AppCompatActivity {
    public void logar(Aluno aluno) {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("alunoId", aluno.getId());

        editor.commit();

        startActivity(new Intent(this, MainActivity.class));
    }
}
