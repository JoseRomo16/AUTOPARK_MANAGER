package dao;

import config.DatabaseConnection;
import model.Reporte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReporteDAO {

    // Método para agregar un nuevo reporte
    public void agregarReporte(Reporte reporte) throws SQLException {
        String sql = "INSERT INTO contabilidad (fecha, total_ingresos, total_egresos, balance, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(reporte.getFecha()));
            stmt.setDouble(2, reporte.getIngresosTotales());
            stmt.setDouble(3, reporte.getGastosTotales());
            stmt.setDouble(4, reporte.getBalance());
            stmt.setString(5, reporte.getDescripcion());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los reportes
    public List<Reporte> obtenerTodosReportes() throws SQLException {
        String sql = "SELECT * FROM contabilidad";
        List<Reporte> reportes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reporte reporte = new Reporte();
                reporte.setIdReporte(rs.getInt("idcontabilidad"));
                reporte.setFecha(rs.getDate("fecha").toLocalDate());
                reporte.setIngresosTotales(rs.getDouble("total_ingresos"));
                reporte.setGastosTotales(rs.getDouble("total_egresos"));
                reporte.setBalance(rs.getDouble("balance"));
                reporte.setDescripcion(rs.getString("descripcion"));

                reportes.add(reporte);
            }
        }
        return reportes;
    }

    // Método para buscar un reporte por ID
    public Reporte buscarPorId(int idReporte) throws SQLException {
        String sql = "SELECT * FROM contabilidad WHERE idcontabilidad = ?";
        Reporte reporte = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idReporte);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reporte = new Reporte();
                reporte.setIdReporte(rs.getInt("idcontabilidad"));
                reporte.setFecha(rs.getDate("fecha").toLocalDate());
                reporte.setIngresosTotales(rs.getDouble("total_ingresos"));
                reporte.setGastosTotales(rs.getDouble("total_egresos"));
                reporte.setBalance(rs.getDouble("balance"));
                reporte.setDescripcion(rs.getString("descripcion"));
            }
        }
        return reporte;
    }

    // Método para actualizar un reporte
    public void actualizarReporte(Reporte reporte) throws SQLException {
        String sql = "UPDATE contabilidad SET fecha = ?, total_ingresos = ?, total_egresos = ?, balance = ?, descripcion = ? WHERE idcontabilidad = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(reporte.getFecha()));
            stmt.setDouble(2, reporte.getIngresosTotales());
            stmt.setDouble(3, reporte.getGastosTotales());
            stmt.setDouble(4, reporte.getBalance());
            stmt.setString(5, reporte.getDescripcion());
            stmt.setInt(6, reporte.getIdReporte());
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un reporte por ID
    public void eliminarReporte(int idReporte) throws SQLException {
        String sql = "DELETE FROM contabilidad WHERE idcontabilidad = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idReporte);
            stmt.executeUpdate();
        }
    }
}

