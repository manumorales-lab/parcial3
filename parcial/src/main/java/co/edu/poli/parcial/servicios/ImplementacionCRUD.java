package co.edu.poli.parcial.servicios;

import co.edu.poli.parcial.model.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionCRUD implements OperacionCRUD {
    private List<Producto> productos;
    private int contador;

    public ImplementacionCRUD() {
        this.productos = new ArrayList<>();
        this.contador = 0;
    }

    @Override
    public boolean crearProducto(Producto producto) {
        if (producto == null) return false;
        productos.add(producto);
        contador++;
        return true;
    }

    @Override
    public Producto leerProducto(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public boolean actualizarProducto(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto.getCodigo() == productoActualizado.getCodigo()) {
                productos.set(i, productoActualizado);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarProducto(int codigo) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto.getCodigo() == codigo) {
                productos.remove(i);
                contador--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean serializarProducto(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Producto deserializarProducto(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            List<Producto> productosDeserializados = (List<Producto>) ois.readObject();
            if (!productosDeserializados.isEmpty()) {
                // Reemplazar la lista actual con la deserializada
                this.productos = productosDeserializados;
                this.contador = productos.size();
                return productos.get(0); // Retornar el primer producto como ejemplo
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Métodos adicionales para la interfaz gráfica
    public int getContador() {
        return contador;
    }

    public boolean existeProducto(int codigo) {
        return leerProducto(codigo) != null;
    }

    public Producto obtenerProductoPorIndice(int indice) {
        if (indice >= 0 && indice < productos.size()) {
            return productos.get(indice);
        }
        return null;
    }

    public List<Producto> getTodosLosProductos() {
        return new ArrayList<>(productos);
    }
}