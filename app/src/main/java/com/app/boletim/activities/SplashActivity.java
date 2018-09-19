package com.app.boletim.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.boletim.R;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity implements Runnable {
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this, 2000);
        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
        user = auth.getCurrentUser();
    }

    @Override
    public void run() {
        if(!LoginActivity.verificarLogin(user)) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
