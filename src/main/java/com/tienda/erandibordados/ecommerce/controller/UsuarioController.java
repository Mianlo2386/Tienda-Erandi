package com.tienda.erandibordados.ecommerce.controller;

import com.tienda.erandibordados.ecommerce.model.TipoUsuario;
import com.tienda.erandibordados.ecommerce.model.Usuario;
import com.tienda.erandibordados.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }
    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("Usuario: {}",usuario);
        usuario.setTipo(TipoUsuario.valueOf("USUARIO"));
        usuarioService.save(usuario);
        return "redirect:/";
    }
}
