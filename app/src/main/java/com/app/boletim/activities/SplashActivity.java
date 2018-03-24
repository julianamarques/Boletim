package com.app.boletim.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.boletim.R;

public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if(!estaLogado()) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private boolean estaLogado() {
        final SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        final long alunoId = preferences.getLong("alunoId", -1);

        return alunoId != -1;
    }
}
