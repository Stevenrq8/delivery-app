package org.delivery.modelo;

import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaAdministradorPedido {

    private AdministradorPedido primero;
    private AdministradorPedido ultimo;
    private int totalAdministradoresPedido;

    public boolean colaVacia() {
        return getTotalAdministradoresPedido() == 0;
    }

    public void agregar(AdministradorPedido administradorPedido) {
        if (colaVacia()) {
            setPrimero(administradorPedido);
            setUltimo(administradorPedido);
        } else {
            getUltimo().setSiguiente(administradorPedido);
            setUltimo(administradorPedido);
        }
        totalAdministradoresPedido++;
    }

    /**
     * Método que agrega los administradores de pedido a una tabla.
     *
     * @param tabla tabla a la que se le agregan los administradores de pedido
     */
    public void agregarAdministradorPedidoTabla(JTable tabla) {
        AdministradorPedido aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;
        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        while (aux != null) {
            modelo.addRow(new Object[]{});
            modelo.setValueAt(aux.getCedula(), fila, 0);
            modelo.setValueAt(aux.getNombre(), fila, 1);
            modelo.setValueAt(aux.getApellido(), fila, 2);
            modelo.setValueAt(aux.getDireccion(), fila, 3);
            modelo.setValueAt(aux.getTelefono(), fila, 4);
            modelo.setValueAt(aux.getCorreo(), fila, 5);
            fila++;
            aux = aux.getSiguiente();
        }
        tabla.setModel(modelo);
    }

    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        if (!colaVacia()) {
            AdministradorPedido aux = getPrimero();
            while (aux != null) {
                if (aux.getCedula() == cedula || aux.getTelefono().equals(telefono) || aux.getCorreo().equals(correo)
                        || aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(long cedula) {
        if (!colaVacia()) {
            AdministradorPedido aux = getPrimero();
            while (aux != null) {
                if (aux.getCedula() == cedula) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(long cedula, String correo, String contrasena) {
        if (!colaVacia()) {
            AdministradorPedido aux = getPrimero();
            while (aux != null) {
                if (aux.getCedula() == cedula && aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public AdministradorPedido buscarAdministradorPedido(String correo, String contrasena) {
        if (!colaVacia()) {
            AdministradorPedido aux = getPrimero();
            while (aux != null) {
                if (aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return null;
    }

    public boolean buscar(String correo, String contrasena) {
        if (!colaVacia()) {
            AdministradorPedido aux = getPrimero();
            while (aux != null) {
                if (aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public void eliminar() {
        if (!colaVacia()) {
            setPrimero(getPrimero().getSiguiente());
            totalAdministradoresPedido--;
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalAdministradoresPedido = 0;
    }
}
