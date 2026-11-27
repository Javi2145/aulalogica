package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Usuario;
import ec.edu.uta.aulalogica.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Intro 3D, registro, inicio de sesión, invitado y salida. */
@Controller
public class AccesoController {
    private final UsuarioService usuarios;

    public AccesoController(UsuarioService usuarios) {
        this.usuarios = usuarios;
    }

    @GetMapping("/")
    public String intro() {
        return "index";
    }

    @GetMapping("/acceso")
    public String acceso(@RequestParam(defaultValue = "reg") String modo, Model model) {
        model.addAttribute("modo", "log".equals(modo) ? "log" : "reg");
        model.addAttribute("mensaje", "");
        return "acceso";
    }

    @PostMapping("/acceso")
    public String enviar(@RequestParam String modo,
                         @RequestParam(defaultValue = "") String nombre,
                         @RequestParam(defaultValue = "") String correo,
                         @RequestParam(defaultValue = "") String clave,
                         HttpSession sesion, Model model) {
        boolean registro = "reg".equals(modo);
        String error = registro ? usuarios.validarRegistro(nombre, correo, clave)
                                : usuarios.validarIngreso(correo, clave);
        if (error != null) {
            model.addAttribute("modo", registro ? "reg" : "log");
            model.addAttribute("mensaje", error);
            model.addAttribute("nombre", nombre);
            model.addAttribute("correo", correo);
            return "acceso";
        }
        Usuario u = registro ? usuarios.registrar(nombre, correo, clave) : usuarios.ingresar(correo);
        sesion.setAttribute(SesionUtil.CLAVE, u);
        return "redirect:/contenidos";
    }

    @PostMapping("/invitado")
    public String invitado(HttpSession sesion) {
        sesion.setAttribute(SesionUtil.CLAVE, usuarios.invitado());
        return "redirect:/contenidos";
    }

    @PostMapping("/salir")
    public String salir(HttpSession sesion) {
        sesion.invalidate();
        return "redirect:/acceso?modo=log";
    }
}
