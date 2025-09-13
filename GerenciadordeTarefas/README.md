# Sistema de Gerenciamento de Tarefas

Este é um aplicativo Java para gerenciamento de tarefas com interface gráfica e persistência em banco de dados MySQL.

## Funcionalidades

- Cadastro de tarefas com título, descrição, data de vencimento e prioridade
- Visualização de todas as tarefas em uma tabela
- Edição de tarefas existentes
- Exclusão de tarefas
- Marcação de tarefas como concluídas
- Filtragem de tarefas por status

## Estrutura do Projeto

- `model/`: Classes de modelo de dados
  - `Tarefa.java`: Representa uma tarefa no sistema
- `dao/`: Classes de acesso a dados
  - `TarefaDAO.java`: Operações CRUD para tarefas no banco de dados
- `view/`: Classes de interface gráfica
  - `TelaTarefas.java`: Interface principal do sistema
- `sql/`: Scripts SQL
  - `criar_tabela_tarefas.sql`: Script para criar a tabela no banco de dados

## Requisitos

- Java 8 ou superior
- MySQL 5.7 ou superior
- Conector JDBC para MySQL

## Configuração do Banco de Dados

1. Crie um banco de dados chamado `felipe` no MySQL
2. Execute o script SQL localizado em `src/sql/criar_tabela_tarefas.sql`
3. Verifique as configurações de conexão em `src/Dao/Conexao.java`

## Como Executar

1. Compile o projeto
2. Execute a classe `Main.java`

## Capturas de Tela

(Adicione capturas de tela do aplicativo aqui)

## Melhorias Futuras

- Implementar sistema de login
- Adicionar categorias para tarefas
- Implementar notificações para tarefas próximas do vencimento
- Adicionar relatórios e estatísticas
- Melhorar o design da interface gráfica