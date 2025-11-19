package co.edu.poli.parcial.model;

import java.io.Serializable;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int codigo;
    private String nombre;
    private double precio;
    private int stock;
    private Proveedor proveedor;
    private Electronico electronico;
    private Ropa ropa;

    public Producto() {
    }

    public Producto(int codigo, String nombre, double precio, int stock, Proveedor proveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    // Getters y Setters (los mismos que antes)
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
    public Electronico getElectronico() { return electronico; }
    public void setElectronico(Electronico electronico) { this.electronico = electronico; }
    public Ropa getRopa() { return ropa; }
    public void setRopa(Ropa ropa) { this.ropa = ropa; }

    public String getTipo() {
        if (electronico != null) return "Electrónico";
        else if (ropa != null) return "Ropa";
        else return "General";
    }

    @Override
    public String toString() {
        return "Producto{codigo=" + codigo + ", nombre='" + nombre + "', precio=" + precio + 
               ", stock=" + stock + ", proveedor=" + proveedor + ", tipo=" + getTipo() + "}";
    }
}