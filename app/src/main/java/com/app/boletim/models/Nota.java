package com.app.boletim.models;

/**
 * Created by juliana on 15/03/18.
 */

public class Nota {
    private long id;
    private double notaBimestral;
    private Disciplina disciplina;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setNotaBimestral(double notaBimestral) {
        this.notaBimestral = notaBimestral;
    }

    public double getNotaBimestral() {
        return notaBimestral;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }
}
