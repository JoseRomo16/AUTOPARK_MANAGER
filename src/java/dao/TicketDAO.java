package dao;

import config.DatabaseConnection;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDAO {
    public int emitirTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO ticket (hora_ingreso, automovil_idautomovil, espacio_parqueo_idespacio_parqueo) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(ticket.getHoraIngreso()));
            preparedStatement.setInt(2, ticket.getIdAutomovil());
            preparedStatement.setInt(3, ticket.getIdEspacioParqueo());
            preparedStatement.executeUpdate();

            return preparedStatement.getGeneratedKeys().getInt(1);
        }
    }
}

