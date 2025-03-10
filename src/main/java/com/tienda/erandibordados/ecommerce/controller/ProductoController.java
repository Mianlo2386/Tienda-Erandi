package com.tienda.erandibordados.ecommerce.controller;


import com.tienda.erandibordados.ecommerce.model.Producto;
import com.tienda.erandibordados.ecommerce.model.TipoUsuario;
import com.tienda.erandibordados.ecommerce.model.Usuario;
import com.tienda.erandibordados.ecommerce.service.IUsuarioService;
import com.tienda.erandibordados.ecommerce.service.ProductoService;
import com.tienda.erandibordados.ecommerce.service.UploadFileService;
import com.tienda.erandibordados.ecommerce.service.UsuarioServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    @Autowired
    private ProductoService productoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private UploadFileService uploadFileService;

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
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario usuario  = usuarioService.findById(Long.parseLong(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(usuario);
        //imagen
        if (producto.getId()==null){ //cuando se crea un producto
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }else{

        }

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
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p = new Producto();
        p=productoService.get(producto.getId()).get();

        if (file.isEmpty()){ //edición de producto sin cambiar la imagen
            producto.setImagen(p.getImagen());
        }else { //cuando se edita tambien la imagen

            //eliminar cuando no haya imagen por defecto
            if (!p.getImagen().equals("default.jpg")){
                uploadFileService.deleteImage(p.getImagen());
            }
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        Producto p = new Producto();
        p=productoService.get(id).get();

        //eliminar cuando no haya imagen por defecto
        if (!p.getImagen().equals("default.jpg")){
            uploadFileService.deleteImage(p.getImagen());
        }

        productoService.delete(id);
        return "redirect:/productos";
    }


}
