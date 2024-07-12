package com.tienda.erandibordados.ecommerce.service;

import com.tienda.erandibordados.ecommerce.model.Orden;
import com.tienda.erandibordados.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    List<Orden> findAll();
    Orden save(Orden orden);
   String generarNumeroOrden();
   List<Orden> findByUsuario(Usuario usuario);
   Optional<Orden> findById(Long id);
}
