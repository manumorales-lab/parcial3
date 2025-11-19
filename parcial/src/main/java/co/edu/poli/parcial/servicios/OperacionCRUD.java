package co.edu.poli.parcial.servicios;

import co.edu.poli.parcial.model.Producto;
import java.util.List;

public interface OperacionCRUD {
    boolean crearProducto(Producto producto);
    Producto leerProducto(int codigo);
    boolean actualizarProducto(Producto producto);
    boolean eliminarProducto(int codigo);
    boolean serializarProducto(String archivo);
    boolean deserializarProducto(String archivo);
    List<Producto> getTodosLosProductos();
    int getContador();
}