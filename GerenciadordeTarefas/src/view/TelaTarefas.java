package view;

import dao.TarefaDAO;
import model.Tarefa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Interface gráfica para gerenciamento de tarefas
 */
public class TelaTarefas extends JFrame {

    private TarefaDAO tarefaDAO;
    private JTable tabelaTarefas;
    private DefaultTableModel modeloTabela;
    private JTextField txtTitulo, txtDescricao, txtDataVencimento;
    private JComboBox<String> cbPrioridade;
    private JCheckBox chkConcluida;
    private JButton btnSalvar, btnExcluir, btnLimpar, btnAtualizar;
    private Tarefa tarefaSelecionada;

    public TelaTarefas() {
        tarefaDAO = new TarefaDAO();
        configurarJanela();
        inicializarComponentes();
        atualizarTabela();
    }

    private void configurarJanela() {
        setTitle("Gerenciador de Tarefas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // Painel de formulário
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Detalhes da Tarefa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        txtTitulo = new JTextField(30);
        painelFormulario.add(txtTitulo, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        painelFormulario.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        txtDescricao = new JTextField(30);
        painelFormulario.add(txtDescricao, gbc);

        // Data de Vencimento
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        painelFormulario.add(new JLabel("Data de Vencimento (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        txtDataVencimento = new JTextField(10);
        painelFormulario.add(txtDataVencimento, gbc);

        // Prioridade
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        painelFormulario.add(new JLabel("Prioridade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        cbPrioridade = new JComboBox<>(new String[]{"Baixa", "Média", "Alta"});
        painelFormulario.add(cbPrioridade, gbc);

        // Concluída
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        painelFormulario.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        chkConcluida = new JCheckBox("Concluída");
        painelFormulario.add(chkConcluida, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnAtualizar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        painelFormulario.add(painelBotoes, gbc);

        // Tabela de tarefas
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Título");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Data Criação");
        modeloTabela.addColumn("Data Vencimento");
        modeloTabela.addColumn("Prioridade");
        modeloTabela.addColumn("Concluída");

        tabelaTarefas = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaTarefas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Tarefas"));

        // Adicionar componentes ao frame
        add(painelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Configurar eventos
        configurarEventos();
    }

    private void configurarEventos() {
        // Evento de seleção na tabela
        tabelaTarefas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaTarefas.getSelectedRow() != -1) {
                int linhaSelecionada = tabelaTarefas.getSelectedRow();
                int id = (int) tabelaTarefas.getValueAt(linhaSelecionada, 0);
                carregarTarefa(id);
            }
        });

        // Evento do botão Salvar
        btnSalvar.addActionListener(e -> salvarTarefa());

        // Evento do botão Excluir
        btnExcluir.addActionListener(e -> excluirTarefa());

        // Evento do botão Limpar
        btnLimpar.addActionListener(e -> limparFormulario());

        // Evento do botão Atualizar
        btnAtualizar.addActionListener(e -> atualizarTabela());
    }

    private void carregarTarefa(int id) {
        tarefaSelecionada = tarefaDAO.buscarPorId(id);
        if (tarefaSelecionada != null) {
            txtTitulo.setText(tarefaSelecionada.getTitulo());
            txtDescricao.setText(tarefaSelecionada.getDescricao());
            
            if (tarefaSelecionada.getDataVencimento() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtDataVencimento.setText(tarefaSelecionada.getDataVencimento().format(formatter));
            } else {
                txtDataVencimento.setText("");
            }
            
            cbPrioridade.setSelectedIndex(tarefaSelecionada.getPrioridade() - 1);
            chkConcluida.setSelected(tarefaSelecionada.isConcluida());
        }
    }

    private void salvarTarefa() {
        try {
            String titulo = txtTitulo.getText().trim();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O título da tarefa é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descricao = txtDescricao.getText().trim();
            LocalDate dataVencimento = null;
            
            if (!txtDataVencimento.getText().trim().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataVencimento = LocalDate.parse(txtDataVencimento.getText().trim(), formatter);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            int prioridade = cbPrioridade.getSelectedIndex() + 1;
            boolean concluida = chkConcluida.isSelected();

            if (tarefaSelecionada == null) {
                // Nova tarefa
                Tarefa novaTarefa = new Tarefa(titulo, descricao, dataVencimento, prioridade);
                novaTarefa.setConcluida(concluida);
                int id = tarefaDAO.inserir(novaTarefa);
                
                if (id > 0) {
                    JOptionPane.showMessageDialog(this, "Tarefa cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparFormulario();
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Atualizar tarefa existente
                tarefaSelecionada.setTitulo(titulo);
                tarefaSelecionada.setDescricao(descricao);
                tarefaSelecionada.setDataVencimento(dataVencimento);
                tarefaSelecionada.setPrioridade(prioridade);
                tarefaSelecionada.setConcluida(concluida);
                
                boolean sucesso = tarefaDAO.atualizar(tarefaSelecionada);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparFormulario();
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirTarefa() {
        if (tarefaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir a tarefa '" + tarefaSelecionada.getTitulo() + "'?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean sucesso = tarefaDAO.excluir(tarefaSelecionada.getId());
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparFormulario() {
        txtTitulo.setText("");
        txtDescricao.setText("");
        txtDataVencimento.setText("");
        cbPrioridade.setSelectedIndex(0);
        chkConcluida.setSelected(false);
        tarefaSelecionada = null;
        tabelaTarefas.clearSelection();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Tarefa> tarefas = tarefaDAO.listarTodas();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Tarefa tarefa : tarefas) {
            Object[] linha = new Object[7];
            linha[0] = tarefa.getId();
            linha[1] = tarefa.getTitulo();
            linha[2] = tarefa.getDescricao();
            linha[3] = tarefa.getDataCriacao() != null ? tarefa.getDataCriacao().format(formatter) : "";
            linha[4] = tarefa.getDataVencimento() != null ? tarefa.getDataVencimento().format(formatter) : "";
            linha[5] = tarefa.getPrioridadeTexto();
            linha[6] = tarefa.isConcluida() ? "Sim" : "Não";
            
            modeloTabela.addRow(linha);
        }
    }
}