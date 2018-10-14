package com.app.boletim.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juliana on 15/03/18.
 */

public class Agendamento implements Serializable {
    private String id;
    private String titulo;
    private Date data;
    private String hora;
    private String anotacao;
    private String alunoId;

    public Agendamento() {}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
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

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoId() {
        return alunoId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> agendamento = new HashMap<>();

        agendamento.put("id", id);
        agendamento.put("titulo", titulo);
        agendamento.put("data", data);
        agendamento.put("hora", hora);
        agendamento.put("anotacao", anotacao);
        agendamento.put("alunoId", alunoId);

        return agendamento;
    }
}
