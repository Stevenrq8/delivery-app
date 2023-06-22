package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.AdministradorPedidoDAO;
import org.delivery.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

@Getter
@Setter
public class ControladorAdministradorPedido implements ActionListener {

    private Repartidor repartidor;
    private ColaAdministrador colaAdministrador;
    private ColaAdministradorPedido colaAdministradorPedido;
    private ColaCliente colaCliente;
    private ColaRepartidor colaRepartidor;
    private ColaRestaurante colaRestaurante;
    private ColaPedido colaPedido;
    private ColaPedidoAsignado colaPedidoAsignado;
    private VistaPrincipal vistaPrincipal;
    private VistaPrincipalAdministradorPedido vistaPrincipalAdministradorPedido;
    private VistaPrincipalRepartidor vistaPrincipalRepartidor;
    private VistaRegistroAdministradorPedido vistaRegistroAdministradorPedido;
    private VistaTablaAdministradorPedido vistaTablaAdministradorPedido;
    private VistaTablaPedido vistaTablaPedido;
    private ControladorInicioSesion controladorInicioSesion;
    private AdministradorPedidoDAO administradorPedidoDAO;

    public ControladorAdministradorPedido(Repartidor repartidor, ColaAdministrador colaAdministrador,
                                          ColaAdministradorPedido colaAdministradorPedido, ColaCliente colaCliente,
                                          ColaRepartidor colaRepartidor, ColaRestaurante colaRestaurante, ColaPedido colaPedido,
                                          ColaPedidoAsignado colaPedidoAsignado, VistaPrincipal vistaPrincipal,
                                          VistaPrincipalAdministradorPedido vistaPrincipalAdministradorPedido,
                                          VistaPrincipalRepartidor vistaPrincipalRepartidor,
                                          VistaRegistroAdministradorPedido vistaRegistroAdministradorPedido,
                                          VistaTablaAdministradorPedido vistaTablaAdministradorPedido,
                                          VistaTablaPedido vistaTablaPedido, ControladorInicioSesion controladorInicioSesion,
                                          AdministradorPedidoDAO administradorPedidoDAO) {
        this.repartidor = repartidor;
        this.colaAdministrador = colaAdministrador;
        this.colaAdministradorPedido = colaAdministradorPedido;
        this.colaCliente = colaCliente;
        this.colaRepartidor = colaRepartidor;
        this.colaRestaurante = colaRestaurante;
        this.colaPedido = colaPedido;
        this.colaPedidoAsignado = colaPedidoAsignado;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipalAdministradorPedido = vistaPrincipalAdministradorPedido;
        this.vistaPrincipalRepartidor = vistaPrincipalRepartidor;
        this.vistaRegistroAdministradorPedido = vistaRegistroAdministradorPedido;
        this.vistaTablaAdministradorPedido = vistaTablaAdministradorPedido;
        this.vistaTablaPedido = vistaTablaPedido;
        this.controladorInicioSesion = controladorInicioSesion;
        this.administradorPedidoDAO = administradorPedidoDAO;
        this.vistaPrincipal.getJmiRegistrarAdministradorPedido().addActionListener(this);
        this.vistaRegistroAdministradorPedido.getJbCancelar().addActionListener(this);
        this.vistaRegistroAdministradorPedido.getJbAceptar().addActionListener(this);
        this.vistaPrincipalAdministradorPedido.getJbVerPedidos().addActionListener(this);
        this.vistaTablaPedido.getJbAsignarPedido().addActionListener(this);
        this.administradorPedidoDAO.listar(getColaAdministradorPedido(), getVistaTablaAdministradorPedido());
    }

