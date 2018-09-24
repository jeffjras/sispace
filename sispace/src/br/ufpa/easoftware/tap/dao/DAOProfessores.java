/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.Professores;
import br.ufpa.easoftware.tap.utils.Conexao;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author UEPA_LAB6
 */
public class DAOProfessores {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<Professores> dados;
    
    public DAOProfessores() {
        conn = new Conexao();
    }
    
    public void inserir(Professores professor) {              
        sql = "INSERT INTO professores (nome, status) VALUES('"+ professor.getNome() +"',"
              + professor.getStatus()+")";        
        try {                         
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();                                 
                linhasAfetadas = stmt.executeUpdate(sql);                                                
            }                                      
            System.out.println("Dados Incluídos com sucesso. Linha(s) afetada(s): " + linhasAfetadas);            
            conn.desconectar();
        } catch (Exception e) {
            e.printStackTrace();  
            System.out.println("Erro ao realizar inserção no banco de dados! descrição do erro:"+ e.getMessage());                                          
        }           
    }     
    
    public void atualizar(Professores professor) {                
        sql = "UPDATE professores SET nome ='"+professor.getNome()+"',status="+professor.getStatus()+
                      " WHERE id_professor="+professor.getId();                      
        try {
            Conexao conn = new Conexao(); 
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();                                 
                linhasAfetadas = stmt.executeUpdate(sql);                
            }
            System.out.println("Dados Atualizados com sucesso. Linha(s) afetada(s): " + linhasAfetadas);            
            conn.desconectar();            
        } catch (Exception e) {            
            System.out.println("Erro ao realizar atualização no banco de dados! descrição do erro: "+ e.getMessage());                                            
        }        
    } 
    
    public void delete(Professores professor) {        
        sql = "DELETE FROM professores WHERE id_professor="+professor.getId();                      
        try {
            Conexao conn = new Conexao(); 
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();                                 
                linhasAfetadas = stmt.executeUpdate(sql);                
            }
            System.out.println("Linhas afetadas: "+ linhasAfetadas);
        } catch (Exception e) {            
            System.out.println("Erro ao excluir no banco de dados! descrição do erro:"+ e.getMessage());                                           
        }        
    } 
            
    public String[] listar(){
        sql = "SELECT * FROM professores";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_professor");
                    String nome = rs.getString("nome");                                   
                    retorno[cont] = id+"       " + nome.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarProfessorPorId(int idProfessor, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id_professor";
        }else {
            campoOrdena = ordem;        
        }        
        if (idProfessor != 0) {
            sql = "SELECT * FROM professores where id_professor =" + idProfessor+" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM professores order by " + campoOrdena ;
        }        
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                                 
                    int id = rs.getInt("id_professor");
                    String nome = rs.getString("nome");                                     
                    retorno[cont] = id+"       " + nome.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarProfessorPorNome(String nome, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "nome";
        }else {
            campoOrdena = ordem;        
        }        
        if (nome.equals("")) {
            sql = "SELECT * FROM professores where nome like'%" +nome+"%' order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM professores order by " + campoOrdena ;
        }              
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                        
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_professor");
                    String nomeProf = rs.getString("nome");                                    
                    retorno[cont] = id+"       " + nomeProf.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeProfessorPorId(Professores professor){
        sql = "SELECT id_professor, nome FROM professores WHERE id_professor ="+ professor.getId();
        int id = 0;
        String nome = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    id = rs.getInt("id_professor");
                    nome = rs.getString("nome");
                }        
                professor.setId(id);
                professor.setNome(nome);
                if (professor.getId()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }
    
    public boolean existeProfessorPorNome(Professores professor){
        String nome = null;
        int id = 0;
        sql = "SELECT id_professor, nome FROM professores WHERE nome like '%"+professor.getNome()+"%'";
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id = rs.getInt("id_professor");
                    nome = rs.getString("nome");
                }                
                professor.setId(id);
                professor.setNome(nome);
                if (!professor.getNome().equals("")) {
                    return true;
                }                               
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return false;
    }                            
    
    public List<Professores> recuperaProfessorPorId(int idProfessor){                
        sql = "SELECT id_professor, nome, status FROM professores WHERE id_professor="+idProfessor;
        List listProfessor = new ArrayList<Professores>();
        Professores professor = new Professores();
        String nome = null;
        int id = 0;
        int status = 0;        
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id             = rs.getInt("id_professor");
                    nome           = rs.getString("nome");
                    status         = rs.getInt("status");
                }      
                if (id != 0) {
                    professor.setId(id);
                    professor.setNome(nome);
                    professor.setStatus(status);                    
                    listProfessor.add(professor);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return listProfessor;
    }
}
