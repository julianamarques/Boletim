package com.app.boletim.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juliana on 15/03/18.
 */

public class Disciplina {
    private String id;
    private String nome;
    private String professor;
    private double media;
    private double provaFinal;
    private boolean disciplinaExtra;
    private String alunoId;
    private List<Nota> notas;

    public Disciplina() {}

    public Disciplina(String nome, String professor) {
        this.nome = nome;
        this.professor = professor;
    }

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

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProvaFinal(double provaFinal) {
        this.provaFinal = provaFinal;
    }

    public double getProvaFinal() {
        return provaFinal;
    }

    public void setDisciplinaExtra(boolean disciplinaExtra) {
        this.disciplinaExtra = disciplinaExtra;
    }

    public boolean getDisciplinaExtra() {
        return disciplinaExtra;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public double getMedia() { ;



        return media;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> disciplina = new HashMap<>();

        disciplina.put("id", id);
        disciplina.put("nome", professor);
        disciplina.put("media", media);
        disciplina.put("provaFinal", provaFinal);
        disciplina.put("disciplinaExtra", disciplinaExtra);

        return disciplina;
    }
}
