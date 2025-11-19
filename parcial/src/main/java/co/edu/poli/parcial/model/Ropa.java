package co.edu.poli.parcial.model;

import java.io.Serializable;

public class Ropa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int codigo;
    private String nombre;
    private double precio;
    private int stock;
    private Proveedor proveedor;
    private String talla;

    public Ropa() {
    }

    public Ropa(int codigo, String nombre, double precio, int stock, Proveedor proveedor, String talla) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
        this.talla = talla;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    @Override
    public String toString() {
        return "Ropa{codigo=" + codigo + ", nombre='" + nombre + "', precio=" + precio + 
               ", stock=" + stock + ", proveedor=" + proveedor + ", talla='" + talla + "'}";
    }
}