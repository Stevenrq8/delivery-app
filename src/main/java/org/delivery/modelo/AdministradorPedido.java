package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
public class AdministradorPedido extends Usuario {

    private AdministradorPedido siguiente; // Apuntador al siguiente nodo de la cola

    public AdministradorPedido() {
        super(0, "", "", "", "", "", "");
        this.siguiente = null;
    }

    public AdministradorPedido(long cedula, String nombre, String apellido, String direccion, String telefono, String correo,
                               String contrasena) {
        super(cedula, nombre, apellido, direccion, telefono, correo, contrasena);
    }
}
