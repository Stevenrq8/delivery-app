package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.*;
import org.delivery.vista.*;

import javax.swing.*;
import java.awt.event.*;

@Getter
@Setter
public class ControladorInicioSesion implements ActionListener {

    private Administrador administrador;
    private AdministradorPedido administradorPedido;
    private Cliente cliente;
    private Repartidor repartidor;
    private Restaurante restaurante;
    private ColaAdministrador colaAdministrador;
    private ColaAdministradorPedido colaAdministradorPedido;
    private ColaCliente colaCliente;
    private ColaRepartidor colaRepartidor;
    private ColaRestaurante colaRestaurante;
    private VistaPrincipal vistaPrincipal;
    private VistaPrincipalCliente vistaPrincipalCliente;
    private VistaPrincipalAdministrador vistaPrincipalAdministrador;
    private VistaPrincipalAdministradorPedido vistaPrincipalAdministradorPedido;
    private VistaPrincipalRestaurante vistaPrincipalRestaurante;
    private VistaPrincipalRepartidor vistaPrincipalRepartidor;
    private VistaInicioSesion vistaInicioSesion;
    private VistaTablaProducto vistaTablaProducto;

    public ControladorInicioSesion(Administrador administrador, AdministradorPedido administradorPedido, Cliente cliente,
                                   Repartidor repartidor, Restaurante restaurante, ColaAdministrador colaAdministrador,
                                   ColaAdministradorPedido colaAdministradorPedido, ColaCliente colaCliente,
                                   ColaRepartidor colaRepartidor, ColaRestaurante colaRestaurante, VistaPrincipal vistaPrincipal,
                                   VistaPrincipalCliente vistaPrincipalCliente, VistaPrincipalAdministrador vistaPrincipalAdministrador,
                                   VistaPrincipalAdministradorPedido vistaPrincipalAdministradorPedido,
                                   VistaPrincipalRestaurante vistaPrincipalRestaurante, VistaPrincipalRepartidor vistaPrincipalRepartidor,
                                   VistaInicioSesion vistaInicioSesion, VistaTablaProducto vistaTablaProducto) {
        this.cliente = cliente;
        this.administrador = administrador;
        this.administradorPedido = administradorPedido;
        this.repartidor = repartidor;
        this.restaurante = restaurante;
        this.colaAdministrador = colaAdministrador;
        this.colaAdministradorPedido = colaAdministradorPedido;
        this.colaCliente = colaCliente;
        this.colaRepartidor = colaRepartidor;
        this.colaRestaurante = colaRestaurante;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipalCliente = vistaPrincipalCliente;
        this.vistaPrincipalAdministrador = vistaPrincipalAdministrador;
        this.vistaPrincipalRepartidor = vistaPrincipalRepartidor;
        this.vistaPrincipalAdministradorPedido = vistaPrincipalAdministradorPedido;
        this.vistaPrincipalRestaurante = vistaPrincipalRestaurante;
        this.vistaInicioSesion = vistaInicioSesion;
        this.vistaTablaProducto = vistaTablaProducto;
        this.vistaInicioSesion.getJbCancelar().addActionListener(this);
        this.vistaInicioSesion.getJbIngresar().addActionListener(this);
        this.vistaPrincipal.getJbIniciarSesion().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getVistaPrincipal().getJbIniciarSesion()) {
            getVistaInicioSesion().setVisible(true);
        }
        if (e.getSource() == getVistaInicioSesion().getJbCancelar()) {
            getVistaInicioSesion().dispose();
        } else if (e.getSource() == getVistaInicioSesion().getJbIngresar()) {
            String correo = getVistaInicioSesion().getJtfCorreo().getText();
            String contrasena = new String(getVistaInicioSesion().getJpfContrasena().getPassword());
            String contrasenaEncriptada = Hash.sha1(contrasena);


            /*
             * Se busca el correo y contraseña del usuario en su respectiva cola. Si se encuentra,
             * se asigna el usuario encontrado a la variable correspondiente para poder acceder.
             */
            if (getColaAdministrador().buscar(correo, contrasenaEncriptada)) { // Se busca el correo y contraseña en la cola de administradores
                setAdministrador(getColaAdministrador().buscarAdministrador(correo, contrasenaEncriptada)); // Se asigna el administrador encontrado
                getVistaInicioSesion().setVisible(false);
                getVistaInicioSesion().getJtfCorreo().setText("");
                getVistaInicioSesion().getJpfContrasena().setText("");
                getVistaInicioSesion().getJtfCorreo().requestFocus();
                getVistaPrincipalAdministrador().setVisible(true);
            } else if (getColaAdministradorPedido().buscar(correo, contrasenaEncriptada)) {
                setAdministradorPedido(getColaAdministradorPedido().buscarAdministradorPedido(correo, contrasenaEncriptada));
                getVistaInicioSesion().setVisible(false);
                getVistaInicioSesion().getJtfCorreo().setText("");
                getVistaInicioSesion().getJpfContrasena().setText("");
                getVistaInicioSesion().getJtfCorreo().requestFocus();

                getVistaPrincipalAdministradorPedido().setVisible(true);
            } else if (getColaCliente().buscar(correo, contrasenaEncriptada)) {
                setCliente(getColaCliente().buscarCliente(correo, contrasenaEncriptada));
                getVistaInicioSesion().setVisible(false);
                getVistaInicioSesion().getJtfCorreo().setText("");
                getVistaInicioSesion().getJpfContrasena().setText("");
                getVistaInicioSesion().getJtfCorreo().requestFocus();

                getVistaPrincipalCliente().setVisible(true);
            } else if (getColaRestaurante().buscar(correo, contrasenaEncriptada)) {
                setRestaurante(getColaRestaurante().buscarRestaurante(correo, contrasenaEncriptada));
                getVistaInicioSesion().setVisible(false);
                getVistaInicioSesion().getJtfCorreo().setText("");
                getVistaInicioSesion().getJpfContrasena().setText("");
                getVistaInicioSesion().getJtfCorreo().requestFocus();
                getVistaPrincipalRestaurante().setVisible(true);
            } else if (getColaRepartidor().buscar(correo, contrasenaEncriptada)) {
                setRepartidor(getColaRepartidor().buscarRepartidor(correo, contrasenaEncriptada));
                getVistaInicioSesion().setVisible(false);
                getVistaInicioSesion().getJtfCorreo().setText("");
                getVistaInicioSesion().getJpfContrasena().setText("");
                getVistaInicioSesion().getJtfCorreo().requestFocus();
                getVistaPrincipalRepartidor().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Correo o Contraseña incorrectos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                getVistaInicioSesion().getJtfCorreo().requestFocus();
            }
        }
    }
}
