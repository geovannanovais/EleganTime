package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ClienteService clienteService;

    // Página de login
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha incorretos!");
        }
        return "login";  // A página de login (login.html)
    }

    // Página de acesso negado (quando o usuário não tem permissão para acessar algo)
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";  // A página de acesso negado (accessDenied.html)
    }

    // Método para associar o carrinho à sessão do usuário logado
    @GetMapping("/login/success")
    public String loginSuccess(HttpSession session, Authentication authentication) {
        String email = authentication.getName();
        // Recupera o carrinho da sessão
        Carrinho carrinhoSessao = (Carrinho) session.getAttribute("carrinho");
        ClienteService clienteService = new ClienteService();
        if (carrinhoSessao != null) {
            // Se o carrinho existir na sessão, associamos ao usuário logado
            carrinhoSessao.setCliente(clienteService.buscarPorEmail(email)); // Aqui você associaria o cliente corretamente
            carrinhoService.salvarCarrinho(carrinhoSessao); // Salva o carrinho no banco de dados
            session.removeAttribute("carrinho"); // Remove o carrinho da sessão após salvar
        }

        return "redirect:/areaCliente";
    }
}
