/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.Disciplinas;
import br.ufpa.easoftware.tap.utils.Conexao;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author UEPA_LAB6
 */
public class DAODisciplinas {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<Disciplinas> dados;
    
    public DAODisciplinas() {
        conn = new Conexao();
    }
    
    public void inserir(Disciplinas disciplina) {                   
        sql = "INSERT INTO disciplinas(id_professor,\n" +
                                       "nome,\n" +
                                       "carga_horaria,ementa,\n" +
                                       "creditos,\n" +
                                        "status) \n"
                      + "VALUES("+ disciplina.getProfessor().getId() +",'"
                                 + disciplina.getNome()+"',"+disciplina.getCargaHoraria()+","
                                 + disciplina.getCreditos()+","+disciplina.getStatus()+")";        
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
    
    public void atualizar(Disciplinas disciplina) {        
        sql = "UPDATE disciplinas SET id_professor ='"+disciplina.getProfessor().getId()+"',nome='"+disciplina.getNome()+
                      ",carga_horaria="+disciplina.getCargaHoraria()+"', ementa="+disciplina.getEmenta()+", creditos="+disciplina.getCreditos()+" status="+disciplina.getStatus()+" WHERE id_disciplina="+disciplina.getId();                      
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
    
    public void delete(Disciplinas disciplina) {        
        sql = "DELETE FROM disciplinas WHERE id_disciplina="+disciplina.getId();                      
        try {
            Conexao conn = new Conexao(); 
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();                                 
                linhasAfetadas = stmt.executeUpdate(sql);                
            }
            System.out.println("Linhas afetadas: "+ linhasAfetadas);
        } catch (Exception e) {            
            System.out.println("Erro ao excluir item no banco de dados! descrição do erro:"+ e.getMessage());                                           
        }        
    } 
            
    public String[] listar(){
        sql = "SELECT * FROM disciplinas";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_disciplina");
                    String nome = rs.getString("nome");
                    int cargaHoraria = rs.getInt("carga_horaria");                    
                    retorno[cont] = id + "       " + nome.trim() + "                      " + cargaHoraria + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarDisciplinaPorId(int idDisciplina, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id_disciplina";
        }else {
            campoOrdena = ordem;        
        }        
        if (idDisciplina != 0) {
            sql = "SELECT * FROM disciplinas where id_disciplina =" + idDisciplina +" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM disciplinas order by " + campoOrdena ;
        }        
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                                 
                    int id = rs.getInt("id_disciplina");
                    String nome = rs.getString("nome");
                    int cargaHoraria = rs.getInt("carga_horaria");                    
                    retorno[cont] = id+"       " + nome.trim() + "                      " + cargaHoraria + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarDisciplinaPorNome(String nome, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id_disciplina";
        }else {
            campoOrdena = ordem;        
        }        
        if (nome.equals("")) {
            sql = "SELECT * FROM disciplinas where nome like'%" +nome+"%' order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM disciplinas order by " + campoOrdena ;
        }            
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                        
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_disciplina");
                    String nomeDisciplina = rs.getString("nome");
                    int cargaHoraria = rs.getInt("carga_horaria");                    
                    retorno[cont] = id+"       " + nomeDisciplina.trim() + "                      " + cargaHoraria + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeDisciplinaPorId(Disciplinas disciplina){
        sql = "SELECT id_disciplina, nome FROM disciplinas WHERE id_disciplina ="+ disciplina.getId();
        int idDisciplina=0;
        String nome = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    idDisciplina = rs.getInt("id_disciplina");
                    nome = rs.getString("nome");
                }        
                disciplina.setId(idDisciplina);
                disciplina.setNome(nome);
                if (disciplina.getId()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }
    
    public boolean existeDisciplinaPorNome(Disciplinas disciplina){
        String nome = null;
        int idDisciplina = 0;
        sql = "SELECT id_disciplina, nome FROM disciplinas WHERE nome like '%"+disciplina.getNome()+"%'";
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idDisciplina = rs.getInt("id_disciplina");
                    nome = rs.getString("nome");
                }                
                disciplina.setId(idDisciplina);
                disciplina.setNome(nome);
                if (!disciplina.getNome().equals("")) {
                    return true;
                }                               
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return false;
    }                            
    
    public List<Disciplinas> recuperaDisciplinaPorId(int idDisciplina){                
        sql = "SELECT id_disciplina, id_professor, nome, carga_horaria, ementa, creditos, status FROM disciplinas WHERE id_disciplina="+idDisciplina;
        List listDisciplina = new ArrayList<Disciplinas>();
        Disciplinas disciplina = new Disciplinas();
        int id = 0;
        int idProfessor = 0;
        String nome = null;        
        int cargaHoraria = 0;
        String ementa = null;
        int creditos = 0;
        int status = 0;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id              = rs.getInt("id_disciplina");
                    idProfessor     = rs.getInt("id_professor");
                    nome            = rs.getString("nome");
                    cargaHoraria    = rs.getInt("carga_horaria");
                    ementa          = rs.getString("ementa");
                    creditos        = rs.getInt("creditos");
                    status          = rs.getInt("status");                    
                }      
                if (id != 0) {
                    disciplina.setId(id);
                    disciplina.getProfessor().setId(idProfessor);
                    disciplina.setNome(nome);
                    disciplina.setCargaHoraria(cargaHoraria);
                    disciplina.setEmenta(ementa);
                    disciplina.setCreditos(creditos);
                    disciplina.setStatus(status);
                    listDisciplina.add(disciplina);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return listDisciplina;
    }
}
