package com.tienda.erandibordados.ecommerce.controller;

import com.tienda.erandibordados.ecommerce.model.DetalleOrden;
import com.tienda.erandibordados.ecommerce.model.Orden;
import com.tienda.erandibordados.ecommerce.model.Producto;
import com.tienda.erandibordados.ecommerce.model.Usuario;
import com.tienda.erandibordados.ecommerce.service.IDetalleOrdenService;
import com.tienda.erandibordados.ecommerce.service.IOrdenService;
import com.tienda.erandibordados.ecommerce.service.IUsuarioService;
import com.tienda.erandibordados.ecommerce.service.ProductoService;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;


    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();// para almacenar detalles de la orden

    Orden orden = new Orden();//datos de la orden

    @GetMapping("")
    public String home(Model model, HttpSession session){
        log.info("Sesion del usuario: {}", session.getAttribute("idusuario") );

        model.addAttribute("productos", productoService.findAll());

        //session
        model.addAttribute("session", session.getAttribute("idusuario"));

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
    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam int cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto:  {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validar para que el producto no se añada dos veces
        Long idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado){
            detalles.add(detalleOrden);
        }


        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }
    //Quitar producto del carrito
    @GetMapping("delete/cart/{id}")
    public String deleteProducto(@PathVariable Long id, Model model){

        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden: detalles){
            if (detalleOrden.getProducto().getId() != id){
                ordenesNueva.add(detalleOrden);
            }
        }
        //crear nueva lista con productos quitados
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);



        return "usuario/carrito";
    }
    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        //session
        model.addAttribute("session", session.getAttribute("idusuario"));
        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session){

        Usuario usuario = usuarioService.findById(Long.parseLong(session.getAttribute("idusuario").toString())).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        return "usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
        try {
            Date fechaCreacion = new Date();
            orden.setFechaCreacion(fechaCreacion);
            orden.setNumero(ordenService.generarNumeroOrden());

            // Obtener usuario desde la sesión
            Long idUsuario = (Long) session.getAttribute("idusuario");
            Usuario usuario = usuarioService.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            orden.setUsuario(usuario);
            ordenService.save(orden);

            for (DetalleOrden detalle : detalles) { // Guardar detalles de la orden
                detalle.setOrden(orden);
                detalleOrdenService.save(detalle);
            }

            // Limpiar orden y detalles
            orden = new Orden();
            detalles.clear();

            return "redirect:/";
        } catch (NumberFormatException | IllegalStateException e) {
            log.error("Error al guardar la orden: {}", e.getMessage());
            // Manejo de errores: redirigir a una página de error o manejar según corresponda
            return "redirect:/error";
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad al guardar la orden: {}", e.getMessage());
            // Manejo de errores de integridad (puedes mostrar un mensaje de error al usuario)
            return "redirect:/error";
        }
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        log.info("Nombre del producto: {}", nombre);
        List<Producto> productos = productoService.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);

        return "usuario/home";
    }


}
