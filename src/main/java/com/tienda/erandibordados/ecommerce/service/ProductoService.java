package com.tienda.erandibordados.ecommerce.service;

import com.tienda.erandibordados.ecommerce.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    public Producto save(Producto producto);
    public Optional<Producto> get(Long id);

    public void eliminar (Long id);
    public List<Producto> findAll();

    public void update(Producto producto);
}
