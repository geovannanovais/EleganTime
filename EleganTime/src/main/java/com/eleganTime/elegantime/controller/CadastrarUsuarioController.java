package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CadastrarUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrarUsuario")
    public String showCadastroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastroUsuario";
    }

    @GetMapping("/editarUsuario/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "cadastroUsuario";
    }

    @PostMapping("/cadastrarUsuario")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario) {

        if (usuario.getIdUsuario() > 0) {
            // Se o ID é maior que zero, redireciona para o método de edição
            return "redirect:/editarUsuario/" + usuario.getIdUsuario();
        }

        usuarioService.salvar(usuario);
        return "redirect:/listarUsuarios";
    }

    @PostMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable int id, @ModelAttribute Usuario usuario) {
        Usuario usuarioExistente = usuarioService.buscarPorId(id);

        if (usuarioExistente != null) {
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setCpf(usuario.getCpf());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setGrupo(usuario.getGrupo());


            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                usuarioExistente.setSenha(usuario.getSenha());
            }

            usuarioExistente.setCondicaoDoUsuario(usuario.isCondicaoDoUsuario());

            usuarioService.atualizarUsuario(usuarioExistente.getIdUsuario(), usuarioExistente);
            return "redirect:/listarUsuarios";
        } else {

            return "redirect:/error";
        }
    }
}