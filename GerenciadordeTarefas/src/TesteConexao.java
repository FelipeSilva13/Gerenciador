import Dao.Conexao;
import java.sql.Connection;

/**
 * Classe para testar a conexão com o banco de dados
 */
public class TesteConexao {
    
    public static void main(String[] args) {
        System.out.println("Testando conexão com o banco de dados...");
        
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver JDBC carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver JDBC: " + e.getMessage());
            System.out.println("Verifique se o MySQL Connector/J está no classpath do projeto.");
            return;
        }
        
        // Tenta estabelecer a conexão
        Connection conn = Conexao.conectar();
        
        if (conn != null) {
            System.out.println("Conexão estabelecida com sucesso!");
            System.out.println("Configurações de conexão:");
            System.out.println("URL: " + Conexao.getURL());
            System.out.println("Usuário: " + Conexao.getUSUARIO());
            Conexao.desconectar(conn);
        } else {
            System.out.println("Falha ao conectar ao banco de dados.");
            System.out.println("Verifique:");
            System.out.println("1. Se o servidor MySQL está em execução");
            System.out.println("2. Se o banco de dados 'felipe' existe");
            System.out.println("3. Se o usuário e senha estão corretos");
            System.out.println("4. Se o MySQL Connector/J está no classpath");
        }
    }
}