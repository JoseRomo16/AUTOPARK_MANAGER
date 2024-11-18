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
import java.util.HashMap;
import java.util.Map;

// Define el endpoint para el servlet
@WebServlet("/getRate")
public class TarifaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String vehicleType = request.getParameter("vehicleType");
        if (vehicleType == null || vehicleType.isEmpty()) {
            response.getWriter().println("Error: Debes seleccionar un tipo de vehículo.");
            return;
        }

        String query = """
            SELECT v.descripcion, t.precio_hora 
            FROM tarifa t 
            JOIN tipo_vehiculo v ON t.tipo_vehiculo_idtipo = v.idtipo 
            WHERE v.idtipo = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(vehicleType));
            ResultSet rs = stmt.executeQuery();

            Map<String, Map<String, Double>> tarifas = new HashMap<>();

            if (rs.next()) {
                String descripcion = rs.getString("descripcion");
                double precioHora = rs.getDouble("precio_hora");

                // Calcular tarifas adicionales
                double precioDia = precioHora * 8; // Supongamos 8 horas diarias
                double precioFinSemana = precioHora * 16; // 2 días
                double precioSemanal = precioHora * 40; // 5 días laborales
                double precioMensual = precioHora * 160; // 4 semanas

                Map<String, Double> precios = new HashMap<>();
                precios.put("Precio por Hora", precioHora);
                precios.put("Precio por Día", precioDia);
                precios.put("Precio por Fin de Semana", precioFinSemana);
                precios.put("Precio Semanal", precioSemanal);
                precios.put("Precio Mensual", precioMensual);

                tarifas.put(descripcion, precios);
            }

            // Añadir las tarifas al request para que sean accesibles en el JSP
            request.setAttribute("tarifas", tarifas);

            // Redirigir al JSP correspondiente
            request.getRequestDispatcher("WEB-INF/vistas/tarifas.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al obtener las tarifas: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.getWriter().println("Error: Tipo de vehículo inválido.");
        }
    }
}





