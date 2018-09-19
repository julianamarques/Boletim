package com.app.boletim.dao;

import com.app.boletim.models.Disciplina;

import java.util.HashMap;
import java.util.Map;

public class DisciplinaDAO {
    private static Disciplina disciplina;

    public static void cadastrarDisciplina(String nome, String professor, boolean disciplinaExtra, String alunoId) {
        String id = ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").push().getKey();

        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setProfessor(professor);
        disciplina.setDisciplinaExtra(disciplinaExtra);
        disciplina.setAlunoId(alunoId);

        Map<String, Object> disciplinaValues = disciplina.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/disciplinas/" + id, disciplinaValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }
}
