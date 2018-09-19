package com.app.boletim.dao;

import com.app.boletim.models.Agendamento;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AgendamentoDAO {
    private static Agendamento agendamento;

    public static void cadastrarAgendamento(String titulo, Date data, String hora, String alunoId) {
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
}
