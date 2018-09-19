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

    public double getMedia() {
        int totalDeProvas = getAluno().getQtdProvas();
        double soma = 0;
        double mediaInstitucional = getAluno().getMediaInstitucional();

        for(int i = 0; i < getNotas().size(); i++) {
            soma += getNotas().get(i).getNotaBimestral();
        }

        this.media = soma / totalDeProvas;

        if(provaFinal >= mediaInstitucional) {
            return provaFinal;
        }

        return media;
    }

    public String informarSituacao() {
        double mediaInstitucional = getAluno().getMediaInstitucional();
        double mediaPessoal = getAluno().getMediaPessoal();

        if(estaDeProvaFinal()) {
            if(getMedia() >= mediaInstitucional && getMedia() > mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final e está acima da sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() == mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final e atingiu sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() < mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final, mas ainda está abaixo da sua média pessoal.";
            }
        }

        else {
            if(getMedia() >= mediaInstitucional && getMedia() > mediaPessoal) {
                return "Parabéns! Você foi aprovado e está acima da sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() == mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final e atingiu sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() < mediaPessoal) {
                return "Parabéns! Você foi aprovado, mas ainda está abaixo da sua média pessoal.";
            }
        }

        return "Infelizmente, você reprovou.";
    }

    public boolean estaDeProvaFinal() {
        double mediaInstitucional = getAluno().getMediaInstitucional();

        if(getMedia() < mediaInstitucional && provaFinal == 0) {
            return true;
        }

        return false;
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
