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
public class Direccion {
    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String ciudad;
    private String estado;
    private String codigoPostal;
}
