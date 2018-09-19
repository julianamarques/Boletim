package com.app.boletim.dao;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebaseAuth {
    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }
}
