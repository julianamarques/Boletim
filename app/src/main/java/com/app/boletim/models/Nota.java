package com.app.boletim.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by juliana on 15/03/18.
 */

@Entity
public class Nota {
    @Id private long id;
    private double notaBimestral;
    //private Double recuperacao;
    ToOne<Disciplina> disciplina;

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

    //public void setRecuperacao(Double recuperacao) {
    //    this.recuperacao = recuperacao;
    //}

    //public Double getRecuperacao() {
    //    return recuperacao;
    //}

    public void setDisciplina(ToOne<Disciplina> disciplina) {
        this.disciplina = disciplina;
    }

    public ToOne<Disciplina> getDisciplina() {
        return disciplina;
    }
}
