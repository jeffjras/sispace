/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.dao;

import br.ufpa.easoftware.tap.model.Alunos;
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
public class DAOCalendario {
    
    String sql;
    Conexao conn;  
    Statement stmt;
    ResultSet rs;
    int linhasAfetadas=0;
    List<Alunos> dados;
    
    public DAOCalendario() {
        conn = new Conexao();
    }
    
    public void inserir(Alunos alunos) {  
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String ingresso = DATE_FORMAT.format(alunos.getDataIngresso());        
        
        sql = "INSERT INTO alunos(matricula, nome, endereco, telefone, data_ingresso) VALUES("+ alunos.getMatricula() +",'"
              + alunos.getNome()+"','"+alunos.getEndereco()+"','"
              + alunos.getTelefone()+"','"+ingresso+"')";        
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
    
    public void atualizar(Alunos alunos) {        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String ingresso = DATE_FORMAT.format(alunos.getDataIngresso()); 
        
        sql = "UPDATE alunos SET nome ='"+alunos.getNome()+"',endereco='"+alunos.getEndereco()+
                      "',telefone='"+alunos.getTelefone()+"', data_ingresso='"+ingresso+"' WHERE matricula="+alunos.getMatricula();                      
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
    
    public void delete(Alunos alunos) {        
        sql = "DELETE FROM alunos WHERE matricula="+alunos.getMatricula();                      
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
        sql = "SELECT * FROM alunos";
        String[] retorno = new String[30];                
        int cont=-1;
        try {            
            if (conn.conectar()) {                                
                stmt = conn.getConn().createStatement();                                
                rs = stmt.executeQuery(sql);                                
                while (rs.next()) {
                    cont = cont + 1;                                                                        
                    int id = rs.getInt("matricula");
                    String nome = rs.getString("nome");
                    double telefone = rs.getDouble("telefone");                    
                    retorno[cont] = id+"       " + nome.trim() + "                      " + telefone + "\n";                    
                }
            }                        
        } catch (Exception e) {                
            System.out.println("Erro ao realizar a listagem de alunos! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }  
    
    public String[] listarAlunoPorMatricula(int matricula, String ordem){                   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "matricula";
        }else {
            campoOrdena = ordem;        
        }        
        if (matricula != 0) {
            sql = "SELECT * FROM alunos where matricula =" + matricula+" order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM alunos order by " + campoOrdena ;
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
                    int id = rs.getInt("matricula");
                    String nome = rs.getString("nome");
                    String telefone = rs.getString("telefone");                    
                    retorno[cont] = id+"       " + nome.trim() + "                      " + telefone + "\n";                    
                }
            }                        
        } catch (Exception e) {            
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
            
    public String[] listarAlunoPorNome(String nome, String ordem){   
        String campoOrdena = null;
        if (ordem.equals("")){            
            campoOrdena = "matricula";
        }else {
            campoOrdena = ordem;        
        }        
        if (nome.equals("")) {
            sql = "SELECT * FROM alunos where nome like'%" +nome+"%' order by " + campoOrdena ;
        } else {
            sql = "SELECT * FROM alunos order by " + campoOrdena ;
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
                    int matricula = rs.getInt("matricula");
                    String nomeAluno = rs.getString("nome");
                    String telefone = rs.getString("telefone");                    
                    retorno[cont] = matricula+"       " + nomeAluno.trim() + "                      " + telefone + "\n";                    
                }
            }                        
        } catch (Exception e) {                 
            System.out.println("Erro ao realizar consulta no banco de dados! descrição do erro:"+ e.getMessage());                                     
        }         
        return retorno;
    }
    
    public boolean existeAlunoPorMatricula(Alunos alunos){
        sql = "SELECT matricula, nome FROM alunos WHERE matricula ="+ alunos.getMatricula();
        int matricula=0;
        String nome = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    matricula = rs.getInt("matricula");
                    nome = rs.getString("nome");
                }        
                alunos.setMatricula(matricula);
                alunos.setNome(nome);
                if (alunos.getMatricula()!= 0 ) {
                    return true;
                }                
            }                                    
        } catch (Exception e) {            
            System.out.println("Nenhum registro localizado com os dados informados. "+e.getMessage());                                                        
        }         
        return false;
    }
    
    public boolean existeAlunoPorNome(Alunos alunos){
        String nome = null;
        int matricula = 0;
        sql = "SELECT matricula, nome FROM alunos WHERE nome like '%"+alunos.getNome()+"%'";
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    matricula = rs.getInt("matricula");
                    nome = rs.getString("nome");
                }                
                alunos.setMatricula(matricula);
                alunos.setNome(nome);
                if (!alunos.getNome().equals("")) {
                    return true;
                }                               
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return false;
    }                            
    
    public List<Alunos> recuperaAlunosPorMatricula(int matricula){                
        sql = "SELECT matricula, nome, endereco, telefone, data_ingresso FROM alunos WHERE matricula="+matricula;
        List aluno = new ArrayList<Alunos>();
        Alunos alun = new Alunos();
        String nome = null;
        int mat = 0;
        String endereco = null;
        String telefone = null;
        Date data_ingresso = null;
        try {            
            if (conn.conectar()) {                
                stmt = conn.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    mat             = rs.getInt("matricula");
                    nome            = rs.getString("nome");
                    endereco        = rs.getString("endereco");
                    telefone        = rs.getString("telefone");
                    data_ingresso   = rs.getDate("data_ingresso");
                }      
                if (mat != 0) {
                    alun.setMatricula(mat);
                    alun.setNome(nome);
                    alun.setEndereco(endereco);
                    alun.setTelefone(telefone);
                    alun.setDataIngresso(data_ingresso);
                    aluno.add(alun);                    
                }                                                                
            }                        
        } catch (Exception e) {
            System.out.println("Consulta não retornou resultado com os dados informados." + e.getMessage());                                
        }        
        return aluno;
    }
}
