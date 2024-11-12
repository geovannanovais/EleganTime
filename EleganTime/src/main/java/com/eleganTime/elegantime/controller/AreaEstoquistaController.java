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
public class AreaEstoquistaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/areaEstoquista")
    public String areaEstoquista(Model model) {

        // Obtém o usuário autenticado do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Nome de usuário (email, por exemplo)

        if (username == null || username.isEmpty()) {
            return "redirect:/login";  // Redireciona para o login se o usuário não estiver autenticado
        }

        // Busca o usuário no banco de dados utilizando o nome de usuário (email)
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorEmail(username);

        if (optionalUsuario.isEmpty()) {
            return "redirect:/login";  // Se o usuário não for encontrado, redireciona para o login
        }

        Usuario usuario = optionalUsuario.get();

        // Verifica se o usuário tem a role 'estoquista'
        if (!usuario.getGrupo().equals("estoquista")) {
            return "redirect:/login";  // Redireciona para login se o usuário não for do grupo Estoquista
        }

        // Adiciona o objeto usuario ao modelo para renderizar na view
        model.addAttribute("usuario", usuario);
        return "areaEstoquista";  // Nome da view (página) a ser retornada
    }
}
