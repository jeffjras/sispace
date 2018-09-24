/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.Cronograma;
import br.ufpa.easoftware.tap.utils.Conexao;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author UEPA_LAB6
 */
public class DAOCronograma {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<Cronograma> dados;
    
    public DAOCronograma() {
        conn = new Conexao();
    }
    
    public void inserir(Cronograma cronograma) {  
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String dataCronograma = DATE_FORMAT.format(cronograma.getData());                
        
        sql = "INSERT INTO cronograma(data, descricao, ano_letivo, status) "
                + "VALUES("+ dataCronograma +",'"
                + cronograma.getDescricao()+"',"+cronograma.getAnoLetivo()+",'"
                + cronograma.getStatus()+"')";        
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
    
    public void atualizar(Cronograma cronograma) {        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String dataCronograma = DATE_FORMAT.format(cronograma.getData()); 
        
        sql = "UPDATE cronograma SET data ='"+dataCronograma+"',"
                + "descricao='"+cronograma.getDescricao()
                + ",ano_letivo="+cronograma.getAnoLetivo()+ "',"
                + " status="+cronograma.getStatus()
                + " WHERE id_cronograma="+cronograma.getId();                      
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
    
    public void delete(Cronograma cronograma) {        
        sql = "DELETE FROM cronograma WHERE id="+cronograma.getId();                      
        try {
            Conexao conn = new Conexao(); 
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();                                 
                linhasAfetadas = stmt.executeUpdate(sql);                
            }
            System.out.println("Linhas afetadas: "+ linhasAfetadas);
        } catch (Exception e) {            
            System.out.println("Erro ao excluir produto no banco de dados! descrição do erro:"+ e.getMessage());                                           
        }        
    } 
            
    public String[] listar(){
        sql = "SELECT * FROM cronograma";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id");
                    Date dataCronograma = rs.getDate("data");
                    String descricao = rs.getString("descricao");
                    //int anoLetivo = rs.getInt("ano_letivo");
                    //int status = rs.getInt("status");                    
                    retorno[cont] = id+"       " + descricao.trim() + "                      " + dataCronograma + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarCronogramaPorId(int id, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id";
        }else {
            campoOrdena = ordem;        
        }        
        if (id != 0) {
            sql = "SELECT * FROM cronograma where id =" + id +" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM cronograma order by " + campoOrdena ;
        }
        //sql = "SELECT * FROM alunos where matricula =" + matricula+" order by" + campoOrdena ;
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                                 
                    int idCrono = rs.getInt("id");
                    Date dataCronograma = rs.getDate("data");
                    String descricao = rs.getString("descricao");                    
                    retorno[cont] = idCrono+"       " + dataCronograma + "                      " + descricao.trim() + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarCronogramaPorDescricao(String descricao, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id";
        }else {
            campoOrdena = ordem;        
        }        
        if (descricao.equals("")) {
            sql = "SELECT * FROM cronograma where descricao like'%" +descricao+"%' order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM cronograma order by " + campoOrdena ;
        }       
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                        
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int idCrono = rs.getInt("id");
                    Date dataCronograma = rs.getDate("data");
                    String desc = rs.getString("descricao");                    
                    retorno[cont] = idCrono+"       " + desc.trim() + "                      " + dataCronograma + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeCronogramaPorId(Cronograma cronograma){
        sql = "SELECT id, descricao FROM cronograma WHERE id ="+ cronograma.getId();
        int idCrono=0;
        String descricao = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    idCrono = rs.getInt("id");
                    descricao = rs.getString("descricao");
                }        
                cronograma.setId(idCrono);
                cronograma.setDescricao(descricao);
                if (cronograma.getId()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }
    
    public boolean existeCronogramaPorDescricao(Cronograma cronograma){
        String descricao = null;
        int idCrono = 0;
        sql = "SELECT id, descricao FROM cronograma WHERE descricao like '%"+cronograma.getDescricao()+"%'";
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idCrono = rs.getInt("id");
                    descricao = rs.getString("descricao");
                }                
                cronograma.setId(idCrono);
                cronograma.setDescricao(descricao);
                if (!cronograma.getDescricao().equals("")) {
                    return true;
                }                               
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return false;
    }                            
    
    public List<Cronograma> recuperaCronogramaPorId(int id){                
        sql = "SELECT id, data, descricao, ano_letivo, status FROM cronograma WHERE id="+id;
        List cronogramas = new ArrayList<Cronograma>();
        Cronograma cronograma = new Cronograma();
        String desc = null;
        int idCrono = 0;
        Date data  = null;
        int  anoLetivo = 0;
        int status = 0;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idCrono         = rs.getInt("id");
                    data            = rs.getDate("data");
                    desc            = rs.getString("descricao");
                    anoLetivo       = rs.getInt("ano_letivo");
                    status          = rs.getInt("status");                    
                }      
                if (idCrono != 0) {
                    cronograma.setId(idCrono);
                    cronograma.setDescricao(desc);
                    cronograma.setAnoLetivo(anoLetivo);
                    cronograma.setStatus(status);                    
                    cronogramas.add(cronograma);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return cronogramas;
    }
}
