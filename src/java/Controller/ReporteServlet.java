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
import java.util.*;

@WebServlet("/reporte")
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String period = request.getParameter("period");
        LocalDateTime fechaInicio;

        // Determinar el rango de fechas basado en el periodo seleccionado
        switch (period) {
            case "1":
                fechaInicio = LocalDateTime.now().minusDays(1);
                break;
            case "7":
                fechaInicio = LocalDateTime.now().minusDays(7);
                break;
            case "30":
                fechaInicio = LocalDateTime.now().minusDays(30);
                break;
            case "180":
                fechaInicio = LocalDateTime.now().minusDays(180);
                break;
            default:
                request.setAttribute("error", "Por favor seleccione un período válido.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
        }

        String sqlIngresos = "SELECT DATE(hora_ingreso) AS fecha, SUM(tarifa_total) AS total_ingresos " +
                             "FROM ticket WHERE hora_ingreso >= ? GROUP BY DATE(hora_ingreso)";
        String sqlTotales = "SELECT SUM(tarifa_total) AS total " +
                            "FROM ticket WHERE hora_ingreso >= ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Consultar ingresos diarios
            PreparedStatement stmtIngresos = connection.prepareStatement(sqlIngresos);
            stmtIngresos.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            ResultSet rsIngresos = stmtIngresos.executeQuery();

            List<Map<String, Object>> ingresosPorDia = new ArrayList<>();
            while (rsIngresos.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("fecha", rsIngresos.getDate("fecha").toString());
                registro.put("total_ingresos", rsIngresos.getDouble("total_ingresos"));
                ingresosPorDia.add(registro);
            }

            // Consultar total general
            PreparedStatement stmtTotales = connection.prepareStatement(sqlTotales);
            stmtTotales.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            ResultSet rsTotales = stmtTotales.executeQuery();

            double totalGeneral = 0;
            if (rsTotales.next()) {
                totalGeneral = rsTotales.getDouble("total");
            }

            // Pasar datos al JSP
            request.setAttribute("ingresosPorDia", ingresosPorDia);
            request.setAttribute("totalGeneral", totalGeneral);
            request.setAttribute("period", period);
            request.getRequestDispatcher("WEB-INF/vistas/contabilidad.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar los datos de contabilidad: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}


