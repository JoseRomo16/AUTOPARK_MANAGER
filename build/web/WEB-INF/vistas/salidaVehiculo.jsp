<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Salida Vehículo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        p {
            font-size: 16px;
            color: #555;
            margin: 10px 0;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        .btn-container {
            text-align: center;
            margin-top: 20px;
        }
        .btn-container a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }
        .btn-container a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Salida Vehículo</h1>
        <% 
            String error = (String) request.getAttribute("error");

            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <% 
            } else { 
        %>
            <p><strong>Placa:</strong> <%= request.getAttribute("plateNumber") %></p>
            <p><strong>Hora de Ingreso:</strong> <%= request.getAttribute("horaIngreso") %></p>
            <p><strong>Hora de Salida:</strong> <%= request.getAttribute("horaSalida") %></p>
            <p><strong>Horas Estacionadas:</strong> <%= request.getAttribute("horasEstacionadas") %></p>
            <p><strong>Tarifa Total:</strong> $<%= request.getAttribute("tarifaTotal") %></p>
        <% 
            } 
        %>
        <div class="btn-container">
            <a href="<%= request.getContextPath() %>/index.jsp">Volver al Inicio</a>
        </div>
    </div>
</body>
</html>


