package model;

public class Automovil {
    private int idAutomovil;
    private String placa;
    private String modelo;
    private String color;
    private int idCliente;
    private int idTipoVehiculo;

    public Automovil(String placa, String modelo, String color, int idCliente, int idTipoVehiculo) {
        this.placa = placa;
        this.modelo = modelo;
        this.color = color;
        this.idCliente = idCliente;
        this.idTipoVehiculo = idTipoVehiculo;
    }

    // Constructor vacío (opcional si es requerido)
    public Automovil() {}

    // Getters y Setters

    public int getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(int idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(int idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }
}

