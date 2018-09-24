/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.Eventos;
import br.ufpa.easoftware.tap.utils.Conexao;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author UEPA_LAB6
 */
public class DAOEventos {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<Eventos> dados;
    
    public DAOEventos() {
        conn = new Conexao();
    }
    
    public void inserir(Eventos eventos) {  
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String dataEvento = DATE_FORMAT.format(eventos.getData());        
        
        sql = "INSERT INTO eventos(id_tipo_evento, "
              + "descricao, "
              + "data, "
              + "status) "
              + "VALUES("+ eventos.getTipoEvento().getId() +",'"
              + eventos.getDescricao()+"','"+dataEvento+"','"
              + eventos.getStatus()+"')";        
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
    
    public void atualizar(Eventos eventos) {        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String dataEvento = DATE_FORMAT.format(eventos.getData()); 
        
        sql = "UPDATE eventos SET id_tipo_evento  ='"+eventos.getTipoEvento().getId()+"', "
                + "descricao='"+eventos.getDescricao()
                + "',data='"+dataEvento+"',"
                + " status='"+eventos.getStatus()+"' "
                + "WHERE id="+eventos.getId();                      
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
    
    public void delete(Eventos eventos) {        
        sql = "DELETE FROM eventos WHERE id="+eventos.getId();                      
        try {
            Conexao conn = new Conexao(); 
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();                                 
                linhasAfetadas = stmt.executeUpdate(sql);                
            }
            System.out.println("Linhas afetadas: "+ linhasAfetadas);
        } catch (Exception e) {            
            System.out.println("Erro ao excluir do banco de dados! descrição do erro:"+ e.getMessage());                                           
        }        
    } 
            
    public String[] listar(){
        sql = "SELECT * FROM eventos";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id");
                    String descricao = rs.getString("descricao");
                    Date data = rs.getDate("data");                    
                    retorno[cont] = id+"       " + descricao.trim() + "                      " + data + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem de eventos! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarEventoPorId(int id, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id";
        }else {
            campoOrdena = ordem;        
        }        
        if (id != 0) {
            sql = "SELECT * FROM eventos where id =" + id+" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM eventos order by " + campoOrdena ;
        }
        //sql = "SELECT * FROM eventos where id =" + id+" order by" + campoOrdena ;
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                                 
                    int idEve = rs.getInt("id");
                    String desc = rs.getString("descricao");
                    Date data = rs.getDate("data");                    
                    retorno[cont] = idEve+"       " + desc.trim() + "                      " + data + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarEventoPorDescricao(String descricao, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id";
        }else {
            campoOrdena = ordem;        
        }        
        if (descricao.equals("")) {
            sql = "SELECT * FROM eventos where descricao like'%" +descricao+"%' order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM eventos order by " + campoOrdena ;
        }
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                        
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id");
                    String desc = rs.getString("descricao");
                    Date data = rs.getDate("data");                    
                    retorno[cont] = id+"       " + desc.trim() + "                      " + data + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeEventoPorId(Eventos eventos){
        sql = "SELECT id, descricao FROM eventos WHERE id ="+ eventos.getId();
        int id=0;
        String descricao = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    id = rs.getInt("id");
                    descricao = rs.getString("descricao");
                }        
                eventos.setId(id);
                eventos.setDescricao(descricao);
                if (eventos.getId()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }
    
    public boolean existeEventoPorDescricao(Eventos eventos){
        String desc = null;
        int id = 0;
        sql = "SELECT id, descricao FROM eventos WHERE descricao like '%"+eventos.getDescricao()+"%'";
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id = rs.getInt("id");
                    desc = rs.getString("descricao");
                }                
                eventos.setId(id);
                eventos.setDescricao(desc);
                if (!eventos.getDescricao().equals("")) {
                    return true;
                }                               
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return false;
    }                            
    
    public List<Eventos> recuperaEventosPorMatricula(int id){                
        sql = "SELECT id_tipo_evento, descricao, data, status FROM eventos WHERE id="+id;
        List eventos = new ArrayList<Eventos>();
        Eventos evento = new Eventos();
        String descricao = null;
        int idEve = 0;
        Date data = null;
        int status = 0;        
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idEve       = rs.getInt("id");
                    descricao   = rs.getString("descricao");
                    data        = rs.getDate("data");
                    status      = rs.getInt("status");                    
                }      
                if (id != 0) {
                    evento.setId(idEve);
                    evento.setDescricao(descricao);
                    evento.setData(data);
                    evento.setStatus(status);                    
                    eventos.add(evento);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return eventos;
    }
}
