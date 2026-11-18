import java.sql.SQLException;
import java.util.Scanner;

import database.Banco;
import models.Usuario;

public class Main {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        Banco db = new Banco();

        db.queryUpdate("CREATE TABLE IF NOT EXISTS usuario ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "email TEXT NOT NULL)");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n1 - Cadastrar usuario");
            System.out.println("2 - Listar usuarios");
            System.out.println("3 - Editar usuario");
            System.out.println("4 - Remover usuario");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                opcao = Integer.parseInt(entrada.nextLine());

                if (opcao == 1) {
                    String nome = lerNome();
                    String email = lerEmail();
                    if (new Usuario(nome, email).cadastrarUsuario(db)) {
                        System.out.println("Usuario cadastrado.");
                    }
                } else if (opcao == 2) {
                    Usuario.listarUsuarios(db);
                } else if (opcao == 3) {
                    System.out.print("ID do usuario: ");
                    int id = Integer.parseInt(entrada.nextLine());
                    // Busca o usuario antes de pedir os novos dados
                    Usuario usuario = Usuario.buscarPorId(db, id);
                    if (usuario == null) {
                        System.out.println("Usuario nao encontrado.");
                    } else {
                        System.out.println("Editando: " + usuario.getNome() + " | " + usuario.getEmail());
                        String nome = lerNome();
                        String email = lerEmail();
                        if (new Usuario(id, nome, email).editarUsuario(db)) {
                            System.out.println("Usuario atualizado.");
                        }
                    }
                } else if (opcao == 4) {
                    System.out.print("ID do usuario: ");
                    int id = Integer.parseInt(entrada.nextLine());
                    if (Usuario.removerUsuario(db, id)) {
                        System.out.println("Usuario removido.");
                    } else {
                        System.out.println("Usuario nao encontrado.");
                    }
                } else if (opcao != 0) {
                    System.out.println("Opcao invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
            } catch (SQLException e) {
                System.out.println("Erro no banco: " + e.getMessage());
            }
        }

        db.desconectar();
        entrada.close();
    }

    // Pede o nome ate ser valido
    static String lerNome() {
        System.out.print("Nome: ");
        String nome = entrada.nextLine().trim();
        while (!Usuario.nomeValido(nome)) {
            System.out.println("Nome invalido. Use apenas letras (minimo 3).");
            System.out.print("Nome: ");
            nome = entrada.nextLine().trim();
        }
        return nome;
    }

    // Pede o email ate ser valido
    static String lerEmail() {
        System.out.print("Email: ");
        String email = entrada.nextLine().trim();
        while (!Usuario.emailValido(email)) {
            System.out.println("Email invalido. Exemplo: nome@email.com");
            System.out.print("Email: ");
            email = entrada.nextLine().trim();
        }
        return email;
    }
}
