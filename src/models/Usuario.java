package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Banco;

public class Usuario {

    private int id;
    private String nome;
    private String email;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Usuario(int id, String nome, String email) {
        this(nome, email);
        this.id = id;
    }

    // Nome: apenas letras (com acento) e espacos, minimo de 3 caracteres
    public static boolean nomeValido(String nome) {
        return nome.matches("[\\p{L} ]{3,}");
    }

    // Email: texto@dominio.extensao
    public static boolean emailValido(String email) {
        return email.matches("[\\w.-]+@[\\w-]+(\\.[\\w-]+)+");
    }

    public boolean cadastrarUsuario(Banco db) {
        String query = String.format("INSERT INTO usuario (nome, email) VALUES ('%s', '%s')", nome, email);
        return db.queryUpdate(query) > 0;
    }

    public boolean editarUsuario(Banco db) {
        String query = String.format("UPDATE usuario SET nome = '%s', email = '%s' WHERE id = %d", nome, email, id);
        return db.queryUpdate(query) > 0;
    }

    public static boolean removerUsuario(Banco db, int id) {
        String query = String.format("DELETE FROM usuario WHERE id = %d", id);
        return db.queryUpdate(query) > 0;
    }

    // Retorna o usuario com esse id, ou null se nao existir
    public static Usuario buscarPorId(Banco db, int id) throws SQLException {
        ResultSet rs = db.querySearch(String.format("SELECT * FROM usuario WHERE id = %d", id));
        if (rs != null && rs.next()) {
            return new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
        }
        return null;
    }

    public static void listarUsuarios(Banco db) throws SQLException {
        ResultSet rs = db.querySearch("SELECT * FROM usuario");
        if (rs == null) {
            return;
        }
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("nome") + " | " + rs.getString("email"));
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
