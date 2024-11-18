<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ingreso Vehículo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        h1 {
            text-align: center;
            color: #007bff;
        }
        .details {
            margin: 20px 0;
        }
        .details p {
            font-size: 16px;
            margin: 5px 0;
        }
        .back-btn {
            text-align: center;
            margin-top: 20px;
        }
        .back-btn button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .back-btn button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Ingreso Vehículo</h1>

    <div class="details">
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p style="color:red"><%= error %></p>
        <%
            } else {
        %>
            <p><strong>Número de Ticket:</strong> <%= request.getAttribute("ticketId") %></p>
            <p><strong>Placa:</strong> <%= request.getAttribute("plateNumber") %></p>
            <p><strong>Modelo:</strong> <%= request.getAttribute("model") %></p>
            <p><strong>Color:</strong> <%= request.getAttribute("color") %></p>
            <p><strong>Hora de Ingreso:</strong> <%= request.getAttribute("horaIngreso") %></p>
            <p><strong>Espacio de Parqueo Asignado:</strong> 
            <%= (request.getAttribute("espacioParqueo") != null ? request.getAttribute("espacioParqueo") : "No asignado") %>
            </p>
            <p><strong>Tarifa Asignada:</strong> 
            <%= (request.getAttribute("tarifa") != null ? request.getAttribute("tarifa") : "No asignada") %>
            </p>

        <% 
            }
        %>
    </div>

    <div class="back-btn">
        <a href="<%= request.getContextPath() %>/index.jsp">
            <button>Volver al Inicio</button>
        </a>
    </div>
</body>
</html>



