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
import java.time.temporal.ChronoUnit;

@WebServlet("/processExit")
public class BillingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String plateNumber = request.getParameter("exitPlateNumber");

            if (plateNumber == null || plateNumber.isEmpty()) {
                request.setAttribute("error", "El número de placa es obligatorio.");
                request.getRequestDispatcher("/WEB-INF/vistas/salidaVehiculo.jsp").forward(request, response);
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                connection.setAutoCommit(false);

                // 1. Verificar que el vehículo está en el parqueadero
                String selectTicketSql = """
                    SELECT t.idticket, t.hora_ingreso, t.espacio_parqueo_idespacio_parqueo, r.precio_hora, a.idautomovil
                    FROM ticket t
                    JOIN automovil a ON t.automovil_idautomovil = a.idautomovil
                    JOIN tarifa r ON t.tarifa_idtarifa = r.idtarifa
                    WHERE a.placa = ? AND t.hora_salida IS NULL
                """;

                PreparedStatement selectStmt = connection.prepareStatement(selectTicketSql);
                selectStmt.setString(1, plateNumber);
                ResultSet rs = selectStmt.executeQuery();

                if (!rs.next()) {
                    request.setAttribute("error", "El vehículo no se encuentra en el parqueadero.");
                    request.getRequestDispatcher("/WEB-INF/vistas/salidaVehiculo.jsp").forward(request, response);
                    connection.rollback();
                    return;
                }

                // Obtener información del ticket
                int ticketId = rs.getInt("idticket");
                LocalDateTime horaIngreso = rs.getTimestamp("hora_ingreso").toLocalDateTime();
                int idEspacioParqueo = rs.getInt("espacio_parqueo_idespacio_parqueo");
                double precioHora = rs.getDouble("precio_hora");
                int idAutomovil = rs.getInt("idautomovil");

                // 2. Calcular tarifa total
                LocalDateTime horaSalida = LocalDateTime.now();
                long horasEstacionadas = ChronoUnit.HOURS.between(horaIngreso, horaSalida);
                if (horasEstacionadas == 0) {
                    horasEstacionadas = 1; // Mínimo una hora de cobro
                }
                double tarifaTotal = horasEstacionadas * precioHora;

                // 3. Actualizar el ticket con la hora de salida y la tarifa total
                String updateTicketSql = "UPDATE ticket SET hora_salida = ?, tarifa_total = ? WHERE idticket = ?";
                PreparedStatement updateTicketStmt = connection.prepareStatement(updateTicketSql);
                updateTicketStmt.setTimestamp(1, Timestamp.valueOf(horaSalida));
                updateTicketStmt.setDouble(2, tarifaTotal);
                updateTicketStmt.setInt(3, ticketId);
                updateTicketStmt.executeUpdate();

                // 4. Liberar el espacio de parqueo
                String liberarEspacioSql = "UPDATE espacio_parqueo SET estado = 'LIBRE' WHERE idespacio_parqueo = ?";
                PreparedStatement liberarStmt = connection.prepareStatement(liberarEspacioSql);
                liberarStmt.setInt(1, idEspacioParqueo);
                liberarStmt.executeUpdate();

                // 5. Actualizar el estado del vehículo en la tabla automovil
                String updateAutomovilSql = "UPDATE automovil SET en_parqueo = FALSE WHERE idautomovil = ?";
                PreparedStatement updateAutomovilStmt = connection.prepareStatement(updateAutomovilSql);
                updateAutomovilStmt.setInt(1, idAutomovil);
                updateAutomovilStmt.executeUpdate();

                connection.commit();

                // Pasar los datos al JSP mediante atributos del request
                request.setAttribute("plateNumber", plateNumber);
                request.setAttribute("horaIngreso", horaIngreso.withNano(0));
                request.setAttribute("horaSalida", horaSalida.withNano(0));
                request.setAttribute("horasEstacionadas", horasEstacionadas);
                request.setAttribute("tarifaTotal", tarifaTotal);

                // Redirigir al JSP
                request.getRequestDispatcher("/WEB-INF/vistas/salidaVehiculo.jsp").forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al procesar la salida: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/vistas/salidaVehiculo.jsp").forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/vistas/salidaVehiculo.jsp").forward(request, response);
        }
    }
}











