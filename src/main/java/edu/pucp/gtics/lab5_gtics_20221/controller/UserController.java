package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.Juegos;
import edu.pucp.gtics.lab5_gtics_20221.entity.User;
import edu.pucp.gtics.lab5_gtics_20221.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/signIn")
    public String signIn() {
        return "user/signIn";
    }


    @GetMapping("/signInRedirect")
    public String signInRedirect(Authentication auth, RedirectAttributes attributes, HttpSession session) {
        String role = "";
        for (GrantedAuthority authority : auth.getAuthorities()) {
            role = authority.getAuthority();
            break;
        }

        User usuario = userRepository.findByCorreo(auth.getName());
        session.setAttribute("usuario", usuario);

        if (role.equals("ADMIN")) {
            return "redirect:/juegos/lista";
        } else { // USER
            // Carrito y ncarrito se inicializan al iniciar la sesión de usuario, a no ser que ya exista
            if (session.getAttribute("carrito") == null) {
                ArrayList<Juegos> carrito = new ArrayList<>();
                session.setAttribute("carrito", carrito);
                session.setAttribute("ncarrito", carrito.size());
            }
            return "redirect:/vista";
        }

    }

}