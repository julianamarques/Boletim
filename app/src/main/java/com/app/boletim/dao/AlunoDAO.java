package com.app.boletim.dao;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.app.boletim.models.Aluno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AlunoDAO {

    public static void cadastrarAluno(String nome, String email, String senha, String instituicao, String alunoId) {
        Aluno aluno = new Aluno();

        aluno.setId(alunoId);
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setSenha(senha);
        aluno.setInstituicao(instituicao);

        Map<String, Object> alunoValues = aluno.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/alunos/" + alunoId, alunoValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }

    public static void exibirAlunoLogado(FirebaseAuth auth, TextView txtVerNome, TextView txtVerEmail, TextView txtVerInstituicao) {

        ConfiguracaoFirebase.getDatabaseReference().child("alunos").orderByKey().equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Aluno aluno = snapshot.getValue(Aluno.class);
                    txtVerNome.setText(aluno.getNome());
                    txtVerEmail.setText(aluno.getEmail());
                    txtVerInstituicao.setText(aluno.getInstituicao());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
