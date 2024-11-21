package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.model.Pagamento;
import com.eleganTime.elegantime.repository.ClienteRepository;
import com.eleganTime.elegantime.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Exibir formulário de pagamento
    @GetMapping("/formaPagamento")
    public String exibirFormularioPagamento(Authentication authentication, Model model) {
        if (authentication == null) {
            throw new RuntimeException("Usuário não autenticado");
        }

        // Obtém o email do usuário autenticado
        String email = authentication.getName();
        if (email == null) {
            throw new RuntimeException("Email do usuário não encontrado");
        }

        // Recupera o cliente autenticado a partir do banco de dados
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Adiciona o cliente e um novo objeto Pagamento ao modelo
        model.addAttribute("cliente", cliente);
        model.addAttribute("pagamentoForm", new Pagamento());

        return "formaPagamento"; // Página de pagamento
    }

    // Processar pagamento - agora usando @ModelAttribute
    @PostMapping("/pagamento")
    public String processarPagamento(Pagamento pagamento, Authentication authentication) {
        // 1. Obtém o email do cliente logado
        String email = authentication != null ? authentication.getName() : null;

        if (email == null) {
            throw new RuntimeException("Cliente não está autenticado.");
        }

        // 2. Busca o cliente no banco de dados pelo email
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // 3. Associa o cliente ao pagamento
        pagamento.setCliente(cliente);

        // 4. Salva o pagamento no banco de dados
        pagamentoRepository.save(pagamento);

        return "redirect:/detalhesPedido";
    }
}
