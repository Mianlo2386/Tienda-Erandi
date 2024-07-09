package com.tienda.erandibordados.ecommerce.service;

import com.tienda.erandibordados.ecommerce.model.Usuario;
import com.tienda.erandibordados.ecommerce.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
}
