package com.app.boletim.dao;

import com.app.boletim.models.Aluno;

import java.util.HashMap;
import java.util.Map;

public class AlunoDAO {
    private static Aluno aluno;

    public static void cadastrarAluno(String nome, String email, String senha, String institucao, double mediaInstitucional, double mediaPessoal, String alunoId) {
        aluno.setId(alunoId);
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setSenha(senha);
        aluno.setInstitucao(institucao);
        aluno.setMediaInstitucional(mediaInstitucional);
        aluno.setMediaPessoal(mediaPessoal);

        Map<String, Object> alunoValues = aluno.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/alunos/" + alunoId, alunoValues);
        ConfiguracaoFirebase.getDatabaseReference().updateChildren(childUpdates);
    }
}
