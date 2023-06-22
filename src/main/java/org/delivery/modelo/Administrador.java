package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
public class Administrador extends Usuario {

    private Administrador siguiente; // Apuntador al siguiente nodo de la cola

    public Administrador() {
        super(0, "", "", "", "", "", "");
        this.siguiente = null;
    }

    public Administrador(long cedula, String nombre, String apellido, String direccion, String telefono, String correo,
                         String contrasena) {
        super(cedula, nombre, apellido, direccion, telefono, correo, contrasena);
        this.siguiente = null;
    }
}
