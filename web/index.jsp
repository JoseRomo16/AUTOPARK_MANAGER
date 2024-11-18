<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/style.css">
    <title>AutoPark Manager</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<header class="container-fluid bg-primary">
    <p class="text-light text-center fs-6 p-2">Contáctenos + 57 3164855814 / jlromo5@soy.sena.edu.co</p>
</header>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Auto Park Manager</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#registrar">Registrar Vehículo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#cobros">Cobros / Salida de Vehículos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#tarifas">Tarifas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#historial">Historial</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contabilidad">Contabilidad</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Header con Slide -->
<header class="bg-light py-5">
    <div class="container">
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" data-bs-interval="6000">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="img/capacityparking.jpg" class="d-block w-100 img-fluid" alt="Estado del parqueadero" style="object-fit: cover; height: 300px;">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Estado del Parqueadero</h5>
                        <p>Espacios ocupados / espacios libres.</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="img/tarifa.jpg" class="d-block w-100 img-fluid" alt="Tarifas" style="object-fit: cover; height: 300px;">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Consulta de Tarifas</h5>
                        <p>Automóvil: $3,000/hora, Motocicleta: $2,000/hora, Bicicleta $1,000/hora</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="img/history.jpg" class="d-block w-100 img-fluid" alt="Historial de vehículos" style="object-fit: cover; height: 300px;">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Historial de Vehículos</h5>
                        <p class="text-white">Consulta el historial de ingresos y salidas en tiempo real.</p>
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Anterior</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Siguiente</span>
            </button>
        </div>
    </div>
</header>

<!-- Section: Registrar Vehículo -->
<section id="registrar" class="py-5">
    <div class="container">
        <h2 class="text-center mb-4">Registrar Vehículo</h2>
        <form action="automovil" method="post">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="plateNumber" class="form-label">Número de Placa</label>
                    <input type="text" class="form-control" id="plateNumber" name="plateNumber" placeholder="Ingrese el número de placa" required>
                </div>
                <div class="col-md-6">
                    <label for="vehicleType" class="form-label">Tipo de Vehículo</label>
                    <select class="form-select" id="vehicleType" name="typeId" required>
                        <option selected value="">Seleccione...</option>
                        <option value="1">Automóvil</option>
                        <option value="2">Motocicleta</option>
                        <option value="3">Camioneta</option>
                        <option value="4">Bicicleta</option>
                    </select>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="model" class="form-label">Modelo</label>
                    <input type="text" class="form-control" id="model" name="model" placeholder="Ingrese el modelo del vehículo" required>
                </div>
                <div class="col-md-6">
                    <label for="color" class="form-label">Color</label>
                    <input type="text" class="form-control" id="color" name="color" placeholder="Ingrese el color del vehículo" required>
                </div>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Registrar</button>
            </div>
        </form>
    </div>
</section>

<!-- Section: Cobros / Salida de Vehículos -->
<section id="cobros" class="py-5 bg-light">
    <div class="container">
        <h2 class="text-center mb-4">Cobros / Salida de Vehículos</h2>
        <form action="processExit" method="post">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="exitPlateNumber" class="form-label">Número de Placa</label>
                    <input type="text" class="form-control" id="exitPlateNumber" name="exitPlateNumber" placeholder="Ingrese el número de placa" required>
                </div>
                <div class="col-md-6">
                    <label for="exitTime" class="form-label">Hora de Salida</label>
                    <input type="time" class="form-control" id="exitTime" name="exitTime" required>
                </div>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-success">Procesar Salida</button>
            </div>
        </form>
    </div>
</section>

<!-- Section: Tarifas -->
<section id="tarifas" class="py-5">
    <div class="container">
        <h2 class="text-center mb-4">Tarifas</h2>
        <form action="getRate" method="get">
            <div class="mb-3">
                <label for="vehicleTypeTarifas" class="form-label">Seleccione el tipo de vehículo:</label>
                <select class="form-select" id="vehicleTypeTarifas" name="vehicleType" required>
                    <option selected="">Seleccione...</option>
                    <option value="1">Automóvil</option>
                    <option value="2">Motocicleta</option>
                    <option value="3">Camioneta</option>
                    <option value="4">Bicicleta</option>
                </select>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Consultar Tarifa</button>
            </div>
        </form>
    </div>
</section>

<!-- Section: Historial -->
<section id="historial" class="py-5 bg-light">
    <div class="container">
        <h2 class="text-center mb-4">Historial de Vehículos</h2>
        <form action="historialEstadisticas" method="get">
            <div class="mb-3">
                <label for="timePeriod" class="form-label">Seleccione el periodo de tiempo:</label>
                <select class="form-select" id="timePeriod" name="timePeriod" required>
                    <option selected="">Seleccione...</option>
                    <option value="1">Últimas 24 horas</option>
                    <option value="2">Última semana</option>
                    <option value="3">Último mes</option>
                    <option value="4">Últimos 6 meses</option>
                </select>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-secondary">Consultar Historial</button>
            </div>
        </form>
    </div>
</section>

<!-- Section: Contabilidad -->
<section id="contabilidad" class="py-5">
    <div class="container">
        <h2 class="text-center mb-4">Contabilidad</h2>
        <form action="reporte" method="get">
            <div class="mb-3">
                <label for="fechaContabilidad" class="form-label">Seleccione el periodo de tiempo:</label>
                <select class="form-select" id="fechaContabilidad" name="period" required>
                    <option selected="">Seleccione...</option>
                    <option value="1">Hoy</option>
                    <option value="7">Últimos 7 días</option>
                    <option value="30">Último mes</option>
                    <option value="180">Últimos 6 meses</option>
                </select>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-success">Consultar Ingresos</button>
            </div>
        </form>
    </div>
</section>

<!-- Footer -->
<footer class="bg-dark text-white text-center py-3">
    <div class="container">
        <p class="mb-0">© 2024 Auto Park Manager. Todos los derechos reservados.</p>
    </div>
</footer>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>




