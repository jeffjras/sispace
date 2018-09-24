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
public class TipoEventos {
    private int id;
    private String descricao;    

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
     * @return the nome
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param nome the nome to set
     */
    public void setDescricao(String nome) {
        this.descricao = nome;
    }              
}
