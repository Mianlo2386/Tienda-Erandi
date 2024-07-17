package com.tienda.erandibordados.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "detalles")
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double cantidad;
    private double precio;
    @Enumerated(EnumType.STRING)
    private TipoCategoria tipoCategoria;
    private double total;

    @ManyToOne//Revisar si no es ManyToOne o OneToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}

