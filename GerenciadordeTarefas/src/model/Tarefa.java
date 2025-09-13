package model;

import java.time.LocalDate;

/**
 * Classe que representa uma tarefa no sistema de gerenciamento de tarefas
 */
public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;
    private boolean concluida;
    private int prioridade; // 1-Baixa, 2-Média, 3-Alta

    // Construtor padrão
    public Tarefa() {
        this.dataCriacao = LocalDate.now();
        this.concluida = false;
        this.prioridade = 1;
    }

    // Construtor com parâmetros
    public Tarefa(String titulo, String descricao, LocalDate dataVencimento, int prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.dataVencimento = dataVencimento;
        this.concluida = false;
        this.prioridade = prioridade;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        if (prioridade >= 1 && prioridade <= 3) {
            this.prioridade = prioridade;
        } else {
            throw new IllegalArgumentException("Prioridade deve ser entre 1 e 3");
        }
    }
    
    public String getPrioridadeTexto() {
        switch (prioridade) {
            case 1: return "Baixa";
            case 2: return "Média";
            case 3: return "Alta";
            default: return "Desconhecida";
        }
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataVencimento=" + dataVencimento +
                ", concluida=" + concluida +
                ", prioridade=" + getPrioridadeTexto() +
                '}';
    }
}