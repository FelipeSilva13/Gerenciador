
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/felipe";
    private static final String USUARIO = "root";
    private static final String SENHA = "felipe87";

    public static Connection conectar() {
        Connection conn = null;
        try {
            // Adiciona parâmetros de conexão para resolver problemas comuns
            String urlCompleta = URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            conn = DriverManager.getConnection(urlCompleta, USUARIO, SENHA);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }
    
    public static void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    // Getters para acesso às constantes de conexão
    public static String getURL() {
        return URL;
    }
    
    public static String getUSUARIO() {
        return USUARIO;
    }
}
