package com.app.boletim.models;

import java.io.Serializable;

/**
 * Created by juliana on 15/03/18.
 */

public class Nota implements Serializable {
    private String id;
    private double notaBimestral;
    private Disciplina disciplina;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
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
