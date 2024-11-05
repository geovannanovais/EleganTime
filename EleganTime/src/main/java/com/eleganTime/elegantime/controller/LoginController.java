package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.service.UsuarioService;
import com.eleganTime.elegantime.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha incorretos!");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {

        Usuario usuario = usuarioService.autenticarUsuario(email, password);

        if (usuario != null) {

            HttpSession session = request.getSession();
            session.setAttribute("usuarioId", usuario.getIdUsuario());

            if ("administrador".equalsIgnoreCase(usuario.getGrupo())) {
                return "redirect:/areaAdmin";
            } else if ("estoquista".equalsIgnoreCase(usuario.getGrupo())) {
                return "redirect:/areaEstoquista";
            } else {
                return "redirect:/login?error";
            }
        } else {
            Cliente cliente = clienteService.autenticarCliente(email, password);

            if (cliente != null) {
                HttpSession session = request.getSession();
                session.setAttribute("clienteId", cliente.getIdCliente());
                return "redirect:/areaUsuario";
            } else {
                return "redirect:/login?error";
            }
        }
    }
}
