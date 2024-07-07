package com.tienda.erandibordados.ecommerce.controller;


import com.tienda.erandibordados.ecommerce.model.Producto;
import com.tienda.erandibordados.ecommerce.model.TipoUsuario;
import com.tienda.erandibordados.ecommerce.model.Usuario;
import com.tienda.erandibordados.ecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }
    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario usuario  = new Usuario(
                1L,                           // id
                "Miguel Lopez",                 // nombre
                "Mianlo",                  // username
                "mianlo@example.com",      // email
                null,                         // direccion
                "555-1234",                   // telefono
                TipoUsuario.ADMINISTRADOR,    // tipo
                "claveSegura123",             // clave
                null,                         // productos
                null                          // ordenes
                );
        producto.setUsuario(usuario);
        productoService.save(producto);
        return "redirect:/productos";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Producto> optionalProducto = productoService.get(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            model.addAttribute("producto", producto);
            LOGGER.info("Producto buscado: {}", producto);
            return "productos/edit";
        } else {
            // Manejar el caso en que el producto no se encuentre (opcional)
            return "redirect:/productos";
        }
    }
    @PostMapping("/update")
    public String update(Producto producto){
        productoService.update(producto);
        return "redirect:/productos";
    }


}
