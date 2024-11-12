package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class AreaAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/areaAdmin")
    public String areaAdmin(Model model) {
        // Obtém o Authentication do SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Recupera o email do usuário (nome de usuário no caso de Spring Security)
        String email = authentication.getName();  // O nome de usuário no Spring Security é o email

        // Verifica se o email está presente
        if (email == null) {
            // Caso o email não esteja presente, redireciona para a página de login
            return "redirect:/login";
        }

        // Busca o usuário completo utilizando o email
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorEmail(email);

        // Verifica se o usuário foi encontrado
        if (optionalUsuario.isEmpty()) {
            // Caso o usuário não exista no banco de dados, redireciona para a página de login
            return "redirect:/login";
        }

        // Se o usuário existe, obtém o objeto Usuario
        Usuario usuario = optionalUsuario.get();

        // Adiciona o usuário completo ao modelo para exibição na página
        model.addAttribute("usuario", usuario);

        // Retorna a página da área administrativa
        return "areaAdmin";
    }
}