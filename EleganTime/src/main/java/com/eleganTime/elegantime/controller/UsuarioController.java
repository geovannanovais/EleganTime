package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.service.UsuarioService;
import com.eleganTime.elegantime.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios") // Define uma rota padrão para os GetMapping
public class UsuarioController {

    @Autowired

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Lista todos os usuários.
     *
     * @return uma lista de todos os usuários.
     */
    @GetMapping()
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        List<Usuario> lista = usuarioService.listarUsuarios();
        return ResponseEntity.status(200).body(lista);
    }

    /**
     * Cria um novo usuário.
     *
     * @param usuario o usuário a ser criado.
     * @return o usuário criado.
     */
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.salvarUsuario(usuario));
    }

    /**
     * Edita um usuário existente.
     *
     * @param usuario o usuário com as alterações.
     * @return o usuário atualizado.
     */
    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(200).body(usuarioService.editUsuario(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> validarSenha(@RequestBody Usuario usuario) {
        Boolean valid = usuarioService.validarSenha(usuario);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(200).build();
    }
}
