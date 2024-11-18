<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reporte de Contabilidad</title>
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
            margin: 20px auto;
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
            margin-top: 30px;
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
        h1 {
    color: #2c3e50; /* Azul oscuro */
}

h2 {
    color: #16a085; /* Verde */
}

h3 {
    color: #2980b9; /* Azul intermedio */
}
    </style>
</head>
<body>
    <div class="container">
        <h1>Reporte de Contabilidad</h1>

        <%
            String period = request.getParameter("period");
            String periodText = "Período seleccionado: ";
            switch (period) {
                case "1": periodText += "Hoy"; break;
                case "7": periodText += "Últimos 7 días"; break;
                case "30": periodText += "Último mes"; break;
                case "180": periodText += "Últimos 6 meses"; break;
                default: periodText += "Desconocido"; break;
            }
        %>
        <h3><%= periodText %></h3>

        <h2>Ingresos Diarios</h2>
        <table>
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Total Ingresos</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> ingresosPorDia = (List<Map<String, Object>>) request.getAttribute("ingresosPorDia");
                    if (ingresosPorDia != null) {
                        for (Map<String, Object> ingreso : ingresosPorDia) {
                            out.println("<tr>");
                            out.println("<td>" + ingreso.get("fecha") + "</td>");
                            out.println("<td>$" + ingreso.get("total_ingresos") + "</td>");
                            out.println("</tr>");
                        }
                    } else {
                        out.println("<tr><td colspan='2'>No se encontraron datos.</td></tr>");
                    }
                %>
            </tbody>
        </table>

        <h2>Gráfico de Ingresos Diarios</h2>
        <canvas id="graficoIngresos" width="600" height="400"></canvas>
        <script>
            const datosIngresos = [
                <%
                    if (ingresosPorDia != null) {
                        for (Map<String, Object> ingreso : ingresosPorDia) {
                            out.print("{fecha: '" + ingreso.get("fecha") + "', total: " + ingreso.get("total_ingresos") + "},");
                        }
                    }
                %>
            ];
            const labelsIngresos = datosIngresos.map(d => d.fecha);
            const dataIngresos = datosIngresos.map(d => d.total);

            new Chart(document.getElementById('graficoIngresos'), {
                type: 'bar',
                data: {
                    labels: labelsIngresos,
                    datasets: [{
                        label: 'Ingresos por Día',
                        data: dataIngresos,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
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

        <h2>Total General</h2>
        <p>Ingresos totales: $<%= request.getAttribute("totalGeneral") %></p>

        <div class="back-button">
            <a href="<%= request.getContextPath() %>/index.jsp">
                <button>Volver al Inicio</button>
            </a>
        </div>
    </div>
</body>
</html>


