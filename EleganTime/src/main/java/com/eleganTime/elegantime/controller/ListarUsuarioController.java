package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListarUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listarUsuarios")
    public String listarUsuarios(Model model) {
        // Recupera o Authentication do SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // Redireciona se o usuário não estiver autenticado
        }

        // Recupera o usuário autenticado, mas agora sem tentar fazer o cast para Usuario
        User user = (User) authentication.getPrincipal(); // O user aqui é o Spring Security User, que é uma instância de org.springframework.security.core.userdetails.User

        // Se o usuário for admin, ele pode ver a lista de usuários
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuario"; // Retorna a página da lista de usuários
    }

    @PostMapping("/alterarStatusUsuario")
    public String alterarStatusUsuario(@RequestParam("id") int id) {
        // Recupera o Authentication do SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // Redireciona se o usuário não estiver autenticado
        }

        // Busca o usuário pelo ID
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario != null) {
            // Inverte o status do usuário
            usuario.setCondicaoDoUsuario(!usuario.isCondicaoDoUsuario());
            usuarioService.salvar(usuario); // Salva as alterações
        }

        return "redirect:/listarUsuarios"; // Redireciona para a lista de usuários
    }
}
