/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.utils;

import java.sql.*;
/**
 *
 * @author UEPA_LAB6
 */
public class Conexao {
    
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    //Conectando com banco de dados
    //JDBC Driver
    static final String JBDC_DRIVER = "com.mysql.jdbc.Driver";    
    static final String DB_URL = "jdbc:mysql://localhost:3307/sispace";
    static final String USER = "root";
    static final String PASS = "usbw";
    //static final String PASS = "";
       
    public Conexao() {    
    }
    
    public Conexao(Connection conn, Statement stmt, ResultSet rs) {
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;
    }
    
    public boolean conectar() throws Exception{
        String mensagem = null;
        try {
            Class.forName(JBDC_DRIVER);                        
            System.out.println("Conectando com o banco de dados sispace ...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);            
            mensagem = "Banco de dados conectado!";
            System.out.println(mensagem);
            return true;                   
        } catch (SQLException se) {
            mensagem = "Problema ao Conectar com Banco de Dados -> " + se.getMessage();
            System.out.println(mensagem);                          
           return false;
        }                        
    }                        
   
    public boolean desconectar() throws Exception {
        try {              
            if (stmt != null) {                
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            return true;
        } catch (SQLException se) {
            String mensagem = "Problema ao tentar se desconectar do banco de dados -> " + se.getMessage();
            System.out.println(mensagem);                          
            return false;
        }
    }    
    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the stmt
     */
    public Statement getStmt() {
        return stmt;
    }

    /**
     * @param stmt the stmt to set
     */
    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    /**
     * @return the rs
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * @param rs the rs to set
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    
    
    
}
