package dao;

import config.DatabaseConnection;
import model.EspacioParqueo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EspacioParqueoDAO {

    // Método para agregar un nuevo espacio de parqueo
    public void agregarEspacioParqueo(EspacioParqueo espacioParqueo) throws SQLException {
        String sql = "INSERT INTO espacio_parqueo (numero_espacio, ubicacion, estado, tipo_espacio) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, espacioParqueo.getNumeroEspacio());
            stmt.setString(2, espacioParqueo.getUbicacion());
            stmt.setString(3, espacioParqueo.getEstado());
            stmt.setString(4, espacioParqueo.getTipoEspacio());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los espacios de parqueo
    public List<EspacioParqueo> obtenerTodosEspacios() throws SQLException {
        String sql = "SELECT * FROM espacio_parqueo";
        List<EspacioParqueo> espacios = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EspacioParqueo espacio = new EspacioParqueo();
                espacio.setIdEspacioParqueo(rs.getInt("idespacio_parqueo"));
                espacio.setNumeroEspacio(rs.getString("numero_espacio"));
                espacio.setUbicacion(rs.getString("ubicacion"));
                espacio.setEstado(rs.getString("estado"));
                espacio.setTipoEspacio(rs.getString("tipo_espacio"));

                espacios.add(espacio);
            }
        }
        return espacios;
    }

    // Método para buscar un espacio de parqueo por ID
    public EspacioParqueo buscarPorId(int idEspacioParqueo) throws SQLException {
        String sql = "SELECT * FROM espacio_parqueo WHERE idespacio_parqueo = ?";
        EspacioParqueo espacio = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEspacioParqueo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                espacio = new EspacioParqueo();
                espacio.setIdEspacioParqueo(rs.getInt("idespacio_parqueo"));
                espacio.setNumeroEspacio(rs.getString("numero_espacio"));
                espacio.setUbicacion(rs.getString("ubicacion"));
                espacio.setEstado(rs.getString("estado"));
                espacio.setTipoEspacio(rs.getString("tipo_espacio"));
            }
        }
        return espacio;
    }

    // Método para actualizar un espacio de parqueo
    public void actualizarEspacioParqueo(EspacioParqueo espacioParqueo) throws SQLException {
        String sql = "UPDATE espacio_parqueo SET numero_espacio = ?, ubicacion = ?, estado = ?, tipo_espacio = ? WHERE idespacio_parqueo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, espacioParqueo.getNumeroEspacio());
            stmt.setString(2, espacioParqueo.getUbicacion());
            stmt.setString(3, espacioParqueo.getEstado());
            stmt.setString(4, espacioParqueo.getTipoEspacio());
            stmt.setInt(5, espacioParqueo.getIdEspacioParqueo());
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un espacio de parqueo por ID
    public void eliminarEspacioParqueo(int idEspacioParqueo) throws SQLException {
        String sql = "DELETE FROM espacio_parqueo WHERE idespacio_parqueo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEspacioParqueo);
            stmt.executeUpdate();
        }
    }
}

