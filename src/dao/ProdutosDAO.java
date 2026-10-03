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
        String sql = "SELECT * FROM produtos";
        listagem.clear();
        
        try {
            conn = new ConectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return listagem;
    }
    
        public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        listagem.clear();
        
        try {
            conn = new ConectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + erro.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return listagem;
    }
        
    public void venderProduto(int id) {
        Connection connLocal = null;
        PreparedStatement stmt = null;

        try {
            connLocal = new ConectaDAO().connectDB();

            String sql = "UPDATE produtos SET status = ? WHERE id = ?";
            stmt = connLocal.prepareStatement(sql);

            stmt.setString(1, "Vendido");
            stmt.setInt(2, id);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao vender produto: " + erro.getMessage());

        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connLocal != null) connLocal.close();
            } catch (SQLException erro) {
                erro.printStackTrace();
            }
        }
    }
    
}
