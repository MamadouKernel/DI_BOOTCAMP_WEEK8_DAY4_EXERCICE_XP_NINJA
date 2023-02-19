package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private Connection connection = null;

    public Connection getConnecion() {
        try {
            String url = "jdbc:postgresql://localhost:5432/dvdrental";
            String username = "postgres";
            String pwd = "postgres";
            connection = DriverManager.getConnection(url, username,pwd);
            System.out.println("Connexion établie avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur de connexion à la base de données");
            ex.printStackTrace();
        }

        return connection;
    }
}
