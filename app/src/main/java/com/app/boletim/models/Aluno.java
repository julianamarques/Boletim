package com.app.boletim.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juliana on 15/03/18.
 */

public class Aluno {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String instituicao;
    private List<Disciplina> disciplinas;
    private List<Agendamento> agendamentos;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> aluno = new HashMap<>();

        aluno.put("id", id);
        aluno.put("nome", nome);
        aluno.put("email", email);
        aluno.put("senha", senha);
        aluno.put("instituicao", instituicao);

        return aluno;
    }
}
