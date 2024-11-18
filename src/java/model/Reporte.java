package model;

import java.time.LocalDate;

public class Reporte {
    private int idReporte;
    private LocalDate fecha;
    private double ingresosTotales;
    private double gastosTotales;
    private double balance;
    private String descripcion;

    // Constructor con parámetros
    public Reporte(int idReporte, LocalDate fecha, double ingresosTotales, double gastosTotales, double balance, String descripcion) {
        this.idReporte = idReporte;
        this.fecha = fecha;
        this.ingresosTotales = ingresosTotales;
        this.gastosTotales = gastosTotales;
        this.balance = balance;
        this.descripcion = descripcion;
    }

    // Constructor vacío
    public Reporte() {}

    // Getters y Setters

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public double getGastosTotales() {
        return gastosTotales;
    }

    public void setGastosTotales(double gastosTotales) {
        this.gastosTotales = gastosTotales;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

