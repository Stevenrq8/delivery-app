package org.delivery.modelo;

import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaRepartidor {

    private Repartidor primero;
    private Repartidor ultimo;
    private int totalRepartidores;

    public boolean colaVacia() {
        return getTotalRepartidores() == 0;
    }

    public void agregar(Repartidor repartidor) {
        if (colaVacia()) {
            setPrimero(repartidor);
            setUltimo(repartidor);
        } else {
            getUltimo().setSiguiente(repartidor);
            setUltimo(repartidor);
        }
        totalRepartidores++;
    }

    /**
     * Método que agrega los repartidores a la tabla
     *
     * @param tabla tabla donde se agregará el repartidor
     */
    public void agregarRepartidorTabla(JTable tabla) {
        Repartidor aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;

        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Tipo de Vehículo");
        modelo.addColumn("Correo");
        if (!colaVacia()) {
            while (aux != null) {
                modelo.addRow(new Object[]{});
                modelo.setValueAt(aux.getCedula(), fila, 0);
                modelo.setValueAt(aux.getNombre(), fila, 1);
                modelo.setValueAt(aux.getApellido(), fila, 2);
                modelo.setValueAt(aux.getDireccion(), fila, 3);
                modelo.setValueAt(aux.getTelefono(), fila, 4);
                modelo.setValueAt(aux.getTipoVehiculo(), fila, 5);
                modelo.setValueAt(aux.getCorreo(), fila, 6);
                fila++;
                aux = aux.getSiguiente();
            }
            tabla.setModel(modelo);
        }
    }

    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        if (!colaVacia()) {
            Repartidor aux = getPrimero();
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
            Repartidor aux = getPrimero();
            while (aux != null) {
                if (aux.getCedula() == cedula) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    /**
     * Busca un repartidor por su correo y contraseña
     *
     * @param correo     del repartidor
     * @param contrasena del repartidor
     * @return el repartidor si lo encuentra, de lo contrario retorna null
     */
    public Repartidor buscarRepartidor(String correo, String contrasena) {
        if (!colaVacia()) {
            Repartidor aux = getPrimero();
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
            Repartidor aux = getPrimero();
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
            Repartidor aux = getPrimero();
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
            totalRepartidores--;
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalRepartidores = 0;
    }
}
