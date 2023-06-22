package org.delivery.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    private long cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private String contrasena;

    /**
     * Método para mostrar la información del usuario
     * 
     * @return el nombre y apellido del usuario
     */
    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
