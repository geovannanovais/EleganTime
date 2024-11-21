package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AreaClienteController {

    @Autowired
    private ClienteService clienteService;

    // Exibe a área do cliente com suas informações
    @GetMapping("/areaCliente")
    public String areaCliente(Model model) {
        // Obtém o nome de usuário da sessão (do Spring Security ou outro sistema de autenticação)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca o cliente no banco pelo username (pode ser email ou outro identificador único)
        Cliente cliente = clienteService.buscarPorEmail(username);

        if (cliente != null) {
            // Passa as informações do cliente para a view Thymeleaf
            model.addAttribute("cliente", cliente);  // Usando o nome 'cliente' aqui
            return "areaCliente"; // Nome da página Thymeleaf
        } else {
            model.addAttribute("errorMessage", "Cliente não encontrado.");
            return "login"; // Redireciona para a página de login em caso de erro
        }
    }


}
