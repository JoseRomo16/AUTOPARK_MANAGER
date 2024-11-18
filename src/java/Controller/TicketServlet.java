package controller;

import config.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String plateNumber = request.getParameter("plateNumber");

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ticket WHERE automovil_idautomovil IN (SELECT idautomovil FROM automovil WHERE placa = ?)");
            stmt.setString(1, plateNumber);
            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/plain");
            while (rs.next()) {
                response.getWriter().println("Ticket ID: " + rs.getInt("idticket") + ", Hora Ingreso: " + rs.getTimestamp("hora_ingreso"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}

