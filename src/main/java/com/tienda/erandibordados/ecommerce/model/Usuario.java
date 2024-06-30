package com.tienda.erandibordados.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Usuario {
    private Long id;
    private String nombre;
    private String username;
    private String email;
    private Direccion direccion;
    private String telefono;
    private TipoUsuario tipo;
    private String clave;
}
