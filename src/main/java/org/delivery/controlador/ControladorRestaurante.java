package org.delivery.controlador;

import lombok.Getter;
import lombok.Setter;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.RestauranteDAO;
import org.delivery.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class ControladorRestaurante implements ActionListener {

    private ColaAdministrador colaAdministrador;
    private ColaAdministradorPedido colaAdministradorPedido;
    private ColaCliente colaCliente;
    private ColaRepartidor colaRepartidor;
    private ColaRestaurante colaRestaurante;
    private VistaPrincipal vistaPrincipal;
    private VistaPrincipalRestaurante vistaPrincipalRestaurante;
    private VistaRegistroRestaurante vistaRegistroRestaurante;
    private VistaRegistroProducto vistaRegistroProducto;
    private VistaTablaRestaurante vistaTablaRestaurante;
    private RestauranteDAO restauranteDAO;

    public ControladorRestaurante(ColaAdministrador colaAdministrador, ColaAdministradorPedido colaAdministradorPedido,
                                  ColaCliente colaCliente, ColaRepartidor colaRepartidor, ColaRestaurante colaRestaurante,
                                  VistaPrincipal vistaPrincipal, VistaPrincipalRestaurante vistaPrincipalRestaurante,
                                  VistaRegistroRestaurante vistaRegistroRestaurante, VistaRegistroProducto vistaRegistroProducto,
                                  VistaTablaRestaurante vistaTablaRestaurante, RestauranteDAO restauranteDAO) {
        this.colaAdministrador = colaAdministrador;
        this.colaAdministradorPedido = colaAdministradorPedido;
        this.colaCliente = colaCliente;
        this.colaRepartidor = colaRepartidor;
        this.colaRestaurante = colaRestaurante;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipalRestaurante = vistaPrincipalRestaurante;
        this.vistaRegistroRestaurante = vistaRegistroRestaurante;
        this.vistaRegistroProducto = vistaRegistroProducto;
        this.vistaTablaRestaurante = vistaTablaRestaurante;
        this.restauranteDAO = restauranteDAO;
        this.vistaPrincipal.getJbRegistroRestaurante().addActionListener(this);
        this.vistaRegistroRestaurante.getJbCancelar().addActionListener(this);
        this.vistaRegistroRestaurante.getJbAceptar().addActionListener(this);
        this.vistaPrincipalRestaurante.getJbAgregarProducto().addActionListener(this);
        this.restauranteDAO.listar(getColaRestaurante(), getVistaTablaRestaurante());
    }

    /**
     * Método para buscar un restaurante en la cola de restaurantes
     *
     * @param telefono   teléfono del restaurante a buscar
     * @param correo     correo del restaurante a buscar
     * @param contrasena contraseña del restaurante a buscar
     * @return true si el restaurante se encuentra en la cola, false si no
     */
    public boolean buscarUsuarioCola(long nit, String nombre, String telefono, String correo, String contrasena) {
        return getColaRestaurante().buscar(nit, nombre, telefono, correo, contrasena);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long nit = 0;
        String nombre = "", direccion = "", telefono = "", correo = "", contrasena, contrasenaEncriptada = "";
        boolean v = false;

        if (e.getSource() == getVistaPrincipal().getJbRegistroRestaurante()) {
            getVistaRegistroRestaurante().setVisible(true);
        }
        if (e.getSource() == getVistaRegistroRestaurante().getJbCancelar()) {
            getVistaRegistroRestaurante().setVisible(false);
        } else if (e.getSource() == getVistaRegistroRestaurante().getJbAceptar()) {
            if (getVistaRegistroRestaurante().getJtfNit().getText().isEmpty() ||
                    getVistaRegistroRestaurante().getJtfNombre().getText().isEmpty() ||
                    getVistaRegistroRestaurante().getJtfDireccion().getText().isEmpty() ||
                    getVistaRegistroRestaurante().getJtfTelefono().getText().isEmpty() ||
                    getVistaRegistroRestaurante().getJtfCorreo().getText().isEmpty() ||
                    getVistaRegistroRestaurante().getJpfContrasena().getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            } else {
                nit = Long.parseLong(getVistaRegistroRestaurante().getJtfNit().getText());
                nombre = getVistaRegistroRestaurante().getJtfNombre().getText();
                direccion = getVistaRegistroRestaurante().getJtfDireccion().getText();
                telefono = getVistaRegistroRestaurante().getJtfTelefono().getText();
                correo = getVistaRegistroRestaurante().getJtfCorreo().getText();
                if (getRestauranteDAO().validarCorreo(correo)) {
                    correo = getVistaRegistroRestaurante().getJtfCorreo().getText();
                } else {
                    JOptionPane.showMessageDialog(null, "El correo no es válido");
                    getVistaRegistroRestaurante().getJtfCorreo().requestFocus();
                    return;
                }
                contrasena = new String(getVistaRegistroRestaurante().getJpfContrasena().getPassword());
                contrasenaEncriptada = Hash.sha1(contrasena);
                v = true;
            }
            if (v) {
                if (getRestauranteDAO().buscar(nit, nombre, telefono, correo, contrasenaEncriptada) ||
                        buscarUsuarioCola(nit, nombre, telefono, correo, contrasenaEncriptada)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un restaurante con estos datos",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    getVistaRegistroRestaurante().getJtfNombre().requestFocus();
                } else {
                    Restaurante restaurante = new Restaurante(nit, nombre, direccion, telefono, correo, contrasenaEncriptada);
                    if (getRestauranteDAO().registrar(restaurante)) {
                        getColaRestaurante().agregar(restaurante);
                        getColaRestaurante().agregarRestauranteTabla(getVistaTablaRestaurante().getJtRestaurante());
                        JOptionPane.showMessageDialog(null, "Se ha registrado exitosamente");
                        getVistaRegistroRestaurante().dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrarse");
                    }
                    getVistaRegistroRestaurante().getJtfNit().setText("");
                    getVistaRegistroRestaurante().getJtfNombre().setText("");
                    getVistaRegistroRestaurante().getJtfDireccion().setText("");
                    getVistaRegistroRestaurante().getJtfTelefono().setText("");
                    getVistaRegistroRestaurante().getJtfCorreo().setText("");
                    getVistaRegistroRestaurante().getJpfContrasena().setText("");
                    getVistaRegistroRestaurante().dispose();
                }
            }
        }
        if (e.getSource() == getVistaPrincipalRestaurante().getJbAgregarProducto()) {
            getVistaRegistroProducto().setVisible(true);
        }
    }
}
