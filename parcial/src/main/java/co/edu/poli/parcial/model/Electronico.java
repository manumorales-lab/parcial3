package co.edu.poli.parcial.model;

import java.io.Serializable;

public class Electronico implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int codigo;
    private String nombre;
    private double precio;
    private Proveedor proveedor;
    private int garantiaMeses;

    public Electronico() {
    }

    public Electronico(int codigo, String nombre, double precio, Proveedor proveedor, int garantiaMeses) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.proveedor = proveedor;
        this.garantiaMeses = garantiaMeses;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public int getGarantiaMeses() { return garantiaMeses; }
    public void setGarantiaMeses(int garantiaMeses) { this.garantiaMeses = garantiaMeses; }

    @Override
    public String toString() {
        return "Electronico{codigo=" + codigo + ", nombre='" + nombre + "', precio=" + precio + 
               ", proveedor=" + proveedor + ", garantiaMeses=" + garantiaMeses + "}";
    }
}