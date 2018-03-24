package com.app.boletim.models;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by juliana on 15/03/18.
 */
@Entity
public class Agendamento {
    @Id private long id;
    private String titulo;
    private Date data;
    private String hora;
    private String anotacao;
    private ToOne<Aluno> aluno;

    public Agendamento() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAluno(ToOne<Aluno> aluno) {
        this.aluno = aluno;
    }

    public ToOne<Aluno> getAluno() {
        return aluno;
    }
}
