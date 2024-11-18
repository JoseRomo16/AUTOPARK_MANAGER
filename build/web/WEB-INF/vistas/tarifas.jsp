<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tarifas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .title {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }
        .tarifa-item {
            border-bottom: 1px solid #ddd;
            padding: 15px 0;
        }
        .tarifa-item:last-child {
            border-bottom: none;
        }
        .tarifa-details ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        .tarifa-details li {
            margin-bottom: 8px;
            font-size: 14px;
            color: #555;
        }
        .back-btn {
            display: block;
            margin: 20px auto;
            text-align: center;
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
    <div class="container">
        <h1 class="title">Tarifas</h1>
        <div>
            <%
                Map<String, Map<String, Double>> tarifas = (Map<String, Map<String, Double>>) request.getAttribute("tarifas");
                if (tarifas != null && !tarifas.isEmpty()) {
                    for (Map.Entry<String, Map<String, Double>> entry : tarifas.entrySet()) {
                        String descripcion = entry.getKey();
                        Map<String, Double> precios = entry.getValue();
            %>
                        <div class="tarifa-item">
                            <h3><%= descripcion %></h3>
                            <ul>
                                <li>Precio por Hora: $<%= precios.get("Precio por Hora") %></li>
                                <li>Precio por Día: $<%= precios.get("Precio por Día") %></li>
                                <li>Precio por Fin de Semana: $<%= precios.get("Precio por Fin de Semana") %></li>
                                <li>Precio Semanal: $<%= precios.get("Precio Semanal") %></li>
                                <li>Precio Mensual: $<%= precios.get("Precio Mensual") %></li>
                            </ul>
                        </div>
            <%
                    }
                } else {
            %>
                <p>No se encontraron tarifas para el tipo de vehículo especificado.</p>
            <%
                }
            %>
        </div>
        <div class="back-btn">
            <a href="<%= request.getContextPath() %>/index.jsp"><button>Volver al Inicio</button></a>
        </div>
    </div>
</body>
</html>

