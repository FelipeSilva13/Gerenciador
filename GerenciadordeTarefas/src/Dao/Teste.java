package Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Teste {



        public static void main(String[] args) {
            Connection conn = Conexao.conectar();

            if (conn != null) {
                String sql = "SELECT * FROM usuariosfelipe";

                try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    System.out.println("Usuários cadastrados:");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nome = rs.getString("nome");
                        String email = rs.getString("email");
                        String dataCadastro = rs.getString("data_cadastro");

                        System.out.println(id + " | " + nome + " | " + email + " | " + dataCadastro);
                    }

                } catch (SQLException e) {
                    System.out.println("Erro ao consultar usuários.");
                    e.printStackTrace();
                } finally {
                    Conexao.desconectar(conn);
                }
            }
        }
}
