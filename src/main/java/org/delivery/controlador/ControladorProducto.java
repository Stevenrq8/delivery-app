package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.ProductoDAO;
import org.delivery.vista.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

@Getter
@Setter
public class ControladorProducto implements ActionListener {

    private Restaurante restaurante;
    private Producto producto;
    private ColaProducto colaProducto;
    private VistaPrincipal vistaPrincipal;
    private VistaRegistroProducto vistaRegistroProducto;
    private VistaRegistroRestaurante vistaRegistroRestaurante;
    private VistaPrincipalRestaurante vistaPrincipalRestaurante;
    private VistaTablaProducto vistaTablaProducto;
    private ControladorInicioSesion controladorInicioSesion;
    private ProductoDAO productoDAO;

    public ControladorProducto(Restaurante restaurante, Producto producto, ColaProducto colaProducto, VistaPrincipal vistaPrincipal,
                               VistaRegistroProducto vistaRegistroProducto, VistaRegistroRestaurante vistaRegistroRestaurante,
                               VistaPrincipalRestaurante vistaPrincipalRestaurante, VistaTablaProducto vistaTablaProducto,
                               ControladorInicioSesion controladorInicioSesion, ProductoDAO productoDAO) {
        this.restaurante = restaurante;
        this.producto = producto;
        this.colaProducto = colaProducto;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaRegistroProducto = vistaRegistroProducto;
        this.vistaRegistroRestaurante = vistaRegistroRestaurante;
        this.vistaPrincipalRestaurante = vistaPrincipalRestaurante;
        this.vistaTablaProducto = vistaTablaProducto;
        this.controladorInicioSesion = controladorInicioSesion;
        this.productoDAO = productoDAO;
        this.vistaRegistroProducto.getJbAceptar().addActionListener(this);
        this.vistaRegistroProducto.getJbCancelar().addActionListener(this);
        this.vistaPrincipalRestaurante.getJbAgregarProducto().addActionListener(this);
        this.productoDAO.listar(getColaProducto(), getVistaTablaProducto());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long id = 0;
        String nombre = "", categoria = "", descripcion = "";
        double precio = 0.0;
        int cantidad = 0;
        boolean v = false;

        if (e.getSource() == getVistaRegistroProducto().getJbCancelar()) {
            getVistaRegistroProducto().setVisible(false);
        } else if (e.getSource() == getVistaRegistroProducto().getJbAceptar()) {
            if (getControladorInicioSesion().getRestaurante() != null) {
                setRestaurante(getControladorInicioSesion().getRestaurante());
                if (getVistaRegistroProducto().getJtfIdProducto().getText().isEmpty() ||
                        getVistaRegistroProducto().getJtfNombre().getText().isEmpty() ||
                        getVistaRegistroProducto().getJtfPrecio().getText().isEmpty() ||
                        getVistaRegistroProducto().getJtfCantidad().getText().isEmpty() ||
                        Objects.requireNonNull(getVistaRegistroProducto().getJcbCategoria().getSelectedItem()).toString().isEmpty() ||
                        getVistaRegistroProducto().getJtaDescripcion().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
                } else {
                    id = Long.parseLong(getVistaRegistroProducto().getJtfIdProducto().getText());
                    nombre = getVistaRegistroProducto().getJtfNombre().getText();
                    precio = Double.parseDouble(getVistaRegistroProducto().getJtfPrecio().getText());
                    cantidad = Integer.parseInt(getVistaRegistroProducto().getJtfCantidad().getText());
                    categoria = Objects.requireNonNull(getVistaRegistroProducto().getJcbCategoria().getSelectedItem()).toString();
                    if (categoria.equals("Categoría del Producto")) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una categoría",
                                "Información", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    descripcion = getVistaRegistroProducto().getJtaDescripcion().getText();
                    v = true;
                }
                if (v) {
                    if (getProductoDAO().buscar(id)) {
                        JOptionPane.showMessageDialog(null, "El id del producto ya existe");
                        getVistaRegistroProducto().getJtfIdProducto().requestFocus();
                    } else if (getColaProducto().buscar(nombre, precio)) {
                        cantidad = Integer.parseInt(getVistaRegistroProducto().getJtfCantidad().getText());
                        getColaProducto().actualizarCantidad(nombre, cantidad);
                        getColaProducto().agregarProductoTabla(getVistaTablaProducto().getJtProducto());
                        JOptionPane.showMessageDialog(null, "Cómo el producto ya existe, se actualizó la cantidad");
                        getVistaRegistroProducto().dispose();
                    } else {
                        Producto producto = new Producto(id, getRestaurante(), nombre, descripcion, precio, cantidad, categoria);
                        if (getProductoDAO().registrar(producto)) {
                            getColaProducto().agregar(producto);
                            getColaProducto().agregarProductoTabla(getVistaTablaProducto().getJtProducto());
                            JOptionPane.showMessageDialog(null, "Producto registrado");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar el producto");
                        }
                        getVistaRegistroProducto().getJtfIdProducto().setText("");
                        getVistaRegistroProducto().getJtfNombre().setText("");
                        getVistaRegistroProducto().getJtfPrecio().setText("");
                        getVistaRegistroProducto().getJtfCantidad().setText("");
                        getVistaRegistroProducto().getJcbCategoria().setSelectedItem("Categoría del Producto");
                        getVistaRegistroProducto().getJtaDescripcion().setText("");
                        getVistaRegistroProducto().dispose();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe iniciar sesión");
            }
        }
    }
}

