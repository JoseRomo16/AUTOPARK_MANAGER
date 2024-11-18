
import java.sql.Connection;
import config.DatabaseConnection;

public class TestDatabase {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Conexion exitosa a la base de datos");
            } else {
                System.out.println("Conexión fallida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

