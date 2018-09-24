/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.model;

/**
 *
 * @author jefferson
 */
public class Calendario {
    private int id;
    private Disciplinas disciplina;
    private Cronograma cronograma;
    private Eventos evento;
    private int dia;
    private int mes;
    private int ano;
    private int status;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the disciplina
     */
    public Disciplinas getDisciplina() {
        return disciplina;
    }

    /**
     * @param disciplina the disciplina to set
     */
    public void setDisciplina(Disciplinas disciplina) {
        this.disciplina = disciplina;
    }

    /**
     * @return the cronograma
     */
    public Cronograma getCronograma() {
        return cronograma;
    }

    /**
     * @param cronograma the cronograma to set
     */
    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    /**
     * @return the evento
     */
    public Eventos getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * @return the ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }    
}
