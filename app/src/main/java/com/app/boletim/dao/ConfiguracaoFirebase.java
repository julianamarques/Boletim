package com.app.boletim.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    public static DatabaseReference database;

    public ConfiguracaoFirebase() {}

    public static DatabaseReference getDatabaseReference(){
        if (database == null){
            database = FirebaseDatabase.getInstance().getReference();
        }

        return database;
    }
}
