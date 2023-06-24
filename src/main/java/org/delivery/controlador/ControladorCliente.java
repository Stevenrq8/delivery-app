package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.ClienteDAO;
import org.delivery.vista.*;

import javax.swing.*;
import java.awt.event.*;

@Getter
@Setter
public class ControladorCliente implements ActionListener {

    private ColaAdministrador colaAdministrador;
    private ColaAdministradorPedido colaAdministradorPedido;
    private ColaCliente colaCliente;
    private ColaRepartidor colaRepartidor;
    private ColaRestaurante colaRestaurante;
    private VistaPrincipal vistaPrincipal;
    private VistaPrincipalCliente vistaPrincipalCliente;
    private VistaRegistroCliente vistaRegistroCliente;
    private VistaTablaCliente vistaTablaCliente;
    private VistaTablaProducto vistaTablaProducto;
    private ClienteDAO clienteDAO;

    public ControladorCliente(ColaAdministrador colaAdministrador, ColaAdministradorPedido colaAdministradorPedido,
                              ColaCliente colaCliente, ColaRepartidor colaRepartidor, ColaRestaurante colaRestaurante,
                              VistaPrincipal vistaPrincipal, VistaPrincipalCliente vistaPrincipalCliente,
                              VistaRegistroCliente vistaRegistroCliente, VistaTablaCliente vistaTablaCliente,
                              VistaTablaProducto vistaTablaProducto,
                              ClienteDAO clienteDAO) {
        this.colaAdministrador = colaAdministrador;
        this.colaAdministradorPedido = colaAdministradorPedido;
        this.colaCliente = colaCliente;
        this.colaRepartidor = colaRepartidor;
        this.colaRestaurante = colaRestaurante;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipalCliente = vistaPrincipalCliente;
        this.vistaRegistroCliente = vistaRegistroCliente;
        this.vistaTablaCliente = vistaTablaCliente;
        this.vistaTablaProducto = vistaTablaProducto;
        this.clienteDAO = clienteDAO;
        this.vistaPrincipal.getJbRegistroCliente().addActionListener(this);
        this.vistaRegistroCliente.getJbCancelar().addActionListener(this);
        this.vistaRegistroCliente.getJbAceptar().addActionListener(this);
        this.vistaPrincipalCliente.getJbVerProductos().addActionListener(this);
        this.clienteDAO.listar(getColaCliente(), getVistaTablaCliente());
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
        String nombre = "", apellido = "", direccion = "", telefono = "", correo = "", contrasena, contrasenaEncriptada = "";
        boolean v = false;

        if (e.getSource() == getVistaPrincipal().getJbRegistroCliente()) {
            getVistaRegistroCliente().setVisible(true);
        }
        if (e.getSource() == getVistaRegistroCliente().getJbCancelar()) {
            getVistaRegistroCliente().dispose();
        } else if (e.getSource() == getVistaRegistroCliente().getJbAceptar()) {
            if (getVistaRegistroCliente().getJtfCedula().getText().isEmpty() ||
                    getVistaRegistroCliente().getJtfNombre().getText().isEmpty() ||
                    getVistaRegistroCliente().getJtfApellido().getText().isEmpty() ||
                    getVistaRegistroCliente().getJtfDireccion().getText().isEmpty() ||
                    getVistaRegistroCliente().getJtfTelefono().getText().isEmpty() ||
                    getVistaRegistroCliente().getJtfCorreo().getText().isEmpty() ||
                    getVistaRegistroCliente().getJpfContrasena().getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            } else {
                cedula = Long.parseLong(getVistaRegistroCliente().getJtfCedula().getText());
                nombre = getVistaRegistroCliente().getJtfNombre().getText();
                apellido = getVistaRegistroCliente().getJtfApellido().getText();
                direccion = getVistaRegistroCliente().getJtfDireccion().getText();
                telefono = getVistaRegistroCliente().getJtfTelefono().getText();
                correo = getVistaRegistroCliente().getJtfCorreo().getText();
                if (getClienteDAO().validarCorreo(correo)) {
                    correo = getVistaRegistroCliente().getJtfCorreo().getText();
                } else {
                    JOptionPane.showMessageDialog(null, "El correo no es válido");
                    getVistaRegistroCliente().getJtfCorreo().requestFocus();
                    return;
                }
                contrasena = new String(getVistaRegistroCliente().getJpfContrasena().getPassword());
                contrasenaEncriptada = Hash.sha1(contrasena);
                v = true;
            }
            if (v) {
                if (getClienteDAO().buscar(cedula, telefono, correo, contrasenaEncriptada) ||
                        buscarUsuarioCola(cedula, telefono, correo, contrasenaEncriptada)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con estos datos",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    getVistaRegistroCliente().getJtfCedula().requestFocus();
                } else {
                    Cliente cliente = new Cliente(cedula, nombre, apellido, direccion, telefono, correo, contrasenaEncriptada);
                    if (getClienteDAO().registrar(cliente)) {
                        getColaCliente().agregar(cliente);
                        getColaCliente().agregarClienteTabla(getVistaTablaCliente().getJtCliente());
                        JOptionPane.showMessageDialog(null, "Se ha registrado correctamente");
                        getVistaRegistroCliente().dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrarse");
                    }
                    getVistaRegistroCliente().getJtfCedula().setText("");
                    getVistaRegistroCliente().getJtfNombre().setText("");
                    getVistaRegistroCliente().getJtfApellido().setText("");
                    getVistaRegistroCliente().getJtfTelefono().setText("");
                    getVistaRegistroCliente().getJtfDireccion().setText("");
                    getVistaRegistroCliente().getJtfCorreo().setText("");
                    getVistaRegistroCliente().getJpfContrasena().setText("");
                    getVistaRegistroCliente().dispose();
                }
            }
        }
        if (e.getSource() == getVistaPrincipalCliente().getJbVerProductos()) {
            if (getVistaTablaProducto().getJtProducto().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No hay productos disponibles en este momento",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            getVistaTablaProducto().setVisible(true);
            JOptionPane.showMessageDialog(null, "¡Debe seleccionar el producto que desea comprar!",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
