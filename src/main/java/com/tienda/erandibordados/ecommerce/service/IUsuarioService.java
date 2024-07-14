package com.tienda.erandibordados.ecommerce.service;

import com.tienda.erandibordados.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
}
