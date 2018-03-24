package com.app.boletim.models;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by juliana on 15/03/18.
 */

@Entity
public class Disciplina {
    @Id private long id;
    private String nome;
    private String professor;
    private double media;
    private double provaFinal;
    private boolean disciplinaExtra;
    private ToOne<Aluno> aluno;
    @Backlink private ToMany<Nota> notas;

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

    public void setNotas(ToMany<Nota> notas) {
        this.notas = notas;
    }

    public ToMany<Nota> getNotas() {
        return notas;
    }

    public void setAluno(ToOne<Aluno> aluno) {
        this.aluno = aluno;
    }

    public ToOne<Aluno> getAluno() {
        return aluno;
    }

    public double getMedia() {
        int totalDeProvas = getAluno().getTarget().getQtdProvas();
        double soma = 0;
        double mediaInstitucional = getAluno().getTarget().getMediaInstitucional();

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
        double mediaInstitucional = getAluno().getTarget().getMediaInstitucional();
        double mediaPessoal = getAluno().getTarget().getMediaPessoal();

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
        double mediaInstitucional = getAluno().getTarget().getMediaInstitucional();

        if(getMedia() < mediaInstitucional && provaFinal == 0) {
            return true;
        }

        return false;
    }
}
