package controller;

import config.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/historialEstadisticas")
public class HistorialEstadisticasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timePeriod = request.getParameter("timePeriod");
        String rangoSQL = ""; // Variable para el filtro de tiempo en las consultas
        LocalDateTime fechaInicio = null;

        // Determinar el rango de tiempo basado en `timePeriod`
        switch (timePeriod) {
            case "1": // Últimas 24 horas
                fechaInicio = LocalDateTime.now().minusHours(24);
                rangoSQL = "hora_ingreso >= ?";
                break;
            case "2": // Última semana
                fechaInicio = LocalDateTime.now().minusWeeks(1);
                rangoSQL = "hora_ingreso >= ?";
                break;
            case "3": // Último mes
                fechaInicio = LocalDateTime.now().minusMonths(1);
                rangoSQL = "hora_ingreso >= ?";
                break;
            case "4": // Últimos 6 meses
                fechaInicio = LocalDateTime.now().minusMonths(6);
                rangoSQL = "hora_ingreso >= ?";
                break;
            default:
                request.setAttribute("error", "Seleccione un rango de tiempo válido.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Consulta 1: Historial de vehículos
            String sqlHistorial = "SELECT t.idticket, a.placa, t.hora_ingreso, t.hora_salida, t.tarifa_total " +
                                  "FROM ticket t " +
                                  "JOIN automovil a ON t.automovil_idautomovil = a.idautomovil " +
                                  "WHERE " + rangoSQL + " ORDER BY t.hora_ingreso DESC";
            PreparedStatement stmtHistorial = connection.prepareStatement(sqlHistorial);
            stmtHistorial.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            ResultSet rsHistorial = stmtHistorial.executeQuery();

            List<Map<String, Object>> historial = new ArrayList<>();
            while (rsHistorial.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("idticket", rsHistorial.getInt("idticket"));
                registro.put("placa", rsHistorial.getString("placa"));
                registro.put("hora_ingreso", rsHistorial.getTimestamp("hora_ingreso"));
                registro.put("hora_salida", rsHistorial.getTimestamp("hora_salida"));
                registro.put("tarifa_total", rsHistorial.getDouble("tarifa_total"));
                historial.add(registro);
            }

            // Consulta 2: Estadísticas por día
            String sqlEstadisticasPorDia = "SELECT DATE(hora_ingreso) AS dia, COUNT(*) AS total " +
                                           "FROM ticket WHERE " + rangoSQL + " GROUP BY dia";
            PreparedStatement stmtPorDia = connection.prepareStatement(sqlEstadisticasPorDia);
            stmtPorDia.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            ResultSet rsPorDia = stmtPorDia.executeQuery();

            List<Map<String, Object>> estadisticasPorDia = new ArrayList<>();
            while (rsPorDia.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("dia", rsPorDia.getDate("dia").toString());
                registro.put("total", rsPorDia.getInt("total"));
                estadisticasPorDia.add(registro);
            }

            // Consulta 3: Estadísticas por tipo de vehículo
            String sqlPorTipoVehiculo = "SELECT v.descripcion AS tipo_vehiculo, COUNT(*) AS total " +
                                        "FROM ticket t " +
                                        "JOIN automovil a ON t.automovil_idautomovil = a.idautomovil " +
                                        "JOIN tipo_vehiculo v ON a.tipo_vehiculo_idtipo = v.idtipo " +
                                        "WHERE " + rangoSQL + " GROUP BY v.descripcion";
            PreparedStatement stmtPorTipoVehiculo = connection.prepareStatement(sqlPorTipoVehiculo);
            stmtPorTipoVehiculo.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            ResultSet rsPorTipoVehiculo = stmtPorTipoVehiculo.executeQuery();

            List<Map<String, Object>> estadisticasPorTipoVehiculo = new ArrayList<>();
            while (rsPorTipoVehiculo.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("tipo_vehiculo", rsPorTipoVehiculo.getString("tipo_vehiculo"));
                registro.put("total", rsPorTipoVehiculo.getInt("total"));
                estadisticasPorTipoVehiculo.add(registro);
            }

            // Pasar datos al JSP
            request.setAttribute("historial", historial);
            request.setAttribute("estadisticasPorDia", estadisticasPorDia);
            request.setAttribute("estadisticasPorTipoVehiculo", estadisticasPorTipoVehiculo);

            request.getRequestDispatcher("WEB-INF/vistas/historialEstadisticas.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los datos: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}

