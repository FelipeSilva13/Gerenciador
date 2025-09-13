-- Script para criar a tabela de tarefas no banco de dados

USE felipe;

CREATE TABLE IF NOT EXISTS tarefas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_criacao DATE NOT NULL,
    data_vencimento DATE,
    concluida BOOLEAN NOT NULL DEFAULT FALSE,
    prioridade INT NOT NULL DEFAULT 1,
    
    CONSTRAINT chk_prioridade CHECK (prioridade BETWEEN 1 AND 3)
);

-- Nota: Os comentários abaixo são apenas para documentação e foram removidos do SQL
-- pois o comando COMMENT ON não é suportado em todas as versões do MySQL
--
-- Tabela tarefas: Tabela para armazenar as tarefas do sistema
-- Coluna id: Identificador único da tarefa
-- Coluna titulo: Título da tarefa
-- Coluna descricao: Descrição detalhada da tarefa
-- Coluna data_criacao: Data em que a tarefa foi criada
-- Coluna data_vencimento: Data limite para conclusão da tarefa
-- Coluna concluida: Indica se a tarefa foi concluída (true) ou não (false)
-- Coluna prioridade: Nível de prioridade da tarefa: 1-Baixa, 2-Média, 3-Alta