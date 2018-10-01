/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.Calendario;
import br.ufpa.easoftware.tap.model.Cronograma;
import br.ufpa.easoftware.tap.model.Disciplinas;
import br.ufpa.easoftware.tap.model.Eventos;
import br.ufpa.easoftware.tap.utils.Conexao;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author UEPA_LAB6
 */
public class DAOCalendario {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<Calendario> dados;
    
    public DAOCalendario() {
        conn = new Conexao();
    }
    
    public void inserir(Calendario calendario) {                  
        sql = "INSERT INTO calendario(id_disciplina, id_cronograma, "
              + "id_evento, dia, mes, ano, status) "
              + "VALUES("
              + calendario.getDisciplina().getId() +","
              + calendario.getCronograma().getId()
              +","+calendario.getEvento().getId()
              +","+calendario.getDia() 
              +", "+ calendario.getMes() +","+ calendario.getAno() 
              +", "+calendario.getStatus()+")";        
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
    
    public void atualizar(Calendario calendario) {                       
        sql = "UPDATE calendario SET id_disciplina ="+calendario.getDisciplina().getId()+",id_cronograma="+calendario.getCronograma().getId()
                + ",id_evento="+calendario.getEvento().getId()+", "
                + "dia="+calendario.getDia()+", "
                + "mes="+calendario.getMes()+", "
                + "ano="+calendario.getAno()+" "
                + "WHERE id_calendario="+calendario.getId();                      
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
    
    public void delete(Calendario calendario) {        
        sql = "DELETE FROM calendario WHERE id_calendario="+calendario.getId();                      
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
        sql = "SELECT * FROM calendario";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_calendario");                    
                    int idDisciplina = rs.getInt("id_disciplina");
                    int idCronograma = rs.getInt("id_cronograma");
                    int idEvento = rs.getInt("id_evento");
                    int dia      = rs.getInt("dia");
                    int mes     = rs.getInt("mes");
                    int ano     = rs.getInt("ano");                    
                    retorno[cont] = id+"  " + idDisciplina + "      " + idCronograma + "  " + idCronograma + " " + idEvento +"   "+ dia + " "+ mes + "  "+ ano + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem de calendario! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarCalendarioPorId(int id, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id_calendario";
        }else {
            campoOrdena = ordem;        
        }        
        if (id != 0) {
            sql = "SELECT * FROM calendario where id_calendario =" + id+" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM calendario order by " + campoOrdena ;
        }       
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                                 
                    int idCal = rs.getInt("id_calendario");                    
                    int idDisciplina = rs.getInt("id_disciplina");
                    int idCronograma = rs.getInt("id_cronograma");
                    int idEvento = rs.getInt("id_evento");
                    int dia      = rs.getInt("dia");
                    int mes     = rs.getInt("mes");
                    int ano     = rs.getInt("ano");
                    List<Eventos> dadosEventos = new ArrayList<>();
                    DAOEventos daoEventos = new DAOEventos();
                    dadosEventos = daoEventos.recuperaEventosPorId(idEvento);
                    retorno[cont] = "Cód.: "+idCal+" Evento: " + dadosEventos.get(0).getDescricao() +" Data: "+ dia + "/"+ mes + "/"+ ano + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarCalendarioPorEvento(int idEve, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "id";
        }else {
            campoOrdena = ordem;        
        }        
        if (idEve != 0) {
            sql = "SELECT * FROM calendario where id_evento =%" +idEve+"% order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM calendario order by " + campoOrdena ;
        }
        //sql = "SELECT * FROM produtos where nome like '%"+nome+ "%'";        
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                        
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("id_calendario");                    
                    int idDisciplina = rs.getInt("id_disciplina");
                    int idCronograma = rs.getInt("id_cronograma");
                    int idEvento = rs.getInt("id_evento");
                    int dia      = rs.getInt("dia");
                    int mes     = rs.getInt("mes");
                    int ano     = rs.getInt("ano");                    
                    retorno[cont] = id+"  " + idDisciplina + "      " + idCronograma  + " " + idEvento +"   "+ dia + " "+ mes + "  "+ ano + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeCalendarioPorId(Calendario calendario){
        sql = "SELECT id_calendario, id_disciplina, id_cronograma, id_evento, dia , mes, ano, status FROM calendario WHERE id_calendario ="+ calendario.getId();                
        int idCal = 0;
        int idDisciplina = 0; 
        int idCronograma  = 0;
        int idEvento  = 0;
        int dia = 0;
        int mes = 0;    
        int ano = 0;    
        int status = 0;
        
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    idCal = rs.getInt("id_calendario");                    
                    idDisciplina = rs.getInt("id_disciplina");
                    idCronograma = rs.getInt("id_cronograma");
                    idEvento = rs.getInt("id_evento");
                    dia      = rs.getInt("dia");
                    mes     = rs.getInt("mes");
                    ano     = rs.getInt("ano");
                    status = rs.getInt("status");
                }        
                calendario.setId(idCal);
                Disciplinas disciplinas = new Disciplinas();
                disciplinas.setId(idDisciplina);
                calendario.setDisciplina(disciplinas);
                Cronograma cronograma = new Cronograma();
                cronograma.setId(idCronograma);
                calendario.setCronograma(cronograma);
                Eventos eventos = new Eventos();
                eventos.setId(idEvento);
                calendario.setEvento(eventos);
                calendario.setDia(dia);
                calendario.setMes(mes);
                calendario.setAno(ano);
                calendario.setStatus(status);
                if (calendario.getId()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }           
    
    public List<Calendario> recuperaCalendarioPorId(int id){                
        sql = "SELECT id_calendario, id_disciplina, id_cronograma, id_evento, dia , mes, ano, status FROM calendario WHERE id_calendario="+id;
        List listCalendario = new ArrayList<Calendario>();
        Calendario calendario = new Calendario();        
        int idCal = 0;
        int idDisc = 0;
        int idCrono = 0;
        int idEven = 0;
        int dia = 0;
        int mes = 0;
        int ano = 0;
        int status = 0;        
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idCal             = rs.getInt("id_calendario");
                    idDisc            = rs.getInt("id_disciplina");
                    idCrono           = rs.getInt("id_cronograma");
                    idEven            = rs.getInt("id_evento");
                    dia               = rs.getInt("dia");
                    mes               = rs.getInt("mes");
                    ano               = rs.getInt("ano");
                    status            = rs.getInt("status");
                }      
                if (idCal != 0) {
                    calendario.setId(idCal);
                    Disciplinas disciplinas = new Disciplinas();
                    disciplinas.setId(idDisc);
                    calendario.setDisciplina(disciplinas);
                    Cronograma cronograma = new Cronograma();
                    cronograma.setId(idCrono);
                    calendario.setCronograma(cronograma);
                    Eventos eventos = new Eventos();
                    eventos.setId(idEven);
                    calendario.setEvento(eventos);
                    calendario.setDia(dia);
                    calendario.setMes(mes);
                    calendario.setAno(ano);
                    calendario.setStatus(status);
                    listCalendario.add(calendario);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return listCalendario;
    }
    public List<Calendario> recuperaTodosCalendario(){                
        sql = "SELECT id_calendario, id_disciplina, id_cronograma, id_evento, dia , mes, ano, status FROM calendario ";
        List listCalendario = new ArrayList<Calendario>();                
        int idCal = 0;
        int idDisc = 0;
        int idCrono = 0;
        int idEven = 0;
        int dia = 0;
        int mes = 0;
        int ano = 0;
        int status = 0;        
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idCal             = rs.getInt("id_calendario");
                    idDisc            = rs.getInt("id_disciplina");
                    idCrono           = rs.getInt("id_cronograma");
                    idEven            = rs.getInt("id_evento");
                    dia               = rs.getInt("dia");
                    mes               = rs.getInt("mes");
                    ano               = rs.getInt("ano");
                    status            = rs.getInt("status"); 
                    Calendario calendario = new Calendario();
                    calendario.setId(idCal);
                    Disciplinas disciplinas = new Disciplinas();
                    disciplinas.setId(idDisc);
                    calendario.setDisciplina(disciplinas);
                    Cronograma cronograma = new Cronograma();
                    cronograma.setId(idCrono);
                    calendario.setCronograma(cronograma);
                    Eventos eventos = new Eventos();
                    eventos.setId(idEven);
                    calendario.setEvento(eventos);
                    calendario.setDia(dia);
                    calendario.setMes(mes);
                    calendario.setAno(ano);
                    calendario.setStatus(status);
                    listCalendario.add(calendario);
                }                                                                                     
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return listCalendario;
    }
}
