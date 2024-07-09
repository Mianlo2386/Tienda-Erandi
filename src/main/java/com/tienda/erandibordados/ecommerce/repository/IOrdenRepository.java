package com.tienda.erandibordados.ecommerce.repository;

import com.tienda.erandibordados.ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long> {

}
