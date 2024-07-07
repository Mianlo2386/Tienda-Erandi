package com.tienda.erandibordados.ecommerce.service;

import com.tienda.erandibordados.ecommerce.model.Producto;
import com.tienda.erandibordados.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }


}
