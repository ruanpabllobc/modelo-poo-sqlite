package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {

    private Connection db = null;
    private Statement statement = null;

    public Banco() {
        try {
            this.db = DriverManager.getConnection("jdbc:sqlite:sistema.db");
            this.statement = this.db.createStatement();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            if (db != null) {
                statement.close();
                db.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao desconectar: " + e.getMessage());
        }
    }

    public int queryUpdate(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Erro no banco: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet querySearch(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Erro no banco: " + e.getMessage());
            return null;
        }
    }
}
