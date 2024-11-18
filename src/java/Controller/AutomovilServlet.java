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

@WebServlet("/automovil")
public class AutomovilServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String plateNumber = request.getParameter("plateNumber");
        String typeId = request.getParameter("typeId");
        String model = request.getParameter("model");
        String color = request.getParameter("color");

        if (plateNumber == null || typeId == null || model == null || color == null ||
            plateNumber.isEmpty() || typeId.isEmpty() || model.isEmpty() || color.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("WEB-INF/vistas/ingresoVehiculo.jsp").forward(request, response);
            return;
        }

        String checkAutomovilSql = "SELECT idautomovil, en_parqueo FROM automovil WHERE placa = ?";
        String updateAutomovilSql = "UPDATE automovil SET en_parqueo = TRUE WHERE idautomovil = ?";
        String selectSpaceSql = "SELECT idespacio_parqueo FROM espacio_parqueo WHERE tipo_espacio = ? AND estado = 'LIBRE' LIMIT 1";
        String selectTarifaSql = "SELECT idtarifa FROM tarifa WHERE tipo_vehiculo_idtipo = ? LIMIT 1";
        String updateSpaceSql = "UPDATE espacio_parqueo SET estado = 'OCUPADO' WHERE idespacio_parqueo = ?";
        String insertAutomovilSql = "INSERT INTO automovil (placa, modelo, color, tipo_vehiculo_idtipo, cliente_idcliente) VALUES (?, ?, ?, ?, ?)";
        String insertTicketSql = "INSERT INTO ticket (hora_ingreso, automovil_idautomovil, espacio_parqueo_idespacio_parqueo, tarifa_idtarifa) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement checkAutomovilStmt = connection.prepareStatement(checkAutomovilSql);
            checkAutomovilStmt.setString(1, plateNumber);
            ResultSet automovilRs = checkAutomovilStmt.executeQuery();

            int idAutomovil;
            boolean enParqueo = false;

            if (automovilRs.next()) {
                idAutomovil = automovilRs.getInt("idautomovil");
                enParqueo = automovilRs.getBoolean("en_parqueo");

                if (enParqueo) {
                    request.setAttribute("error", "El vehículo ya está registrado en el parqueadero.");
                    request.getRequestDispatcher("WEB-INF/vistas/ingresoVehiculo.jsp").forward(request, response);
                    connection.rollback();
                    return;
                }

                PreparedStatement updateAutomovilStmt = connection.prepareStatement(updateAutomovilSql);
                updateAutomovilStmt.setInt(1, idAutomovil);
                updateAutomovilStmt.executeUpdate();
            } else {
                PreparedStatement insertAutomovilStmt = connection.prepareStatement(insertAutomovilSql, Statement.RETURN_GENERATED_KEYS);
                insertAutomovilStmt.setString(1, plateNumber);
                insertAutomovilStmt.setString(2, model);
                insertAutomovilStmt.setString(3, color);
                insertAutomovilStmt.setInt(4, Integer.parseInt(typeId));
                insertAutomovilStmt.setInt(5, 1);
                insertAutomovilStmt.executeUpdate();

                ResultSet generatedKeys = insertAutomovilStmt.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new SQLException("No se pudo registrar el automóvil.");
                }

                idAutomovil = generatedKeys.getInt(1);
            }

            PreparedStatement selectSpaceStmt = connection.prepareStatement(selectSpaceSql);
            selectSpaceStmt.setString(1, getTipoEspacio(typeId));
            ResultSet spaceRs = selectSpaceStmt.executeQuery();

            if (!spaceRs.next()) {
                request.setAttribute("error", "No hay espacios de parqueo disponibles.");
                request.getRequestDispatcher("WEB-INF/vistas/ingresoVehiculo.jsp").forward(request, response);
                connection.rollback();
                return;
            }

            int idEspacioParqueo = spaceRs.getInt("idespacio_parqueo");

            PreparedStatement selectTarifaStmt = connection.prepareStatement(selectTarifaSql);
            selectTarifaStmt.setInt(1, Integer.parseInt(typeId));
            ResultSet tarifaRs = selectTarifaStmt.executeQuery();

            if (!tarifaRs.next()) {
                request.setAttribute("error", "No se encontró una tarifa para este tipo de vehículo.");
                request.getRequestDispatcher("WEB-INF/vistas/ingresoVehiculo.jsp").forward(request, response);
                connection.rollback();
                return;
            }

            int idTarifa = tarifaRs.getInt("idtarifa");

            PreparedStatement insertTicketStmt = connection.prepareStatement(insertTicketSql, Statement.RETURN_GENERATED_KEYS);
            insertTicketStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().withNano(0)));
            insertTicketStmt.setInt(2, idAutomovil);
            insertTicketStmt.setInt(3, idEspacioParqueo);
            insertTicketStmt.setInt(4, idTarifa);
            insertTicketStmt.executeUpdate();

            ResultSet ticketGeneratedKeys = insertTicketStmt.getGeneratedKeys();
            if (!ticketGeneratedKeys.next()) {
                throw new SQLException("No se pudo generar el ticket.");
            }

            int ticketId = ticketGeneratedKeys.getInt(1);

            PreparedStatement updateSpaceStmt = connection.prepareStatement(updateSpaceSql);
            updateSpaceStmt.setInt(1, idEspacioParqueo);
            updateSpaceStmt.executeUpdate();

            connection.commit();

            request.setAttribute("ticketId", ticketId);
            request.setAttribute("plateNumber", plateNumber);
            request.setAttribute("model", model);
            request.setAttribute("color", color);
            request.setAttribute("horaIngreso", Timestamp.valueOf(LocalDateTime.now().withNano(0)));
            request.setAttribute("espacioParqueo", idEspacioParqueo);
            request.setAttribute("tarifa", idTarifa);
            request.getRequestDispatcher("WEB-INF/vistas/ingresoVehiculo.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar el automóvil: " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/ingresoVehiculo.jsp").forward(request, response);
        }
    }

    private String getTipoEspacio(String typeId) {
        switch (typeId) {
            case "1":
                return "Automóvil";
            case "2":
                return "Motocicleta";
            case "3":
                return "Camioneta";
            case "4":
                return "Bicicleta";
            default:
                return null;
        }
    }
}






