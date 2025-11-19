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
        if (producto == null || existeProducto(producto.getCodigo())) {
            return false;
        }
        productos.add(producto);
        contador++;
        return true;
    }

    @Override
    public Producto leerProducto(int codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean actualizarProducto(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == productoActualizado.getCodigo()) {
                productos.set(i, productoActualizado);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarProducto(int codigo) {
        boolean removed = productos.removeIf(p -> p.getCodigo() == codigo);
        if (removed) {
            contador--;
        }
        return removed;
    }

    @Override
    public boolean serializarProducto(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
            System.out.println("Productos serializados correctamente en: " + archivo);
            System.out.println("Total de productos guardados: " + productos.size());
            return true;
        } catch (IOException e) {
            System.err.println("Error al serializar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deserializarProducto(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            @SuppressWarnings("unchecked")
            List<Producto> productosDeserializados = (List<Producto>) ois.readObject();
            
            if (productosDeserializados != null) {
                // Limpiar la lista actual y agregar los productos deserializados
                this.productos.clear();
                this.productos.addAll(productosDeserializados);
                this.contador = productos.size();
                
                System.out.println("Productos deserializados correctamente desde: " + archivo);
                System.out.println("Total de productos cargados: " + productos.size());
                
                // Mostrar información de los productos cargados
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    System.out.println("Producto " + (i + 1) + ": " + p.getNombre() + " (Código: " + p.getCodigo() + ")");
                }
                
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Producto> getTodosLosProductos() {
        return new ArrayList<>(productos);
    }

    @Override
    public int getContador() {
        return contador;
    }

    // Métodos adicionales para funcionalidad interna
    public boolean existeProducto(int codigo) {
        return leerProducto(codigo) != null;
    }

    public Producto obtenerProductoPorIndice(int indice) {
        if (indice >= 0 && indice < productos.size()) {
            return productos.get(indice);
        }
        return null;
    }

    // Método para verificar si un archivo existe
    public boolean archivoExiste(String archivo) {
        return new File(archivo).exists();
    }
}