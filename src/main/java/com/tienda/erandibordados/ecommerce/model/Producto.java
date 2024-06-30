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
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int cantidad;
}
