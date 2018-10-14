package com.app.boletim.dao;

import android.support.annotation.NonNull;

import com.app.boletim.adapters.AgendamentosAdapter;
import com.app.boletim.models.Agendamento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendamentoDAO {
    public static void cadastrarAgendamento(String titulo, Date data, String hora, String alunoId) {
        Agendamento agendamento = new Agendamento();
        String id = ConfiguracaoFirebase.getDatabaseReference().child("agendamentos").push().getKey();

        agendamento.setId(id);
        agendamento.setTitulo(titulo);
        agendamento.setData(data);
        agendamento.setHora(hora);
        agendamento.setAlunoId(alunoId);

        Map<String, Object> agendamentoValues = agendamento.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/agendamentos/" + id, agendamentoValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }


    public static void cadastrarAnotacao(Agendamento agendamento, String anotacao) {
        agendamento.setAnotacao(anotacao);

        Map<String, Object> agendamentoValues = agendamento.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/agendamentos/" + agendamento.getId(), agendamentoValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }

    public static List<Agendamento> listarAgendamentos(FirebaseAuth auth, AgendamentosAdapter adapter) {
        final List<Agendamento> agendamentos = new ArrayList<>();

        ConfiguracaoFirebase.getDatabaseReference().child("agendamentos").orderByChild("alunoId").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                agendamentos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Agendamento agendamento = snapshot.getValue(Agendamento.class);
                    agendamentos.add(agendamento);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return agendamentos;
    }

    public static void deletarAgendamento(String agendamentoId) {
        ConfiguracaoFirebase.getDatabaseReference().child("agendamentos").orderByChild("id").equalTo(agendamentoId).addValueEventListener(new ValueEventListener() {
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

    public static void editarAgendamento() {}
}
