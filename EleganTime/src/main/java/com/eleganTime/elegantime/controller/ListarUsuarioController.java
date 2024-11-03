package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListarUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listarUsuarios")
    public String listarUsuarios(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return "redirect:/login";
        }

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuario";
    }

    @PostMapping("/alterarStatusUsuario")
    public String alterarStatusUsuario(@RequestParam("id") int id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario != null) {
            // Inverte o status do usuário
            usuario.setCondicaoDoUsuario(!usuario.isCondicaoDoUsuario());
            usuarioService.salvar(usuario); // Salva as alterações
        }

        return "redirect:/listarUsuarios"; // Redireciona para a lista de usuários
    }

}
