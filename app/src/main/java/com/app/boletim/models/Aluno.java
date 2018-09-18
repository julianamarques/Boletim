package com.app.boletim.models;

import java.util.List;

/**
 * Created by juliana on 15/03/18.
 */

public class Aluno {
    private long id;
    private String nome;
    private String senha;
    private String institucao;
    private String email;
    private double mediaInstitucional;
    private double mediaPessoal;
    final private static int qtdProvas = 4;
    private List<Disciplina> disciplinas;
    private List<Agendamento> agendamentos;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setInstitucao(String institucao) {
        this.institucao = institucao;
    }

    public String getInstitucao() {
        return institucao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMediaInstitucional(double mediaInstitucional) {
        this.mediaInstitucional = mediaInstitucional;
    }

    public double getMediaInstitucional() {
        return mediaInstitucional;
    }

    public void setMediaPessoal(double mediaPessoal) {
        this.mediaPessoal = mediaPessoal;
    }

    public double getMediaPessoal() {
        return mediaPessoal;
    }

    public int getQtdProvas() {
        return qtdProvas;
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
}
