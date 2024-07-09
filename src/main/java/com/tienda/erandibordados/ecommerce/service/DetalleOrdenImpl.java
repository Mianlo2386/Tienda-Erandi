package com.tienda.erandibordados.ecommerce.service;

import com.tienda.erandibordados.ecommerce.model.DetalleOrden;
import com.tienda.erandibordados.ecommerce.repository.IDetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenImpl implements IDetalleOrdenService{
    @Autowired
    private IDetalleOrdenRepository detalleOrdenRepository;
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
