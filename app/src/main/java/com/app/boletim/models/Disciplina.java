package com.app.boletim.models;

import java.util.List;

/**
 * Created by juliana on 15/03/18.
 */

public class Disciplina {
    private long id;
    private String nome;
    private String professor;
    private double media;
    private double provaFinal;
    private boolean disciplinaExtra;
    private Aluno aluno;
    private List<Nota> notas;

    public Disciplina() {}

    public Disciplina(String nome, String professor) {
        this.nome = nome;
        this.professor = professor;
    }

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

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aluno getAluno() {
        return aluno;
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
}
