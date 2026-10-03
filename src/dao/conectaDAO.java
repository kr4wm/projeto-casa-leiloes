package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class ConectaDAO {
    
    public Connection connectDB(){
        Connection conn = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/uc11_leiloes";
            String user = "root";
            String password = "Vinicius1@20"; 
        
            conn = DriverManager.getConnection(url, user, password);
        
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de Dados" + erro.getMessage());
        }
        return conn;
    }
    
}
