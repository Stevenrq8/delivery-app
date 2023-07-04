package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.*;
import org.delivery.vista.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

@Getter
@Setter
public class ControladorAdministrador implements ActionListener {

    private ColaAdministrador colaAdministrador;
    private ColaAdministradorPedido colaAdministradorPedido;
    private ColaCliente colaCliente;
    private ColaRepartidor colaRepartidor;
    private ColaRestaurante colaRestaurante;
    private VistaPrincipal vistaPrincipal;
    private VistaPrincipalAdministrador vistaPrincipalAdministrador;
    private VistaPrincipalRepartidor vistaPrincipalRepartidor;
    private VistaRegistroAdministrador vistaRegistroAdministrador;
    private VistaTablaAdministrador vistaTablaAdministrador;
    private VistaTablaAdministradorPedido vistaTablaAdministradorPedido;
    private VistaTablaCliente vistaTablaCliente;
    private VistaTablaRepartidor vistaTablaRepartidor;
    private VistaTablaRestaurante vistaTablaRestaurante;
    private VistaTablaProducto vistaTablaProducto;
    private VistaTablaPedido vistaTablaPedido;
    private AdministradorDAO administradorDAO;


    public ControladorAdministrador(ColaAdministrador colaAdministrador, ColaAdministradorPedido colaAdministradorPedido,
                                    ColaCliente colaCliente, ColaRepartidor colaRepartidor, ColaRestaurante colaRestaurante,
                                    VistaPrincipal vistaPrincipal, VistaPrincipalAdministrador vistaPrincipalAdministrador,
                                    VistaPrincipalRepartidor vistaPrincipalRepartidor, VistaRegistroAdministrador vistaRegistroAdministrador,
                                    VistaTablaAdministrador vistaTablaAdministrador, VistaTablaAdministradorPedido vistaTablaAdministradorPedido,
                                    VistaTablaCliente vistaTablaCliente, VistaTablaRepartidor vistaTablaRepartidor,
                                    VistaTablaRestaurante vistaTablaRestaurante, VistaTablaProducto vistaTablaProducto,
                                    VistaTablaPedido vistaTablaPedido, AdministradorDAO administradorDAO) {
        this.colaAdministrador = colaAdministrador;
        this.colaAdministradorPedido = colaAdministradorPedido;
        this.colaCliente = colaCliente;
        this.colaRepartidor = colaRepartidor;
        this.colaRestaurante = colaRestaurante;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipalAdministrador = vistaPrincipalAdministrador;
        this.vistaPrincipalRepartidor = vistaPrincipalRepartidor;
        this.vistaRegistroAdministrador = vistaRegistroAdministrador;
        this.vistaTablaAdministrador = vistaTablaAdministrador;
        this.vistaTablaAdministradorPedido = vistaTablaAdministradorPedido;
        this.vistaTablaCliente = vistaTablaCliente;
        this.vistaTablaRepartidor = vistaTablaRepartidor;
        this.vistaTablaRestaurante = vistaTablaRestaurante;
        this.vistaTablaProducto = vistaTablaProducto;
        this.vistaTablaPedido = vistaTablaPedido;
        this.administradorDAO = administradorDAO;
        this.vistaPrincipal.getJmiRegistrarAdministrador().addActionListener(this);
        this.vistaRegistroAdministrador.getJbCancelar().addActionListener(this);
        this.vistaRegistroAdministrador.getJbAceptar().addActionListener(this);
        this.vistaPrincipalAdministrador.getJcbUsuariosRegistrados().addActionListener(this);
        this.vistaPrincipalAdministrador.getJbVer().addActionListener(this);
        this.administradorDAO.listar(getColaAdministrador(), getVistaTablaAdministrador());
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
        Administrador administrador;

        if (e.getSource() == getVistaPrincipal().getJmiRegistrarAdministrador()) {
            getVistaRegistroAdministrador().setVisible(true);
        }
        if (e.getSource() == getVistaRegistroAdministrador().getJbCancelar()) {
            getVistaRegistroAdministrador().dispose();
        } else if (e.getSource() == getVistaRegistroAdministrador().getJbAceptar()) {
            if (getVistaRegistroAdministrador().getJtfCedula().getText().isEmpty() ||
                    getVistaRegistroAdministrador().getJtfNombre().getText().isEmpty() ||
                    getVistaRegistroAdministrador().getJtfApellido().getText().isEmpty() ||
                    getVistaRegistroAdministrador().getJtfDireccion().getText().isEmpty() ||
                    getVistaRegistroAdministrador().getJtfTelefono().getText().isEmpty() ||
                    getVistaRegistroAdministrador().getJtfCorreo().getText().isEmpty() ||
                    getVistaRegistroAdministrador().getJpfContrasena().getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            } else {
                cedula = Long.parseLong(getVistaRegistroAdministrador().getJtfCedula().getText());
                nombre = getVistaRegistroAdministrador().getJtfNombre().getText();
                apellido = getVistaRegistroAdministrador().getJtfApellido().getText();
                direccion = getVistaRegistroAdministrador().getJtfDireccion().getText();
                telefono = getVistaRegistroAdministrador().getJtfTelefono().getText();
                correo = getVistaRegistroAdministrador().getJtfCorreo().getText();
                if (getAdministradorDAO().validarCorreo(correo)) {
                    correo = getVistaRegistroAdministrador().getJtfCorreo().getText();
                } else {
                    JOptionPane.showMessageDialog(null, "El correo no es válido");
                    getVistaRegistroAdministrador().getJtfCorreo().requestFocus();
                    return;
                }
                contrasena = new String(getVistaRegistroAdministrador().getJpfContrasena().getPassword());
                contrasenaEncriptada = Hash.sha1(contrasena);
                v = true;
            }
            if (v) {
                if (getAdministradorDAO().buscar(cedula, telefono, correo, contrasenaEncriptada) ||
                        buscarUsuarioCola(cedula, telefono, correo, contrasenaEncriptada)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con estos datos",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    getVistaRegistroAdministrador().getJtfCedula().requestFocus();
                } else {
                    administrador = new Administrador(cedula, nombre, apellido, direccion, telefono, correo, contrasenaEncriptada);
                    if (getAdministradorDAO().registrar(administrador)) {
                        getColaAdministrador().agregar(administrador);
                        getColaAdministrador().agregarAdministradorTabla(getVistaTablaAdministrador().getJtAdministrador());
                        JOptionPane.showMessageDialog(null, "Se ha registrado exitosamente");
                        getVistaRegistroAdministrador().dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrarse");
                    }
                    getVistaRegistroAdministrador().getJtfCedula().setText("");
                    getVistaRegistroAdministrador().getJtfNombre().setText("");
                    getVistaRegistroAdministrador().getJtfApellido().setText("");
                    getVistaRegistroAdministrador().getJtfTelefono().setText("");
                    getVistaRegistroAdministrador().getJtfDireccion().setText("");
                    getVistaRegistroAdministrador().getJtfCorreo().setText("");
                    getVistaRegistroAdministrador().getJpfContrasena().setText("");
                    getVistaRegistroAdministrador().dispose();
                }
            }
        }
        if (e.getSource() == getVistaPrincipalAdministrador().getJbVer()) {
            if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Administradores")) {
                if (getVistaTablaAdministrador().getJtAdministrador().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay administradores registrados");
                    return;
                }
                getVistaTablaAdministrador().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Administradores de Pedido")) {
                if (getVistaTablaAdministradorPedido().getJtAdministradorPedido().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay administradores de pedido registrados");
                    return;
                }
                getVistaTablaAdministradorPedido().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Clientes")) {
                if (getVistaTablaCliente().getJtCliente().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay clientes registrados");
                    return;
                }
                getVistaTablaCliente().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Repartidores")) {
                if (getVistaTablaRepartidor().getJtRepartidor().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay repartidores registrados");
                    return;
                }
                getVistaTablaRepartidor().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Productos")) {
                if (getVistaTablaProducto().getJtProducto().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay productos registrados");
                    return;
                }
                getVistaTablaProducto().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Restaurantes")) {
                if (getVistaTablaRestaurante().getJtRestaurante().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay restaurantes registrados");
                    return;
                }
                getVistaTablaRestaurante().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Pedidos")) {
                if (getVistaTablaPedido().getJtPedido().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay pedidos registrados");
                    return;
                }
                getVistaTablaPedido().setVisible(true);
                // se oculta el botón de asignar pedido para que solo el administrador de pedido pueda verlo
                getVistaTablaPedido().getJbAsignarPedido().setVisible(false);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Pedidos Asignados")) {
                if (getVistaPrincipalRepartidor().getJtPedidoAsignado().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay pedidos asignados registrados");
                    return;
                }
                getVistaPrincipalRepartidor().setVisible(true);
            } else if (Objects.equals(getVistaPrincipalAdministrador().getJcbUsuariosRegistrados().getSelectedItem(), "Seleccione una Opción")) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opción");
            }
        }
    }
}
