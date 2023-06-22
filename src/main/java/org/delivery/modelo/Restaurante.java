package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Restaurante {

    private long nit;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String contrasena;
    private Restaurante siguiente; // Apuntador al siguiente nodo de la cola

    public Restaurante(long nit, String nombre, String direccion, String telefono, String correo, String contrasena) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasena = contrasena;
        this.siguiente = null;
    }

    /**
     * Método para mostrar la información del restaurante
     *
     * @return el nombre del restaurante
     */
    @Override
    public String toString() {
        return nombre;
    }
}
