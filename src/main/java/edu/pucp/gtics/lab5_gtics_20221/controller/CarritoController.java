package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.*;
import edu.pucp.gtics.lab5_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    JuegosRepository juegosRepository;

    @Autowired
    JuegosxUsuarioRepository juegosxUsuarioRepository;

    @GetMapping("/lista")
    public String listaCarrito (Model model, HttpSession session) {
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        carrito.sort(Comparator.comparing(Juegos::getPrecio));
        model.addAttribute("listaJuegos", carrito);
        return "carrito/lista";
    }

    @GetMapping("/compra")
    public String comprarCarrito (@RequestParam("id") int id, HttpSession session, RedirectAttributes attr){
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        for (Juegos juego : carrito) {
            juegosxUsuarioRepository.registrarJuegoPorUser(id, juego.getIdjuego()); // la cantidad 1 se añade en el query
        }
        //el carrito se borra una vez se hace la compra
        carrito = new ArrayList<>();
        session.setAttribute("carrito", carrito);
        session.setAttribute("ncarrito", carrito.size());
        attr.addFlashAttribute("msg", "Compra exitosa!");
        return "redirect:/vista";
    }

    @GetMapping("/anadir")
    public String anadirCarrito (@RequestParam("id") int idJuego, HttpSession session, RedirectAttributes attr) {
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        Optional<Juegos> optionalJuego = juegosRepository.findById(idJuego);
        if (optionalJuego.isPresent()) {
            Juegos juegoCarrito = optionalJuego.get();
            carrito.add(juegoCarrito);
        }
        session.setAttribute("carrito", carrito);
        session.setAttribute("ncarrito", carrito.size());
        attr.addFlashAttribute("msg", "Juego añadido al carrito");
        return "redirect:/vista";
    }

    @GetMapping("/borrar")
    public String borrarCarrito (@RequestParam("id") int idJuegoDel, HttpSession session, RedirectAttributes attr) {
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        for (Juegos juegoDel : carrito) {
            if (juegoDel.getIdjuego() == idJuegoDel) {
                carrito.remove(juegoDel);
                break;
            }
        }
        session.setAttribute("carrito", carrito);
        session.setAttribute("ncarrito", carrito.size());
        attr.addFlashAttribute("msg", "Juego eliminado del carrito");
        return "redirect:/carrito/lista";
    }

}
