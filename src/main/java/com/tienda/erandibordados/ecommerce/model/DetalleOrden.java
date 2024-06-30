package com.tienda.erandibordados.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleOrden {
    private Long id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;
}

