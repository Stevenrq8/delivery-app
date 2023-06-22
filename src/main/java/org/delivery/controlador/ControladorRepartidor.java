package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.RepartidorDAO;
import org.delivery.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Objects;

@Getter
@Setter
public class ControladorRepartidor implements ActionListener {

    private ColaAdministrador colaAdministrador;
    private ColaAdministradorPedido colaAdministradorPedido;
    private ColaCliente colaCliente;
    private ColaRepartidor colaRepartidor;
    private ColaRestaurante colaRestaurante;
    private ColaPedidoAsignado colaPedidoAsignado;
    private VistaPrincipal vistaPrincipal;
    private VistaPrincipalRepartidor vistaPrincipalRepartidor;
    private VistaRegistroRepartidor vistaRegistroRepartidor;
    private VistaTablaRepartidor vistaTablaRepartidor;
    private RepartidorDAO repartidorDAO;

    public ControladorRepartidor(ColaAdministrador colaAdministrador, ColaAdministradorPedido colaAdministradorPedido,
                                 ColaCliente colaCliente, ColaRepartidor colaRepartidor, ColaRestaurante colaRestaurante,
                                 ColaPedidoAsignado colaPedidoAsignado, VistaPrincipal vistaPrincipal,
                                 VistaPrincipalRepartidor vistaPrincipalRepartidor, VistaRegistroRepartidor vistaRegistroRepartidor,
                                 VistaTablaRepartidor vistaTablaRepartidor, RepartidorDAO repartidorDAO) {
        this.colaAdministrador = colaAdministrador;
        this.colaAdministradorPedido = colaAdministradorPedido;
        this.colaCliente = colaCliente;
        this.colaRepartidor = colaRepartidor;
        this.colaRestaurante = colaRestaurante;
        this.colaPedidoAsignado = colaPedidoAsignado;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipalRepartidor = vistaPrincipalRepartidor;
        this.vistaRegistroRepartidor = vistaRegistroRepartidor;
        this.vistaTablaRepartidor = vistaTablaRepartidor;
        this.repartidorDAO = repartidorDAO;
        this.vistaPrincipal.getJbRegistroRepartidor().addActionListener(this);
        this.vistaRegistroRepartidor.getJbCancelar().addActionListener(this);
        this.vistaRegistroRepartidor.getJbAceptar().addActionListener(this);
        this.vistaPrincipalRepartidor.getJbAceptarPedido().addActionListener(this);
        this.repartidorDAO.listar(getColaRepartidor(), getVistaTablaRepartidor());
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
        long cedula = 0L;
        String nombre = "", apellido = "", direccion = "", telefono = "", tipoVehiculo = "", correo = "", contrasena,
                contrasenaEncriptada = "";
        boolean v = false;

        if (e.getSource() == getVistaPrincipal().getJbRegistroRepartidor()) {
            getVistaRegistroRepartidor().setVisible(true);
        }
        if (e.getSource() == getVistaRegistroRepartidor().getJbCancelar()) {
            getVistaRegistroRepartidor().setVisible(false);
        } else if (e.getSource() == getVistaRegistroRepartidor().getJbAceptar()) {
            if (getVistaRegistroRepartidor().getJtfCedula().getText().isEmpty() ||
                    getVistaRegistroRepartidor().getJtfNombre().getText().isEmpty() ||
                    getVistaRegistroRepartidor().getJtfApellido().getText().isEmpty() ||
                    getVistaRegistroRepartidor().getJtfDireccion().getText().isEmpty() ||
                    getVistaRegistroRepartidor().getJtfTelefono().getText().isEmpty() ||
                    Objects.requireNonNull(getVistaRegistroRepartidor().getJcbTipoVehiculo().getSelectedItem()).toString().isEmpty() ||
                    getVistaRegistroRepartidor().getJtfCorreo().getText().isEmpty() ||
                    getVistaRegistroRepartidor().getJpfContrasena().getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                cedula = Long.parseLong(getVistaRegistroRepartidor().getJtfCedula().getText());
                nombre = getVistaRegistroRepartidor().getJtfNombre().getText();
                apellido = getVistaRegistroRepartidor().getJtfApellido().getText();
                direccion = getVistaRegistroRepartidor().getJtfDireccion().getText();
                telefono = getVistaRegistroRepartidor().getJtfTelefono().getText();
                tipoVehiculo = Objects.requireNonNull(getVistaRegistroRepartidor().getJcbTipoVehiculo().getSelectedItem()).toString();
                if (tipoVehiculo.equals("Tipo de Vehículo")) {
                    JOptionPane.showMessageDialog(null, "Seleccione un tipo de vehículo",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                correo = getVistaRegistroRepartidor().getJtfCorreo().getText();
                if (getRepartidorDAO().validarCorreo(correo)) {
                    correo = getVistaRegistroRepartidor().getJtfCorreo().getText();
                } else {
                    JOptionPane.showMessageDialog(null, "El correo no es válido");
                    getVistaRegistroRepartidor().getJtfCorreo().requestFocus();
                    return;
                }
                contrasena = new String(getVistaRegistroRepartidor().getJpfContrasena().getPassword());
                contrasenaEncriptada = Hash.sha1(contrasena);
                v = true;
            }
            if (v) {
                if (getRepartidorDAO().buscar(cedula, telefono, correo, contrasenaEncriptada) ||
                        buscarUsuarioCola(cedula, telefono, correo, contrasenaEncriptada)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con estos datos",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    getVistaRegistroRepartidor().getJtfCedula().requestFocus();
                } else {
                    Repartidor repartidor = new Repartidor(cedula, nombre, apellido, direccion, telefono, tipoVehiculo,
                            correo, contrasenaEncriptada);
                    if (getRepartidorDAO().registrar(repartidor)) {
                        getColaRepartidor().agregar(repartidor);
                        getColaRepartidor().agregarRepartidorTabla(getVistaTablaRepartidor().getJtRepartidor());
                        JOptionPane.showMessageDialog(null, "Se ha registrado exitosamente");
                        getVistaRegistroRepartidor().dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrarse");
                    }
                    getVistaRegistroRepartidor().getJtfCedula().setText("");
                    getVistaRegistroRepartidor().getJtfNombre().setText("");
                    getVistaRegistroRepartidor().getJtfApellido().setText("");
                    getVistaRegistroRepartidor().getJtfDireccion().setText("");
                    getVistaRegistroRepartidor().getJtfTelefono().setText("");
                    getVistaRegistroRepartidor().getJcbTipoVehiculo().setSelectedItem("Tipo de Vehículo");
                    getVistaRegistroRepartidor().getJtfCorreo().setText("");
                    getVistaRegistroRepartidor().getJpfContrasena().setText("");
                    getVistaRegistroRepartidor().dispose();
                }
            }
        }
        if (e.getSource() == getVistaPrincipalRepartidor().getJbAceptarPedido()) {
            Pedido primerPedido = getColaPedidoAsignado().getPrimero();
            primerPedido.setEstado("En camino");
            JOptionPane.showMessageDialog(null, "Pedido en camino" + "\n" + "Cliente: " +
                    primerPedido.getCliente().toString());
            getColaPedidoAsignado().eliminar();
            if (getColaPedidoAsignado().getTotalPedidos() == 0) {
                DefaultTableModel modelo = (DefaultTableModel) getVistaPrincipalRepartidor().getJtPedidoAsignado().getModel();
                modelo.setRowCount(0);
            }
        }
    }
}
