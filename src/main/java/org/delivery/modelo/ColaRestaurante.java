package org.delivery.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaRestaurante {

    private Restaurante primero;
    private Restaurante ultimo;
    private int totalRestaurantes;

    public boolean colaVacia() {
        return getTotalRestaurantes() == 0;
    }

    public void agregar(Restaurante restaurante) {
        if (colaVacia()) {
            setPrimero(restaurante);
            setUltimo(restaurante);
        } else {
            getUltimo().setSiguiente(restaurante);
            setUltimo(restaurante);
        }
        totalRestaurantes++;
    }

    /**
     * Método para agregar los restaurantes a la tabla
     *
     * @param tabla tabla a la que se le agregaran los restaurantes
     */
    public void agregarRestauranteTabla(JTable tabla) {
        Restaurante aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;

        modelo.addColumn("NIT");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        if (!colaVacia()) {
            while (aux != null) {
                modelo.addRow(new Object[]{});
                modelo.setValueAt(aux.getNit(), fila, 0);
                modelo.setValueAt(aux.getNombre(), fila, 1);
                modelo.setValueAt(aux.getDireccion(), fila, 2);
                modelo.setValueAt(aux.getTelefono(), fila, 3);
                modelo.setValueAt(aux.getCorreo(), fila, 4);
                fila++;
                aux = aux.getSiguiente();
            }
            tabla.setModel(modelo);
        }
    }

    public boolean buscar(String nombre) {
        if (!colaVacia()) {
            Restaurante aux = getPrimero();
            while (aux != null) {
                if (aux.getNombre().equals(nombre)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public Restaurante buscarRestaurante(String correo, String contrasena) {
        if (!colaVacia()) {
            Restaurante aux = getPrimero();
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
            Restaurante aux = getPrimero();
            while (aux != null) {
                if (aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(String nombre, String correo, String contrasena) {
        if (!colaVacia()) {
            Restaurante aux = getPrimero();
            while (aux != null) {
                if (aux.getNombre().equals(nombre) && aux.getCorreo().equals(correo) && aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(String nombre, String direccion, String telefono, String correo, String contrasena) {
        if (!colaVacia()) {
            Restaurante aux = getPrimero();
            while (aux != null) {
                if (aux.getNombre().equals(nombre) || aux.getDireccion().equals(direccion)
                        || aux.getTelefono().equals(telefono) || aux.getCorreo().equals(correo)
                        || aux.getContrasena().equals(contrasena)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(long nit, String nombre, String telefono, String correo, String contrasena) {
        if (!colaVacia()) {
            Restaurante aux = getPrimero();
            while (aux != null) {
                if (aux.getNit() == nit || aux.getNombre().equals(nombre) || aux.getTelefono().equals(telefono)
                        || aux.getCorreo().equals(correo) || aux.getContrasena().equals(contrasena)) {
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
            totalRestaurantes--;
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalRestaurantes = 0;
    }
}
