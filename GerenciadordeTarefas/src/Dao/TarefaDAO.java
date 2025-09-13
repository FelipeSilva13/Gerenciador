package dao;

import Dao.Conexao;
import model.Tarefa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de CRUD para a entidade Tarefa
 */
public class TarefaDAO {

    /**
     * Insere uma nova tarefa no banco de dados
     * @param tarefa A tarefa a ser inserida
     * @return O ID da tarefa inserida ou -1 em caso de erro
     */
    public int inserir(Tarefa tarefa) {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            return -1;
        }

        String sql = "INSERT INTO tarefas (titulo, descricao, data_criacao, data_vencimento, concluida, prioridade) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, Date.valueOf(tarefa.getDataCriacao()));
            stmt.setDate(4, tarefa.getDataVencimento() != null ? Date.valueOf(tarefa.getDataVencimento()) : null);
            stmt.setBoolean(5, tarefa.isConcluida());
            stmt.setInt(6, tarefa.getPrioridade());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir tarefa: " + e.getMessage());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * Atualiza uma tarefa existente no banco de dados
     * @param tarefa A tarefa com os dados atualizados
     * @return true se a atualização foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Tarefa tarefa) {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            return false;
        }

        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, data_vencimento = ?, "
                + "concluida = ?, prioridade = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, tarefa.getDataVencimento() != null ? Date.valueOf(tarefa.getDataVencimento()) : null);
            stmt.setBoolean(4, tarefa.isConcluida());
            stmt.setInt(5, tarefa.getPrioridade());
            stmt.setInt(6, tarefa.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * Exclui uma tarefa do banco de dados
     * @param id O ID da tarefa a ser excluída
     * @return true se a exclusão foi bem-sucedida, false caso contrário
     */
    public boolean excluir(int id) {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            return false;
        }

        String sql = "DELETE FROM tarefas WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tarefa: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * Busca uma tarefa pelo ID
     * @param id O ID da tarefa a ser buscada
     * @return A tarefa encontrada ou null se não existir
     */
    public Tarefa buscarPorId(int id) {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            return null;
        }

        String sql = "SELECT * FROM tarefas WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return criarTarefaDoResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar tarefa: " + e.getMessage());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * Lista todas as tarefas do banco de dados
     * @return Uma lista com todas as tarefas
     */
    public List<Tarefa> listarTodas() {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            return new ArrayList<>();
        }

        String sql = "SELECT * FROM tarefas ORDER BY data_vencimento, prioridade DESC";
        List<Tarefa> tarefas = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tarefas.add(criarTarefaDoResultSet(rs));
            }

            return tarefas;
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * Lista tarefas não concluídas
     * @return Uma lista com as tarefas não concluídas
     */
    public List<Tarefa> listarNaoConcluidas() {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            return new ArrayList<>();
        }

        String sql = "SELECT * FROM tarefas WHERE concluida = false ORDER BY data_vencimento, prioridade DESC";
        List<Tarefa> tarefas = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tarefas.add(criarTarefaDoResultSet(rs));
            }

            return tarefas;
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas não concluídas: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * Método auxiliar para criar um objeto Tarefa a partir de um ResultSet
     * @param rs O ResultSet contendo os dados da tarefa
     * @return Um objeto Tarefa preenchido com os dados do ResultSet
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet
     */
    private Tarefa criarTarefaDoResultSet(ResultSet rs) throws SQLException {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(rs.getInt("id"));
        tarefa.setTitulo(rs.getString("titulo"));
        tarefa.setDescricao(rs.getString("descricao"));
        
        Date dataCriacao = rs.getDate("data_criacao");
        if (dataCriacao != null) {
            tarefa.setDataCriacao(dataCriacao.toLocalDate());
        }
        
        Date dataVencimento = rs.getDate("data_vencimento");
        if (dataVencimento != null) {
            tarefa.setDataVencimento(dataVencimento.toLocalDate());
        }
        
        tarefa.setConcluida(rs.getBoolean("concluida"));
        tarefa.setPrioridade(rs.getInt("prioridade"));
        
        return tarefa;
    }
}