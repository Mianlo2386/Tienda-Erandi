package com.tienda.erandibordados.ecommerce.controller;

import com.tienda.erandibordados.ecommerce.model.Producto;
import com.tienda.erandibordados.ecommerce.repository.ProductoRepository;
import com.tienda.erandibordados.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService productoService;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Long id, Model model) {
        log.info("Id del producto enviado como parámetro: {}", id);
        Producto producto = productoService.findById(id).orElse(null);
        if (producto == null) {
            return "redirect:/"; // Redirigir a la página de inicio si el producto no existe
        }
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

}
