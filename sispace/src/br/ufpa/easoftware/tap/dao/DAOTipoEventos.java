/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.TipoEventos;
import br.ufpa.easoftware.tap.utils.Conexao;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author UEPA_LAB6
 */
public class DAOTipoEventos {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<TipoEventos> dados;
    
    public DAOTipoEventos() {
        conn = new Conexao();
    }
    
    public void inserir(TipoEventos tipoEventos) {              
        sql = "INSERT INTO tipo_eventos (descricao) VALUES('"+ tipoEventos.getDescricao() +"')";        
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
    
    public void atualizar(TipoEventos tipoEventos) {                
        sql = "UPDATE tipo_eventos SET descricao ='"+tipoEventos.getDescricao()+"' WHERE id_tipo_eventos="+tipoEventos.getId();                      
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
    
    public void delete(TipoEventos tipoEventos) {        
        sql = "DELETE FROM tipo_eventos WHERE id_tipo_eventos="+tipoEventos.getId();                      
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
        sql = "SELECT * FROM tipo_eventos";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_tipo_eventos");
                    String descricao = rs.getString("descricao");                                   
                    retorno[cont] = id+"       " + descricao.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarTipoEventosPorId(int idTipoEventos, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id_tipo_eventos";
        }else {
            campoOrdena = ordem;        
        }        
        if (idTipoEventos != 0) {
            sql = "SELECT * FROM tipo_eventos where id_tipo_eventos =" + idTipoEventos+" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM tipo_eventos order by " + campoOrdena ;
        }        
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                                 
                    int id = rs.getInt("id_tipo_eventos");
                    String descricao = rs.getString("descricao");                                     
                    retorno[cont] = id+"       " + descricao.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarTipoEventoPorDescricao(String descricao, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "descricao";
        }else {
            campoOrdena = ordem;        
        }        
        if (descricao.equals("")) {
            sql = "SELECT * FROM tipo_eventos where descricao like'%" +descricao+"%' order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM tipo_eventos order by " + campoOrdena ;
        }              
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                        
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_tipo_eventos");
                    String desc = rs.getString("descricao");                                    
                    retorno[cont] = id+"       " + desc.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeTipoEventosPorId(TipoEventos tipoEventos){
        sql = "SELECT id_tipo_eventos, descricao FROM tipo_eventos WHERE id_tipo_eventos ="+ tipoEventos.getId();
        int id = 0;
        String descricao = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    id = rs.getInt("id_tipo_eventos");
                    descricao = rs.getString("descricao");
                }        
                tipoEventos.setId(id);
                tipoEventos.setDescricao(descricao);
                if (tipoEventos.getId()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }
    
    public boolean existeTipoEventosPorDescricao(TipoEventos tipoEventos){
        String descricao = null;
        int id = 0;
        sql = "SELECT id_tipo_eventos, descricao FROM tipo_eventos WHERE descricao like '%"+tipoEventos.getDescricao()+"%'";
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id = rs.getInt("id_tipoEventos");
                    descricao = rs.getString("descricao");
                }                
                tipoEventos.setId(id);
                tipoEventos.setDescricao(descricao);
                if (!tipoEventos.getDescricao().equals("")) {
                    return true;
                }                               
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return false;
    }                            
    
    public List<TipoEventos> recuperaTipoEventosPorId(int idTipoEventos){                
        sql = "SELECT id_tipo_eventos, descricao FROM tipo_eventos WHERE id_tipo_eventos="+idTipoEventos;
        List listTipoEventos = new ArrayList<TipoEventos>();
        TipoEventos tipoEventos = new TipoEventos();
        String descricao = null;
        int id = 0;        
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id             = rs.getInt("id_tipo_eventos");
                    descricao      = rs.getString("descricao");                    
                }      
                if (id != 0) {
                    tipoEventos.setId(id);
                    tipoEventos.setDescricao(descricao);                    
                    listTipoEventos.add(tipoEventos);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return listTipoEventos;
    }
}
