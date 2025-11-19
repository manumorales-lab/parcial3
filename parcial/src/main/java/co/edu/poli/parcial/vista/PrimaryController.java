package co.edu.poli.parcial.vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import co.edu.poli.parcial.model.*;
import co.edu.poli.parcial.servicios.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class PrimaryController {
    
    @FXML
    private Label lblTotalProductos;
    
    @FXML
    private Label lblUltimaAccion;
    
    @FXML
    private Label lblTitulo;
    
    @FXML
    private Button btnVolver;
    
    @FXML
    private StackPane contenedorPrincipal;
    
    @FXML
    private VBox vistaMenuPrincipal;
    
    @FXML
    private ScrollPane vistaContenido;
    
    @FXML
    private VBox contenidoPrincipal;
    
    private OperacionCRUD operacionCRUD;
    private Proveedor[] proveedores;
    
    public void initialize() {
        operacionCRUD = new ImplementacionCRUD();
        inicializarProveedores();
        actualizarInformacion();
        mostrarMenuPrincipal();
    }
    
    private void inicializarProveedores() {
        proveedores = new Proveedor[]{
            new Proveedor(1, "China", "TecnoGlobal"),
            new Proveedor(2, "Italia", "ModaStyle"),
            new Proveedor(3, "Japon", "TechWorld"),
            new Proveedor(4, "Colombia", "NataStyle")
        };
    }
    
    private void actualizarInformacion() {
        int total = operacionCRUD.getContador();
        lblTotalProductos.setText(String.valueOf(total));
    }
    
    private void mostrarMenuPrincipal() {
        vistaMenuPrincipal.setVisible(true);
        vistaContenido.setVisible(false);
        btnVolver.setVisible(false);
        btnVolver.setManaged(false);
        lblTitulo.setText("Sistema de Gestión de Productos");
        actualizarInformacion();
    }
    
    private void mostrarVistaContenido(String titulo) {
        vistaMenuPrincipal.setVisible(false);
        vistaContenido.setVisible(true);
        btnVolver.setVisible(true);
        btnVolver.setManaged(true);
        lblTitulo.setText(titulo);
        lblUltimaAccion.setText(titulo);
    }
    
    @FXML
    private void crearProducto() {
        mostrarVistaContenido("Crear Producto");
        contenidoPrincipal.getChildren().clear();
        contenidoPrincipal.getChildren().add(crearFormularioCrearProducto());
    }
    
    @FXML
    private void listarProductos() {
        mostrarVistaContenido("Listar Productos");
        contenidoPrincipal.getChildren().clear();
        contenidoPrincipal.getChildren().add(crearVistaListarProductos());
        actualizarInformacion();
    }
    
    @FXML
    private void actualizarProducto() {
        mostrarVistaContenido("Actualizar Producto");
        contenidoPrincipal.getChildren().clear();
        contenidoPrincipal.getChildren().add(crearFormularioBuscarParaActualizar());
    }
    
    @FXML
    private void eliminarProducto() {
        mostrarVistaContenido("Eliminar Producto");
        contenidoPrincipal.getChildren().clear();
        contenidoPrincipal.getChildren().add(crearFormularioEliminarProducto());
    }
    
    @FXML
    private void buscarProducto() {
        mostrarVistaContenido("Buscar Producto");
        contenidoPrincipal.getChildren().clear();
        contenidoPrincipal.getChildren().add(crearFormularioBuscarProducto());
    }
    
    @FXML
    private void serializar() {
        TextInputDialog dialog = new TextInputDialog("productos.dat");
        dialog.setTitle("Serializar Datos");
        dialog.setHeaderText("Guardar Productos en Archivo");
        dialog.setContentText("Nombre del archivo:");
        
        dialog.showAndWait().ifPresent(archivo -> {
            if (archivo.isEmpty()) {
                archivo = "productos.dat";
            }
            
            if (operacionCRUD.serializarProducto(archivo)) {
                lblUltimaAccion.setText("Serializar Datos - Éxito");
                mostrarAlerta("Éxito", 
                    "Datos serializados exitosamente en: " + archivo + 
                    "\nTotal de productos guardados: " + operacionCRUD.getContador(), 
                    Alert.AlertType.INFORMATION);
                actualizarInformacion();
            } else {
                mostrarAlerta("Error", "Error al serializar los datos", Alert.AlertType.ERROR);
            }
        });
    }
    
    @FXML
    private void deserializar() {
        TextInputDialog dialog = new TextInputDialog("productos.dat");
        dialog.setTitle("Deserializar Datos");
        dialog.setHeaderText("Cargar Productos desde Archivo");
        dialog.setContentText("Nombre del archivo:");
        
        dialog.showAndWait().ifPresent(archivo -> {
            if (archivo.isEmpty()) {
                archivo = "productos.dat";
            }
            
            // Verificar si el archivo existe
            File file = new File(archivo);
            if (!file.exists()) {
                mostrarAlerta("Error", "El archivo " + archivo + " no existe", Alert.AlertType.ERROR);
                return;
            }
            
            if (operacionCRUD.deserializarProducto(archivo)) {
                lblUltimaAccion.setText("Deserializar Datos - Éxito");
                actualizarInformacion();
                
                // Mostrar información de los productos cargados
                List<Producto> productosCargados = operacionCRUD.getTodosLosProductos();
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Datos deserializados exitosamente desde: ").append(archivo).append("\n");
                mensaje.append("Total de productos cargados: ").append(productosCargados.size()).append("\n\n");
                
                if (!productosCargados.isEmpty()) {
                    mensaje.append("Productos cargados:\n");
                    for (int i = 0; i < Math.min(productosCargados.size(), 5); i++) {
                        Producto p = productosCargados.get(i);
                        mensaje.append("- ").append(p.getNombre()).append(" (Código: ").append(p.getCodigo()).append(")\n");
                    }
                    if (productosCargados.size() > 5) {
                        mensaje.append("... y ").append(productosCargados.size() - 5).append(" más");
                    }
                }
                
                mostrarAlerta("Éxito", mensaje.toString(), Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Error al deserializar los datos", Alert.AlertType.ERROR);
            }
        });
    }
    
    @FXML
    private void salir() {
        System.exit(0);
    }
    
    @FXML
    private void volverAlMenuPrincipal() {
        mostrarMenuPrincipal();
    }
    
    // ========== COMPONENTES DE LA INTERFAZ ==========
    
    private VBox crearFormularioCrearProducto() {
        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        formulario.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1;");
        
        Label titulo = new Label("Crear Nuevo Producto");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));
        
        // Campos del formulario
        Label lblCodigo = new Label("Código:");
        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Ingrese código numérico...");
        
        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ingrese nombre del producto...");
        
        Label lblPrecio = new Label("Precio:");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Ingrese precio...");
        
        Label lblStock = new Label("Stock:");
        TextField txtStock = new TextField();
        txtStock.setPromptText("Ingrese cantidad en stock...");
        
        Label lblProveedor = new Label("Proveedor:");
        ComboBox<Proveedor> cmbProveedor = new ComboBox<>();
        cmbProveedor.setItems(FXCollections.observableArrayList(proveedores));
        cmbProveedor.setConverter(new javafx.util.StringConverter<Proveedor>() {
            @Override
            public String toString(Proveedor proveedor) {
                return proveedor != null ? proveedor.getNombre() + " (" + proveedor.getPais() + ")" : "";
            }
            
            @Override
            public Proveedor fromString(String string) {
                return null;
            }
        });
        cmbProveedor.setPromptText("Seleccione un proveedor...");
        
        Label lblTipo = new Label("Tipo de Producto:");
        ToggleGroup tipoGroup = new ToggleGroup();
        RadioButton rbElectronico = new RadioButton("Electrónico");
        RadioButton rbRopa = new RadioButton("Ropa");
        rbElectronico.setToggleGroup(tipoGroup);
        rbRopa.setToggleGroup(tipoGroup);
        HBox tipoBox = new HBox(10, rbElectronico, rbRopa);
        
        Label lblGarantia = new Label("Garantía (meses):");
        TextField txtGarantia = new TextField();
        txtGarantia.setPromptText("Ingrese meses de garantía...");
        lblGarantia.setVisible(false);
        txtGarantia.setVisible(false);
        
        Label lblTalla = new Label("Talla:");
        ComboBox<String> cmbTalla = new ComboBox<>();
        cmbTalla.setItems(FXCollections.observableArrayList("S", "M", "L", "XL"));
        cmbTalla.setPromptText("Seleccione talla...");
        lblTalla.setVisible(false);
        cmbTalla.setVisible(false);
        
        // Mostrar/ocultar campos según el tipo
        rbElectronico.selectedProperty().addListener((obs, oldVal, newVal) -> {
            lblGarantia.setVisible(newVal);
            txtGarantia.setVisible(newVal);
            lblTalla.setVisible(false);
            cmbTalla.setVisible(false);
        });
        
        rbRopa.selectedProperty().addListener((obs, oldVal, newVal) -> {
            lblTalla.setVisible(newVal);
            cmbTalla.setVisible(newVal);
            lblGarantia.setVisible(false);
            txtGarantia.setVisible(false);
        });
        
        // Botones
        Button btnCrear = new Button("Crear Producto");
        btnCrear.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");
        
        HBox botones = new HBox(10, btnCrear, btnLimpiar);
        
        // Agregar elementos al grid
        grid.add(lblCodigo, 0, 0);
        grid.add(txtCodigo, 1, 0);
        grid.add(lblNombre, 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(lblPrecio, 0, 2);
        grid.add(txtPrecio, 1, 2);
        grid.add(lblStock, 0, 3);
        grid.add(txtStock, 1, 3);
        grid.add(lblProveedor, 0, 4);
        grid.add(cmbProveedor, 1, 4);
        grid.add(lblTipo, 0, 5);
        grid.add(tipoBox, 1, 5);
        grid.add(lblGarantia, 0, 6);
        grid.add(txtGarantia, 1, 6);
        grid.add(lblTalla, 0, 7);
        grid.add(cmbTalla, 1, 7);
        
        // Acción del botón crear
        btnCrear.setOnAction(e -> {
            try {
                // Validaciones
                if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || 
                    txtPrecio.getText().isEmpty() || txtStock.getText().isEmpty() ||
                    cmbProveedor.getValue() == null || tipoGroup.getSelectedToggle() == null) {
                    mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                    return;
                }
                
                int codigo = Integer.parseInt(txtCodigo.getText());
                String nombre = txtNombre.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());
                Proveedor proveedor = cmbProveedor.getValue();
                
                // Verificar si ya existe el producto
                if (operacionCRUD.leerProducto(codigo) != null) {
                    mostrarAlerta("Error", "Ya existe un producto con ese código", Alert.AlertType.ERROR);
                    return;
                }
                
                Producto nuevoProducto = new Producto(codigo, nombre, precio, stock, proveedor);
                
                if (rbElectronico.isSelected()) {
                    if (txtGarantia.getText().isEmpty()) {
                        mostrarAlerta("Error", "La garantía es obligatoria para productos electrónicos", Alert.AlertType.ERROR);
                        return;
                    }
                    int garantia = Integer.parseInt(txtGarantia.getText());
                    Electronico electronico = new Electronico(codigo, nombre, precio, proveedor, garantia);
                    nuevoProducto.setElectronico(electronico);
                } else if (rbRopa.isSelected()) {
                    if (cmbTalla.getValue() == null) {
                        mostrarAlerta("Error", "La talla es obligatoria para productos de ropa", Alert.AlertType.ERROR);
                        return;
                    }
                    Ropa ropa = new Ropa(codigo, nombre, precio, stock, proveedor, cmbTalla.getValue());
                    nuevoProducto.setRopa(ropa);
                }
                
                if (operacionCRUD.crearProducto(nuevoProducto)) {
                    mostrarAlerta("Éxito", "Producto creado exitosamente", Alert.AlertType.INFORMATION);
                    limpiarFormulario(txtCodigo, txtNombre, txtPrecio, txtStock, txtGarantia, cmbProveedor, cmbTalla, tipoGroup);
                    actualizarInformacion();
                } else {
                    mostrarAlerta("Error", "Error al crear el producto", Alert.AlertType.ERROR);
                }
                
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor ingrese valores numéricos válidos", Alert.AlertType.ERROR);
            }
        });
        
        btnLimpiar.setOnAction(e -> limpiarFormulario(txtCodigo, txtNombre, txtPrecio, txtStock, txtGarantia, cmbProveedor, cmbTalla, tipoGroup));
        
        formulario.getChildren().addAll(titulo, grid, botones);
        return formulario;
    }
    
    private VBox crearVistaListarProductos() {
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));
        
        Label titulo = new Label("Lista de Productos");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        int totalProductos = operacionCRUD.getContador();
        
        Label lblTotal = new Label("Total de productos: " + totalProductos);
        lblTotal.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60; -fx-font-size: 16px;");
        
        VBox listaProductos = new VBox(10);
        
        if (totalProductos == 0) {
            Label lblVacio = new Label("No hay productos registrados");
            lblVacio.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic; -fx-font-size: 14px;");
            listaProductos.getChildren().add(lblVacio);
        } else {
            List<Producto> todosProductos = operacionCRUD.getTodosLosProductos();
            for (int i = 0; i < todosProductos.size(); i++) {
                Producto producto = todosProductos.get(i);
                if (producto != null) {
                    VBox card = crearCardProducto(producto, i + 1);
                    listaProductos.getChildren().add(card);
                }
            }
        }
        
        contenedor.getChildren().addAll(titulo, lblTotal, listaProductos);
        return contenedor;
    }
    
    private VBox crearCardProducto(Producto producto, int numero) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-border-radius: 5;");
        
        Label lblNumero = new Label("Producto #" + numero);
        lblNumero.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 16px;");
        
        Label lblCodigo = new Label("Código: " + producto.getCodigo());
        Label lblNombre = new Label("Nombre: " + producto.getNombre());
        Label lblPrecio = new Label("Precio: $" + String.format("%.2f", producto.getPrecio()));
        Label lblStock = new Label("Stock: " + producto.getStock());
        Label lblProveedor = new Label("Proveedor: " + producto.getProveedor().getNombre());
        Label lblTipo = new Label("Tipo: " + producto.getTipo());
        
        VBox detallesEspecificos = new VBox(5);
        if (producto.getElectronico() != null) {
            Label lblGarantia = new Label("Garantía: " + producto.getElectronico().getGarantiaMeses() + " meses");
            lblGarantia.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
            detallesEspecificos.getChildren().add(lblGarantia);
        }
        if (producto.getRopa() != null) {
            Label lblTalla = new Label("Talla: " + producto.getRopa().getTalla());
            lblTalla.setStyle("-fx-text-fill: #9b59b6; -fx-font-weight: bold;");
            detallesEspecificos.getChildren().add(lblTalla);
        }
        
        card.getChildren().addAll(lblNumero, lblCodigo, lblNombre, lblPrecio, lblStock, lblProveedor, lblTipo, detallesEspecificos);
        return card;
    }
    
    private VBox crearFormularioBuscarProducto() {
        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        
        Label titulo = new Label("Buscar Producto");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        HBox busquedaBox = new HBox(10);
        Label lblCodigo = new Label("Código del producto:");
        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Ingrese el código...");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        
        busquedaBox.getChildren().addAll(lblCodigo, txtCodigo, btnBuscar);
        
        VBox resultado = new VBox(10);
        resultado.setPadding(new Insets(15));
        resultado.setVisible(false);
        
        btnBuscar.setOnAction(e -> {
            try {
                if (txtCodigo.getText().isEmpty()) {
                    mostrarAlerta("Error", "Por favor ingrese un código", Alert.AlertType.ERROR);
                    return;
                }
                
                int codigo = Integer.parseInt(txtCodigo.getText());
                Producto producto = operacionCRUD.leerProducto(codigo);
                
                resultado.getChildren().clear();
                resultado.setVisible(true);
                
                if (producto != null) {
                    resultado.setStyle("-fx-background-color: #d4edda; -fx-border-color: #c3e6cb; -fx-border-width: 2; -fx-border-radius: 5;");
                    
                    Label lblEncontrado = new Label("✅ Producto encontrado:");
                    lblEncontrado.setStyle("-fx-font-weight: bold; -fx-text-fill: #155724;");
                    
                    VBox card = crearCardProducto(producto, 1);
                    
                    resultado.getChildren().addAll(lblEncontrado, card);
                } else {
                    resultado.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #f5c6cb; -fx-border-width: 2; -fx-border-radius: 5;");
                    Label lblError = new Label("❌ Producto no encontrado");
                    lblError.setStyle("-fx-text-fill: #721c24; -fx-font-weight: bold;");
                    
                    Label lblSugerencia = new Label("El producto con código " + codigo + " no existe en la base de datos.");
                    lblSugerencia.setStyle("-fx-text-fill: #721c24;");
                    
                    resultado.getChildren().addAll(lblError, lblSugerencia);
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor ingrese un código válido (número entero)", Alert.AlertType.ERROR);
            }
        });
        
        // Enter key support
        txtCodigo.setOnAction(e -> btnBuscar.fire());
        
        formulario.getChildren().addAll(titulo, busquedaBox, resultado);
        return formulario;
    }
    
    private VBox crearFormularioBuscarParaActualizar() {
        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        
        Label titulo = new Label("Actualizar Producto");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        HBox busquedaBox = new HBox(10);
        Label lblCodigo = new Label("Código del producto:");
        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Ingrese el código...");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #ffc107; -fx-text-fill: white;");
        
        busquedaBox.getChildren().addAll(lblCodigo, txtCodigo, btnBuscar);
        
        VBox formularioActualizacion = new VBox(15);
        formularioActualizacion.setVisible(false);
        
        btnBuscar.setOnAction(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                Producto producto = operacionCRUD.leerProducto(codigo);
                
                if (producto != null) {
                    formularioActualizacion.getChildren().clear();
                    formularioActualizacion.getChildren().add(crearFormularioActualizacion(producto));
                    formularioActualizacion.setVisible(true);
                } else {
                    mostrarAlerta("Error", "Producto no encontrado", Alert.AlertType.ERROR);
                    formularioActualizacion.setVisible(false);
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor ingrese un código válido", Alert.AlertType.ERROR);
            }
        });
        
        formulario.getChildren().addAll(titulo, busquedaBox, formularioActualizacion);
        return formulario;
    }
    
    private VBox crearFormularioActualizacion(Producto producto) {
        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        formulario.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffeaa7; -fx-border-width: 1;");
        
        Label titulo = new Label("Actualizando: " + producto.getNombre());
        titulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #856404;");
        
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));
        
        Label lblNombre = new Label("Nuevo nombre:");
        TextField txtNombre = new TextField(producto.getNombre());
        
        Label lblPrecio = new Label("Nuevo precio:");
        TextField txtPrecio = new TextField(String.valueOf(producto.getPrecio()));
        
        Label lblStock = new Label("Nuevo stock:");
        TextField txtStock = new TextField(String.valueOf(producto.getStock()));
        
        Button btnActualizar = new Button("Actualizar Producto");
        btnActualizar.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold;");
        
        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblPrecio, 0, 1);
        grid.add(txtPrecio, 1, 1);
        grid.add(lblStock, 0, 2);
        grid.add(txtStock, 1, 2);
        grid.add(btnActualizar, 1, 3);
        
        btnActualizar.setOnAction(e -> {
            try {
                String nombre = txtNombre.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());
                
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setStock(stock);
                
                if (operacionCRUD.actualizarProducto(producto)) {
                    mostrarAlerta("Éxito", "Producto actualizado exitosamente", Alert.AlertType.INFORMATION);
                    volverAlMenuPrincipal();
                } else {
                    mostrarAlerta("Error", "Error al actualizar el producto", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor ingrese valores numéricos válidos", Alert.AlertType.ERROR);
            }
        });
        
        formulario.getChildren().addAll(titulo, grid);
        return formulario;
    }
    
    private VBox crearFormularioEliminarProducto() {
        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        
        Label titulo = new Label("Eliminar Producto");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        HBox busquedaBox = new HBox(10);
        Label lblCodigo = new Label("Código del producto:");
        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Ingrese el código...");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        
        busquedaBox.getChildren().addAll(lblCodigo, txtCodigo, btnBuscar);
        
        VBox confirmacion = new VBox(15);
        confirmacion.setVisible(false);
        
        btnBuscar.setOnAction(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                Producto producto = operacionCRUD.leerProducto(codigo);
                
                if (producto != null) {
                    confirmacion.getChildren().clear();
                    confirmacion.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #f5c6cb; -fx-border-width: 1; -fx-padding: 15;");
                    
                    Label lblProducto = new Label("Producto a eliminar:");
                    lblProducto.setStyle("-fx-font-weight: bold;");
                    
                    VBox card = crearCardProducto(producto, 1);
                    
                    Label lblConfirmacion = new Label("¿Está seguro de que desea eliminar este producto?");
                    lblConfirmacion.setStyle("-fx-text-fill: #721c24;");
                    
                    HBox botonesConfirmacion = new HBox(10);
                    Button btnConfirmar = new Button("Confirmar Eliminación");
                    btnConfirmar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
                    
                    Button btnCancelar = new Button("Cancelar");
                    btnCancelar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");
                    
                    botonesConfirmacion.getChildren().addAll(btnConfirmar, btnCancelar);
                    
                    btnConfirmar.setOnAction(ev -> {
                        if (operacionCRUD.eliminarProducto(codigo)) {
                            mostrarAlerta("Éxito", "Producto eliminado exitosamente", Alert.AlertType.INFORMATION);
                            actualizarInformacion();
                            volverAlMenuPrincipal();
                        } else {
                            mostrarAlerta("Error", "Error al eliminar el producto", Alert.AlertType.ERROR);
                        }
                    });
                    
                    btnCancelar.setOnAction(ev -> {
                        confirmacion.setVisible(false);
                        txtCodigo.clear();
                    });
                    
                    confirmacion.getChildren().addAll(lblProducto, card, lblConfirmacion, botonesConfirmacion);
                    confirmacion.setVisible(true);
                } else {
                    mostrarAlerta("Error", "Producto no encontrado", Alert.AlertType.ERROR);
                    confirmacion.setVisible(false);
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor ingrese un código válido", Alert.AlertType.ERROR);
            }
        });
        
        formulario.getChildren().addAll(titulo, busquedaBox, confirmacion);
        return formulario;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    private void limpiarFormulario(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
    }
    
    private void limpiarFormulario(TextField txtCodigo, TextField txtNombre, TextField txtPrecio, 
                                 TextField txtStock, TextField txtGarantia, ComboBox<Proveedor> cmbProveedor, 
                                 ComboBox<String> cmbTalla, ToggleGroup tipoGroup) {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtGarantia.clear();
        cmbProveedor.getSelectionModel().clearSelection();
        cmbTalla.getSelectionModel().clearSelection();
        tipoGroup.selectToggle(null);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}