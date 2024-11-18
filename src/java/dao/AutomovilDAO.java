package dao;

import config.DatabaseConnection;
import model.Automovil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AutomovilDAO {
    public void registrarAutomovil(Automovil automovil) throws SQLException {
        String sql = "INSERT INTO automovil (placa, modelo, color, cliente_idcliente, tipo_vehiculo_idtipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, automovil.getPlaca());
            preparedStatement.setString(2, automovil.getModelo());
            preparedStatement.setString(3, automovil.getColor());
            preparedStatement.setInt(4, automovil.getIdCliente());
            preparedStatement.setInt(5, automovil.getIdTipoVehiculo());
            preparedStatement.executeUpdate();
        }
    }
}

