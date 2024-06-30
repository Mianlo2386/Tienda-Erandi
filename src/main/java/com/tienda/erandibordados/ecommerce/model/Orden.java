package com.tienda.erandibordados.ecommerce.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Orden {
    private Long id;
    private String numero;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaRecepcion;
    private double total;
}

