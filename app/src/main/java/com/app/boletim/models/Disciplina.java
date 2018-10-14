package com.app.boletim.models;

import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juliana on 15/03/18.
 */

public class Disciplina implements Serializable {
    private String id;
    private String nome;
    private String professor;
    private double media;
    private double mediaMinima;
    private double mediaPessoal;
    private int qtdProvas;
    private double provaFinal;
    private boolean disciplinaExtra;
    private String alunoId;
    private List<Nota> notas = new ArrayList<>();

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

    public void setMediaMinima(double mediaMinima) {
        this.mediaMinima = mediaMinima;
    }

    public double getMediaMinima() {
        return mediaMinima;
    }

    public void setMediaPessoal(double mediaPessoal) {
        this.mediaPessoal = mediaPessoal;
    }

    public double getMediaPessoal() {
        return mediaPessoal;
    }

    public void setQtdProvas(int qtdProvas) {
        this.qtdProvas = qtdProvas;
    }

    public int getQtdProvas() {
        return qtdProvas;
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
        int soma = 0;

        for (int i = 0; i < notas.size(); i++) {
            soma += notas.get(i).getNotaBimestral();
        }

        if (this.qtdProvas == 0) {
            media = 0;
        }

        else {
            media = soma / qtdProvas;
        }

        return media;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> disciplina = new HashMap<>();

        disciplina.put("id", id);
        disciplina.put("nome", nome);
        disciplina.put("professor", professor);
        disciplina.put("notas", notas);
        disciplina.put("mediaMinima", mediaMinima);
        disciplina.put("mediaPessoal", mediaPessoal);
        disciplina.put("media", media);
        disciplina.put("qtdProvas", qtdProvas);
        disciplina.put("provaFinal", provaFinal);
        disciplina.put("disciplinaExtra", disciplinaExtra);
        disciplina.put("alunoId", alunoId);

        return disciplina;
    }
}
