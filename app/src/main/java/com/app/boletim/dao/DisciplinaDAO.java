package com.app.boletim.dao;

import android.support.annotation.NonNull;

import com.app.boletim.adapters.DisciplinasAdapter;
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

public class DisciplinaDAO {

    public static void cadastrarDisciplina(String nome, String professor, boolean disciplinaExtra, String alunoId, double mediaMinima, double mediaPessoal, int qtdProvas) {
        Disciplina disciplina = new Disciplina();
        String id = ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").push().getKey();

        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setProfessor(professor);
        disciplina.setDisciplinaExtra(disciplinaExtra);
        disciplina.setMediaMinima(mediaMinima);
        disciplina.setMediaPessoal(mediaPessoal);
        disciplina.setQtdProvas(qtdProvas);
        disciplina.setAlunoId(alunoId);

        Map<String, Object> disciplinaValues = disciplina.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/disciplinas/" + id, disciplinaValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }

    public static List<Disciplina> listarDisciplinas(FirebaseAuth auth, DisciplinasAdapter adapter) {
        final List<Disciplina> disciplinas = new ArrayList<>();

        ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").orderByChild("alunoId").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                disciplinas.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Disciplina disciplina = snapshot.getValue(Disciplina.class);
                    disciplinas.add(disciplina);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return disciplinas;
    }

    public static void deletarDisciplina(String disciplinaId) {
        ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").orderByChild("id").equalTo(disciplinaId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void editarDisciplina(String id, String nome, String professor, boolean disciplinaExtra, double mediaMinima, double mediaPessoal, int qtdProvas, String idDisciplina, List<Nota> notas) {
        Disciplina disciplina = new Disciplina();

        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setProfessor(professor);
        disciplina.setDisciplinaExtra(disciplinaExtra);
        disciplina.setMediaMinima(mediaMinima);
        disciplina.setMediaPessoal(mediaPessoal);
        disciplina.setNotas(notas);
        disciplina.setQtdProvas(qtdProvas);

        Map<String, Object> disciplinaValues = disciplina.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/disciplinas/" + idDisciplina, disciplinaValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }
}
