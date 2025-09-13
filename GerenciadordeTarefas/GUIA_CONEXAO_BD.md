# Guia de Solução de Problemas de Conexão com o Banco de Dados

Este guia ajudará você a resolver problemas de conexão com o banco de dados MySQL no projeto de Gerenciamento de Tarefas.

## Pré-requisitos

1. MySQL Server instalado e em execução
2. MySQL Connector/J (driver JDBC) adicionado ao projeto

## Verificações Básicas

### 1. Verificar se o MySQL está em execução

- **Windows**: Verifique no Gerenciador de Tarefas se o processo `mysqld.exe` está em execução
- Ou abra o prompt de comando e execute: `sc query mysql`

### 2. Verificar se o banco de dados existe

1. Abra o MySQL Workbench ou o prompt do MySQL
2. Execute o comando: `SHOW DATABASES;`
3. Verifique se o banco de dados `felipe` está na lista
4. Se não estiver, execute o script `src/sql/criar_banco_dados.sql`

### 3. Verificar credenciais

As credenciais configuradas no projeto são:
- **URL**: jdbc:mysql://localhost:3306/felipe
- **Usuário**: root
- **Senha**: felipe87

Verifique se estas credenciais estão corretas para o seu ambiente MySQL.

## Problemas Comuns e Soluções

### Erro "Communications link failure"

- **Causa**: O servidor MySQL não está em execução ou a porta está bloqueada
- **Solução**: Inicie o serviço MySQL ou verifique configurações de firewall

### Erro "Access denied for user"

- **Causa**: Usuário ou senha incorretos
- **Solução**: Verifique as credenciais em `Dao.Conexao.java`

### Erro "Unknown database"

- **Causa**: O banco de dados 'felipe' não existe
- **Solução**: Execute o script `src/sql/criar_banco_dados.sql`

### Erro "No suitable driver found"

- **Causa**: O driver JDBC do MySQL não está no classpath
- **Solução**: Adicione o MySQL Connector/J às bibliotecas do projeto

## Como Adicionar o MySQL Connector/J no IntelliJ IDEA

1. Vá para **File > Project Structure**
2. Selecione **Libraries** no painel esquerdo
3. Clique no botão **+** (New Project Library)
4. Selecione **From Maven**
5. Digite `mysql:mysql-connector-java:8.0.28` (ou versão mais recente)
6. Clique em **OK** e aplique as alterações

## Testando a Conexão

Execute a classe `TesteConexao.java` para verificar se a conexão está funcionando corretamente. Esta classe irá:

1. Verificar se o driver JDBC está disponível
2. Tentar estabelecer uma conexão com o banco de dados
3. Exibir mensagens detalhadas em caso de erro

## Modificando Parâmetros de Conexão

Se necessário, você pode modificar os parâmetros de conexão em `Dao.Conexao.java`. Os parâmetros adicionais já incluídos são:

- `useSSL=false`: Desativa a conexão SSL
- `allowPublicKeyRetrieval=true`: Permite recuperação de chave pública
- `serverTimezone=UTC`: Define o fuso horário para UTC

## Criando as Tabelas

Após estabelecer a conexão com o banco de dados, execute o script `src/sql/criar_tabela_tarefas.sql` para criar a tabela necessária para o funcionamento do aplicativo.