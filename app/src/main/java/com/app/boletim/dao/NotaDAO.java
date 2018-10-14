package com.app.boletim.dao;

import android.support.annotation.NonNull;

import com.app.boletim.adapters.NotasAdapter;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotaDAO {
    public static void cadastrarNota(double notaBimestral, Disciplina disciplina) {
        Nota nota = new Nota();
        String id = ConfiguracaoFirebase.getDatabaseReference().child("notas").push().getKey();

        nota.setNotaBimestral(notaBimestral);
        disciplina.getNotas().add(nota);

        Map<String, Object> disciplinaValues = disciplina.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/disciplinas/" + disciplina.getId(), disciplinaValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }

    public static List<Nota> listarNotas(NotasAdapter adapter, Disciplina disciplina) {
        final List<Nota> notas = new ArrayList<>();

        ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").child(disciplina.getId()).child("notas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notas.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Nota nota = snapshot.getValue(Nota.class);
                    notas.add(nota);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return notas;
    }
}
