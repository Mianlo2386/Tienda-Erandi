package com.tienda.erandibordados.ecommerce.repository;

import com.tienda.erandibordados.ecommerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
}
