package co.edu.poli.parcial.model;

import java.io.Serializable;

public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String pais;
    private String nombre;

    public Proveedor() {
    }

    public Proveedor(int id, String pais, String nombre) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return nombre + " (" + pais + ")";
    }
}