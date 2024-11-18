<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historial y Estadísticas</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            text-align: center;
        }
        table {
            margin: 0 auto;
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        canvas {
            margin: 20px auto;
            display: block;
        }
        .back-button {
            margin-top: 20px;
        }
        .back-button button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .back-button button:hover {
            background-color: #0056b3;
        }
        
        h1 {color: #2c3e50; /* Azul oscuro */
            }

        h2 {color: #16a085; /* Verde */
            }           

        h3 {color: #2980b9; /* Azul intermedio */
            }
    </style>
</head>
<body>
<%
    String timePeriod = request.getParameter("timePeriod");
    String tituloPeriodo = "";

    switch (timePeriod) {
        case "1":
            tituloPeriodo = "Últimas 24 Horas";
            break;
        case "2":
            tituloPeriodo = "Última Semana";
            break;
        case "3":
            tituloPeriodo = "Último Mes";
            break;
        case "4":
            tituloPeriodo = "Últimos 6 Meses";
            break;
        default:
            tituloPeriodo = "Período no especificado";
            break;
    }
%>

    <div class="container">
        <h1>Historial de Vehículos</h1>
        <h2><%= tituloPeriodo %></h2>

        <!-- Mostrar tabla de historial -->
        <table>
            <thead>
                <tr>
                    <th>Ticket</th>
                    <th>Placa</th>
                    <th>Hora Ingreso</th>
                    <th>Hora Salida</th>
                    <th>Tarifa Total</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> historial = (List<Map<String, Object>>) request.getAttribute("historial");
                    if (historial != null) {
                        for (Map<String, Object> registro : historial) {
                            out.println("<tr>");
                            out.println("<td>" + registro.get("idticket") + "</td>");
                            out.println("<td>" + registro.get("placa") + "</td>");
                            out.println("<td>" + registro.get("hora_ingreso") + "</td>");
                            out.println("<td>" + registro.get("hora_salida") + "</td>");
                            out.println("<td>$" + registro.get("tarifa_total") + "</td>");
                            out.println("</tr>");
                        }
                    } else {
                        out.println("<tr><td colspan='5'>No se encontraron datos.</td></tr>");
                    }
                %>
            </tbody>
        </table>

        <h2>Gráfico de Ingresos por Día</h2>
        <canvas id="graficoPorDia" width="800" height="400"></canvas>
        <script>
            const datosPorDia = [
                <%
                    List<Map<String, Object>> estadisticasPorDia = (List<Map<String, Object>>) request.getAttribute("estadisticasPorDia");
                    if (estadisticasPorDia != null) {
                        for (Map<String, Object> registro : estadisticasPorDia) {
                            out.print("{dia: '" + registro.get("dia") + "', total: " + registro.get("total") + "},");
                        }
                    }
                %>
            ];

            const labelsPorDia = datosPorDia.map(d => d.dia);
            const dataPorDia = datosPorDia.map(d => d.total);

            new Chart(document.getElementById('graficoPorDia'), {
                type: 'bar',
                data: {
                    labels: labelsPorDia,
                    datasets: [{
                        label: 'Ingresos por Día',
                        data: dataPorDia,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        </script>

        <h2>Gráfico de Ingresos por Tipo de Vehículo</h2>
        <canvas id="graficoPorTipo" width="500" height="300"></canvas>
        <script>
            const datosPorTipo = [
                <%
                    List<Map<String, Object>> estadisticasPorTipoVehiculo = (List<Map<String, Object>>) request.getAttribute("estadisticasPorTipoVehiculo");
                    if (estadisticasPorTipoVehiculo != null) {
                        for (Map<String, Object> registro : estadisticasPorTipoVehiculo) {
                            out.print("{tipo_vehiculo: '" + registro.get("tipo_vehiculo") + "', total: " + registro.get("total") + "},");
                        }
                    }
                %>
            ];

            const labelsPorTipo = datosPorTipo.map(d => d.tipo_vehiculo);
            const dataPorTipo = datosPorTipo.map(d => d.total);

            new Chart(document.getElementById('graficoPorTipo'), {
                type: 'pie',
                data: {
                    labels: labelsPorTipo,
                    datasets: [{
                        data: dataPorTipo,
                        backgroundColor: ['#4CAF50', '#FFC107', '#2196F3', '#9C27B0'] 
                    }]
                }
            });
        </script>

        <div class="back-button">
        <a href="<%= request.getContextPath() %>/index.jsp">
        <button style="margin-top: 40px; padding: 15px 25px; background-color: #007bff; color: white; border: none; border-radius: 10px; cursor: pointer;">Volver al Inicio</button>
        </a>
        </div>
    </div>
</body>
</html>

