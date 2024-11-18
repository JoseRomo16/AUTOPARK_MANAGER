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

@WebServlet("/espacio")
public class EspacioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM espacio_parqueo");
            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/plain");
            while (rs.next()) {
                response.getWriter().println("ID: " + rs.getInt("idespacio_parqueo") + ", Estado: " + rs.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String spaceNumber = request.getParameter("spaceNumber");
        String location = request.getParameter("location");
        String state = request.getParameter("state");

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO espacio_parqueo (numero_espacio, ubicacion, estado) VALUES (?, ?, ?)");
            stmt.setString(1, spaceNumber);
            stmt.setString(2, location);
            stmt.setString(3, state);
            stmt.executeUpdate();

            response.getWriter().println("Espacio registrado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al registrar el espacio: " + e.getMessage());
        }
    }
}

