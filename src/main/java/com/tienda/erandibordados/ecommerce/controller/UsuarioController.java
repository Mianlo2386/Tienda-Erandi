package com.tienda.erandibordados.ecommerce.controller;

import com.tienda.erandibordados.ecommerce.model.DetalleOrden;
import com.tienda.erandibordados.ecommerce.model.Orden;
import com.tienda.erandibordados.ecommerce.model.TipoUsuario;
import com.tienda.erandibordados.ecommerce.model.Usuario;
import com.tienda.erandibordados.ecommerce.service.IOrdenService;
import com.tienda.erandibordados.ecommerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IOrdenService ordenService;

    @GetMapping("/registro")
    public String create() {
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario) {
        logger.info("Usuario: {}", usuario);
        usuario.setTipo(TipoUsuario.USUARIO);
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        logger.info("Accesos: {}", usuario);

        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        if (user.isPresent()) {
            logger.info("Tipo de usuario de BD: {}", user.get().getTipo());
            session.setAttribute("idusuario", user.get().getId());

            if (user.get().getTipo() == TipoUsuario.ADMINISTRADOR) {
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        } else {
            logger.info("Usuario no existe.");
            return "redirect:/usuario/login";
        }
    }
    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        model.addAttribute("session", session.getAttribute("idusuario"));
        Usuario usuario = usuarioService.findById(Long.parseLong(session
                .getAttribute("idusuario").toString())).get();
        List<Orden> ordenes = ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);

        return "usuario/compras";
    }
    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Long id, HttpSession session, Model model){
        logger.info("Id de la orden: {}", id);
        Optional<Orden> orden = ordenService.findById(id);

        if (orden.isPresent()) {
            Orden ordenEncontrada = orden.get();
            logger.info("Orden encontrada: {}", ordenEncontrada);
            List<DetalleOrden> detalles = ordenEncontrada.getDetalleOrden();
            logger.info("Detalles de la orden: {}", detalles);

            model.addAttribute("detalles", detalles);
        } else {
            logger.error("Orden no encontrada con ID: {}", id);
            return "redirect:/usuario/error"; // Página de error o manejo adecuado
        }

        model.addAttribute("session", session.getAttribute("idusuario"));

        return "usuario/detallecompra";
    }
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){
        session.removeAttribute("idusuario");

        return "redirect:/";
    }

}
