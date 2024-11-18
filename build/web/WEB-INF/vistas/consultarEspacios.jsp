<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultar Espacios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-5">
    <h2 class="text-center mb-4">Consultar Espacios Disponibles</h2>
    <form action="EspacioServlet" method="get">
        <div class="mb-3">
            <label for="parkingStatus" class="form-label">Estado de los Espacios:</label>
            <select class="form-select" id="parkingStatus" name="status">
                <option value="ALL">Todos</option>
                <option value="LIBRE">Libres</option>
                <option value="OCUPADO">Ocupados</option>
            </select>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">Consultar</button>
        </div>
    </form>
</div>
</body>
</html>

