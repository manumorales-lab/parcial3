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
        System.out.println("Producto creado: " + producto.getNombre() + " (Código: " + producto.getCodigo() + ")");
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
                System.out.println("Producto actualizado: " + productoActualizado.getNombre() + " (Código: " + productoActualizado.getCodigo() + ")");
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
                System.out.println("Producto eliminado: Código " + codigo);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean serializarProducto(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
            System.out.println(" Productos serializados correctamente en: " + archivo);
            System.out.println(" Total de productos guardados: " + productos.size());
            
            // Log detallado de productos guardados
            if (!productos.isEmpty()) {
                System.out.println(" Productos guardados:");
                for (Producto producto : productos) {
                    System.out.println("   - " + producto.getNombre() + " (Código: " + producto.getCodigo() + ", Tipo: " + producto.getTipo() + ")");
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println(" Error al serializar: " + e.getMessage());
            e.printStackTrace();
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
                
                System.out.println(" Productos deserializados correctamente desde: " + archivo);
                System.out.println(" Total de productos cargados: " + productos.size());
                
                // Log detallado de productos cargados
                if (!productos.isEmpty()) {
                    System.out.println(" Productos cargados:");
                    for (Producto producto : productos) {
                        System.out.println("   - " + producto.getNombre() + " (Código: " + producto.getCodigo() + ", Tipo: " + producto.getTipo() + ")");
                    }
                } else {
                    System.out.println("  No hay productos en el archivo");
                }
                return true;
            }
        } catch (FileNotFoundException e) {
            System.err.println(" Archivo no encontrado: " + archivo);
            return false;
        } catch (IOException e) {
            System.err.println(" Error de E/S al deserializar: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println(" Error de clase no encontrada: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println(" Error inesperado al deserializar: " + e.getMessage());
            e.printStackTrace();
            return false;
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
        File file = new File(archivo);
        boolean existe = file.exists();
        System.out.println(" Verificando archivo: " + archivo + " - Existe: " + existe);
        if (existe) {
            System.out.println(" Tamaño del archivo: " + file.length() + " bytes");
        }
        return existe;
    }

    // Método para obtener información del estado actual
    public String getEstadoActual() {
        StringBuilder estado = new StringBuilder();
        estado.append("Estado actual del CRUD:\n");
        estado.append("Total productos: ").append(contador).append("\n");
        estado.append("Productos en memoria: ").append(productos.size()).append("\n");
        
        if (!productos.isEmpty()) {
            estado.append("Lista de productos:\n");
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                estado.append("  ").append(i + 1).append(". ")
                      .append(p.getNombre()).append(" (Código: ").append(p.getCodigo())
                      .append(", Tipo: ").append(p.getTipo()).append(")\n");
            }
        } else {
            estado.append("No hay productos registrados\n");
        }
        
        return estado.toString();
    }

    // Método para limpiar todos los productos (útil para testing)
    public void limpiarTodosLosProductos() {
        productos.clear();
        contador = 0;
        System.out.println("🧹 Todos los productos han sido eliminados");
    }

    // Método para buscar productos por nombre (búsqueda parcial)
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        String nombreBusqueda = nombre.toLowerCase();
        
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombreBusqueda)) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    // Método para obtener productos por tipo
    public List<Producto> getProductosPorTipo(String tipo) {
        List<Producto> resultados = new ArrayList<>();
        
        for (Producto producto : productos) {
            if (producto.getTipo().equalsIgnoreCase(tipo)) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    // Método para verificar la integridad de los datos
    public boolean verificarIntegridadDatos() {
        System.out.println(" Verificando integridad de datos...");
        System.out.println("   - Contador: " + contador);
        System.out.println("   - Tamaño lista: " + productos.size());
        System.out.println("   - Coinciden: " + (contador == productos.size()));
        
        // Verificar que no haya códigos duplicados
        for (int i = 0; i < productos.size(); i++) {
            for (int j = i + 1; j < productos.size(); j++) {
                if (productos.get(i).getCodigo() == productos.get(j).getCodigo()) {
                    System.err.println("❌ Código duplicado encontrado: " + productos.get(i).getCodigo());
                    return false;
                }
            }
        }
        
        System.out.println("✅ Integridad de datos verificada correctamente");
        return true;
    }
}