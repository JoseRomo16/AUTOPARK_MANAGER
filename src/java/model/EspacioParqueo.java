package model;

public class EspacioParqueo {
    private int idEspacioParqueo;
    private String numeroEspacio;
    private String ubicacion;
    private String estado; // Libre, Ocupado
    private String tipoEspacio; // Tipo de espacio (Automóvil, Motocicleta, Bicicleta, etc.)

    // Constructor con parámetros
    public EspacioParqueo(int idEspacioParqueo, String numeroEspacio, String ubicacion, String estado, String tipoEspacio) {
        this.idEspacioParqueo = idEspacioParqueo;
        this.numeroEspacio = numeroEspacio;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.tipoEspacio = tipoEspacio;
    }

    // Constructor vacío
    public EspacioParqueo() {}

    // Getters y Setters

    public int getIdEspacioParqueo() {
        return idEspacioParqueo;
    }

    public void setIdEspacioParqueo(int idEspacioParqueo) {
        this.idEspacioParqueo = idEspacioParqueo;
    }

    public String getNumeroEspacio() {
        return numeroEspacio;
    }

    public void setNumeroEspacio(String numeroEspacio) {
        this.numeroEspacio = numeroEspacio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }
}

