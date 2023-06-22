package org.delivery.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaCliente {

    private Cliente primero;
    private Cliente ultimo;
    private int totalClientes;

    public boolean colaVacia() {
        return getTotalClientes() == 0;
    }

    public void agregar(Cliente cliente) {
        if (colaVacia()) {
            setPrimero(cliente);
            setUltimo(cliente);
        } else {
            getUltimo().setSiguiente(cliente);
            setUltimo(cliente);
        }
        totalClientes++;
    }

    /**
     * Método para agregar los clientes a la tabla
     *
     * @param tabla tabla a la que se le agregan los clientes
     */
    public void agregarClienteTabla(JTable tabla) {
        Cliente aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;

        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        if (!colaVacia()) {
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
    }

    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        if (!colaVacia()) {
            Cliente aux = getPrimero();
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
            Cliente aux = getPrimero();
            while (aux != null) {
                if (aux.getCedula() == cedula) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public Cliente buscarCliente(String correo, String contrasena) {
        if (!colaVacia()) {
            Cliente aux = getPrimero();
            while (aux != null) {
                if (aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return null;
    }

    public boolean buscar(long cedula, String correo, String contrasena) {
        if (!colaVacia()) {
            Cliente aux = getPrimero();
            while (aux != null) {
                if (aux.getCedula() == cedula && aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(String correo, String contrasena) {
        if (!colaVacia()) {
            Cliente aux = getPrimero();
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
            totalClientes--;
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalClientes = 0;
    }
}