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

        // Tenta autenticar como usuário (admin/estoquista)
        Usuario usuario = usuarioService.autenticarUsuario(email, password);

        // Caso o usuário seja autenticado
        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", usuario.getIdUsuario()); // Armazena como userId
            if ("administrador".equalsIgnoreCase(usuario.getGrupo())) {
                return "redirect:/areaAdmin";  // Redireciona para área de admin
            } else if ("estoquista".equalsIgnoreCase(usuario.getGrupo())) {
                return "redirect:/areaEstoquista";  // Redireciona para área de estoquista
            }
        }

        // Caso o usuário não seja encontrado, tenta autenticar como cliente
        Cliente cliente = clienteService.autenticarCliente(email, password);

        if (cliente != null) {
            // Passa o clienteId na URL
            return "redirect:/home?clienteId=" + cliente.getIdCliente();  // Redireciona para a home com clienteId na URL
        }

        // Se nenhum dos dois for autenticado, retorna para a página de login com erro
        return "redirect:/login?error";
    }
}