package dao;

import dto.ProdutosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Import necessário adicionado
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        Connection connLocal = null;
        PreparedStatement stmt = null;

        try {
            connLocal = new ConectaDAO().connectDB();
            connLocal.setAutoCommit(false);

            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            stmt = connLocal.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());

            stmt.executeUpdate();
            connLocal.commit();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (Exception e) {
            try {
                if (connLocal != null) {
                    connLocal.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connLocal != null) connLocal.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    } 
    
    public ArrayList<ProdutosDTO> listarProdutos(){       
        return listagem;
    }
}
