package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
public class Cliente extends Usuario {

    private Cliente siguiente; // Apuntador al siguiente nodo de la cola

    public Cliente() {
        super(0, "", "", "", "", "", "");
        siguiente = null;
    }

    public Cliente(long cedula, String nombre, String apellido, String direccion, String telefono, String correo,
                   String contrasena) {
        super(cedula, nombre, apellido, direccion, telefono, correo, contrasena);
        siguiente = null;
    }
}
