package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.service.UsuarioService;

import jakarta.validation.Valid;

import com.eleganTime.elegantime.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.salvarUsuario(usuario));
    }

    /**
     * Edita um usuário existente.
     * 
     * @param usuario o usuário com as alterações.
     * @return o usuário atualizado.
     */
    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(200).body(usuarioService.editUsuario(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> validarSenha(@Valid @RequestBody Usuario usuario) {
        Boolean valid = usuarioService.validarSenha(usuario);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(200).build();
    }

    /**
     * Trata exceções de validação de argumentos de método.
     *
     * Este método é invocado automaticamente quando ocorre uma exceção
     * do tipo MethodArgumentNotValidException. Ele retorna um mapa com
     * os campos inválidos e suas respectivas mensagens de erro.
     *
     * @param ex a exceção de validação de argumentos do método.
     * @return um mapa contendo os nomes dos campos inválidos como chave
     *         e as mensagens de erro correspondentes como valor.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Cria um mapa para armazenar os erros de validação, onde a chave será o nome
        // do campo
        // e o valor será a mensagem de erro correspondente.
        Map<String, String> errors = new HashMap<>();

        // Percorre todos os erros encontrados na validação
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // Obtém o nome do campo que falhou na validação
            String fieldName = ((FieldError) error).getField();

            // Obtém a mensagem de erro associada ao campo
            String errorMessage = error.getDefaultMessage();

            // Adiciona o campo e a mensagem de erro ao mapa de erros
            errors.put(fieldName, errorMessage);
        });

        // Retorna o mapa com todos os erros de validação
        return errors;
    }

}
