package com.app.boletim.models;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by juliana on 15/03/18.
 */

@Entity
public class Aluno {
    @Id private long id;
    private String nome;
    private String senha;
    private String institucao;
    private String email;
    private double mediaInstitucional;
    private double mediaPessoal;
    final private int qtdProvas = 4;
    @Backlink private ToMany<Disciplina> disciplinas;
    @Backlink private ToMany<Agendamento> agendamentos;

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

    /*public void setQtdProvas(int qtdProvas) {
        this.qtdProvas = qtdProvas;
    }*/

    public int getQtdProvas() {
        return qtdProvas;
    }

    public void setDisciplinas(ToMany<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public ToMany<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setAgendamentos(ToMany<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public ToMany<Agendamento> getAgendamentos() {
        return agendamentos;
    }
}
