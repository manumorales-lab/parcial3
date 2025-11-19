package co.edu.poli.parcial.servicios;

import co.edu.poli.parcial.model.Producto;

public interface OperacionCRUD {
    boolean crearProducto(Producto producto);
    Producto leerProducto(int codigo);
    boolean actualizarProducto(Producto producto);
    boolean eliminarProducto(int codigo);
    boolean serializarProducto(String archivo);
    Producto deserializarProducto(String archivo);
}