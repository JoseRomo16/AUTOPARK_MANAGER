package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/parking_manager";
    private static final String USER = "root"; // Cambiar si tienes otro usuario
    private static final String PASSWORD = ""; // Cambiar si tienes contraseña

    public static Connection getConnection() throws SQLException {
        try {
            // Registrar explícitamente el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("El controlador JDBC no fue encontrado.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}




