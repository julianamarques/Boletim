package com.app.boletim.dao;

import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;

import java.util.HashMap;
import java.util.Map;

public class NotaDAO {
    private static Nota nota;

    public static void cadastrarNota(double notaBimestral, Disciplina disciplina) {
        String id = ConfiguracaoFirebase.getDatabaseReference().child("notas").push().getKey();

        nota.setNotaBimestral(notaBimestral);
        disciplina.getNotas().add(nota);

        Map<String, Object> disciplinaValues = disciplina.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/disciplinas/" + disciplina.getId(), disciplinaValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }
}
