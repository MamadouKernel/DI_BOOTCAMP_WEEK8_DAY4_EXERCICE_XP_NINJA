/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author m-konate
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    
    Connection connection = null;

    public Connection getConnecion() {
        try {
            String url = "jdbc:postgresql://localhost:5432/mydatabase";
            String username = "postgres";
            String password = "postgres";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.out.println("Erreur de connexion à la base de données");
        }

        return connection;
    }
}