    /**
     * Método para buscar un usuario en las colas de usuarios
     * (administrador, administrador de pedido, cliente y repartidor)
     *
     * @param cedula     cédula del usuario a buscar
     * @param telefono   teléfono del usuario a buscar
     * @param correo     correo del usuario a buscar
     * @param contrasena contraseña del usuario a buscar
     * @return true si el usuario se encuentra en alguna cola, false si no se encuentra en ninguna cola
     */
    public boolean buscarUsuarioCola(long cedula, String telefono, String correo, String contrasena) {
        return getColaAdministrador().buscar(cedula, telefono, correo, contrasena)
                || getColaAdministradorPedido().buscar(cedula, telefono, correo, contrasena)
                || getColaCliente().buscar(cedula, telefono, correo, contrasena)
                || getColaRepartidor().buscar(cedula, telefono, correo, contrasena);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long cedula = 0;
        String nombre = "", apellido = "", direccion = "", telefono = "", correo = "", contrasena, contrasenaEncriptada = "";
        boolean v = false;

        if (e.getSource() == getVistaPrincipal().getJmiRegistrarAdministradorPedido()) {
            getVistaRegistroAdministradorPedido().setVisible(true);
        }
        if (e.getSource() == getVistaRegistroAdministradorPedido().getJbCancelar()) {
            getVistaRegistroAdministradorPedido().dispose();
        } else if (e.getSource() == getVistaRegistroAdministradorPedido().getJbAceptar()) {
            if (getVistaRegistroAdministradorPedido().getJtfCedula().getText().isEmpty() ||
                    getVistaRegistroAdministradorPedido().getJtfNombre().getText().isEmpty() ||
                    getVistaRegistroAdministradorPedido().getJtfApellido().getText().isEmpty() ||
                    getVistaRegistroAdministradorPedido().getJtfDireccion().getText().isEmpty() ||
                    getVistaRegistroAdministradorPedido().getJtfTelefono().getText().isEmpty() ||
                    getVistaRegistroAdministradorPedido().getJtfCorreo().getText().isEmpty() ||
                    getVistaRegistroAdministradorPedido().getJpfContrasena().getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            } else {
                cedula = Long.parseLong(getVistaRegistroAdministradorPedido().getJtfCedula().getText());
                nombre = getVistaRegistroAdministradorPedido().getJtfNombre().getText();
                apellido = getVistaRegistroAdministradorPedido().getJtfApellido().getText();
                direccion = getVistaRegistroAdministradorPedido().getJtfDireccion().getText();
                telefono = getVistaRegistroAdministradorPedido().getJtfTelefono().getText();
                correo = getVistaRegistroAdministradorPedido().getJtfCorreo().getText();
                if (getAdministradorPedidoDAO().validarCorreo(correo)) {
                    correo = getVistaRegistroAdministradorPedido().getJtfCorreo().getText();
                } else {
                    JOptionPane.showMessageDialog(null, "El correo no es válido");
                    getVistaRegistroAdministradorPedido().getJtfCorreo().requestFocus();
                    return;
                }
                contrasena = new String(getVistaRegistroAdministradorPedido().getJpfContrasena().getPassword());
                contrasenaEncriptada = Hash.sha1(contrasena);
                v = true;
            }
            if (v) {
                if (getAdministradorPedidoDAO().buscar(cedula, telefono, correo, contrasenaEncriptada) ||
                        buscarUsuarioCola(cedula, telefono, correo, contrasenaEncriptada)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con estos datos",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    getVistaRegistroAdministradorPedido().getJtfCedula().requestFocus();
                } else {
                    AdministradorPedido administradorPedido = new AdministradorPedido(cedula, nombre, apellido, direccion,
                            telefono, correo, contrasenaEncriptada);
                    if (getAdministradorPedidoDAO().registrar(administradorPedido)) {
                        getColaAdministradorPedido().agregar(administradorPedido);
                        getColaAdministradorPedido().agregarAdministradorPedidoTabla(getVistaTablaAdministradorPedido().getJtAdministradorPedido());
                        JOptionPane.showMessageDialog(null, "Se ha registrado exitosamente");
                        getVistaRegistroAdministradorPedido().dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrarse");
                    }
                    getVistaRegistroAdministradorPedido().getJtfCedula().setText("");
                    getVistaRegistroAdministradorPedido().getJtfNombre().setText("");
                    getVistaRegistroAdministradorPedido().getJtfApellido().setText("");
                    getVistaRegistroAdministradorPedido().getJtfDireccion().setText("");
                    getVistaRegistroAdministradorPedido().getJtfTelefono().setText("");
                    getVistaRegistroAdministradorPedido().getJtfCorreo().setText("");
                    getVistaRegistroAdministradorPedido().getJpfContrasena().setText("");
                    getVistaRegistroAdministradorPedido().dispose();
                }
            }
        }
        if (e.getSource() == getVistaPrincipalAdministradorPedido().getJbVerPedidos()) {
            getVistaTablaPedido().setVisible(true);
        }
        if (e.getSource() == getVistaTablaPedido().getJbAsignarPedido()) {
            if (getControladorInicioSesion().getRepartidor() != null) { // si el repartidor inició sesión
                setRepartidor(getControladorInicioSesion().getRepartidor());
                if (!getColaPedido().colaVacia() && getVistaTablaPedido().getJtPedido().getRowCount() > 0) {
                    int fila = 0;
                    if (fila < getVistaTablaPedido().getJtPedido().getRowCount()) {
                        // int numeroPedido = Integer.parseInt(getVistaTablaPedido().getJtPedido().getValueAt(fila, 0).toString());
                        int numeroPedido = (int) (Math.random() * 1000);
                        String fechaPedido = getVistaTablaPedido().getJtPedido().getValueAt(fila, 1).toString();
                        Cliente clientePedido = (Cliente) getVistaTablaPedido().getJtPedido().getValueAt(fila, 2);
                        String direccionPedido = getVistaTablaPedido().getJtPedido().getValueAt(fila, 3).toString();
                        Producto productoPedido = (Producto) getVistaTablaPedido().getJtPedido().getValueAt(fila, 4);
                        int cantidadPedido = Integer.parseInt(getVistaTablaPedido().getJtPedido().getValueAt(fila, 5).toString());
                        double precioUnitarioPedido = Double.parseDouble(getVistaTablaPedido().getJtPedido().getValueAt(fila, 6).toString());
                        double precioTotalPedido = Double.parseDouble(getVistaTablaPedido().getJtPedido().getValueAt(fila, 7).toString());
                        String estadoPedido = "Asignado"; // el estado del pedido cambia a "Asignado" cuando se le asigna un repartidor

                        Pedido pedidoAsignado = new Pedido(numeroPedido, fechaPedido, clientePedido, direccionPedido,
                                productoPedido, cantidadPedido, precioUnitarioPedido, precioTotalPedido, estadoPedido);
                        getColaPedidoAsignado().agregar(pedidoAsignado);
                        JOptionPane.showMessageDialog(null, "Pedido asignado al repartidor");
                        getColaPedido().eliminar();
                        getColaPedido().agregarPedidoTabla(getVistaTablaPedido().getJtPedido());
                        getColaPedidoAsignado().agregarPedidoAsignadoTabla(getVistaPrincipalRepartidor().getJtPedidoAsignado());
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay pedidos por asignar");
                    }
                    if (getColaPedido().getTotalPedidos() == 0) {
                        DefaultTableModel modelo = (DefaultTableModel) getVistaTablaPedido().getJtPedido().getModel();
                        modelo.setRowCount(0);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe iniciar sesión como repartidor");
            }
        }
    }
}
