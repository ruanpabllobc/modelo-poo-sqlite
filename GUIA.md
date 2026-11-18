# Modelo de integração Java + SQLite

Exemplo simples de como cadastrar, listar, editar e remover dados em um banco SQLite com Java. Use-o como referência para as classes do seu sistema.

## Como executar no VS Code

1. Tenha o **JDK** instalado e a extensão **Extension Pack for Java** no VS Code.
2. Abra a pasta `modelo-poo-sqlite` no VS Code (Arquivo > Abrir Pasta).
3. Abra `src/Main.java` e clique em **Run**.

O driver do SQLite já está na pasta `lib`. Na primeira execução, o arquivo `sistema.db` (o banco de dados) é criado na pasta do projeto.

## Estrutura

```
src/
  database/Banco.java    conecta ao banco e executa os comandos SQL
  models/Usuario.java    classe do modelo, com os métodos que usam o banco
  Main.java              cria a tabela e mostra o menu
```

A classe `Banco` tem dois métodos:

- `queryUpdate(sql)`: para comandos que alteram dados (`CREATE`, `INSERT`, `UPDATE`, `DELETE`).
- `querySearch(sql)`: para buscas (`SELECT`). Retorna um `ResultSet`, percorrido com `while (rs.next())`.

## Como adaptar para o seu sistema

Para cada classe do diagrama que precisa ser salva:

1. **Crie a tabela no `Main`**, com `db.queryUpdate("CREATE TABLE IF NOT EXISTS ...")`. Cada atributo vira uma coluna: `int` → `INTEGER`, `double` → `REAL`, `String` → `TEXT`.
2. **Crie os métodos de banco na própria classe**, recebendo o `Banco` como parâmetro, como em `cadastrarUsuario(Banco db)`.

Nos comandos SQL, textos ficam entre aspas simples (`'%s'`) e números ficam sem aspas (`%d`). Veja os quatro métodos em `Usuario.java` como modelo: cadastrar (`INSERT`), listar (`SELECT`), editar (`UPDATE`) e remover (`DELETE`).

## Visualizando o banco no Beekeeper Studio

1. No Beekeeper Studio, crie uma **New Connection** e escolha o tipo **SQLite**.
2. Em **Database File**, selecione o arquivo `sistema.db` da pasta do projeto e clique em **Connect**.
3. As tabelas aparecem na barra lateral. Clique em uma tabela para ver os dados e use o botão de atualizar depois de rodar o programa.

Para começar do zero, feche o programa e o Beekeeper Studio e apague o arquivo `sistema.db`.
