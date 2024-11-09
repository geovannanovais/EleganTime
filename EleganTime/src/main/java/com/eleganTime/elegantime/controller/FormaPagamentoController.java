package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.FormaPagamento;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class FormaPagamentoController {

    // Método GET para exibir o formulário de pagamento
    @GetMapping("/pagamento")
    public String exibirFormularioPagamento(Model model) {
        // Adiciona o objeto FormaPagamento vazio ao modelo para o formulário
        model.addAttribute("pagamentoForm", new FormaPagamento());
        return "formaPagamento";  // Exibe o formulário
    }

    // Método POST para processar o pagamento
    @PostMapping("/processarPagamento")
    public String processarPagamento(@ModelAttribute("pagamentoForm") FormaPagamento pagamento, Model model) {
        // Verifica se o nome do titular do cartão foi preenchido
        if (pagamento.getNome() == null || pagamento.getNome().isEmpty()) {
            model.addAttribute("error", "Nome do titular do cartão é obrigatório.");
            return "formaPagamento";  // Se o nome não for preenchido, retorna para o formulário com erro
        }

        // Adiciona o pagamento completo ao modelo (se os dados estiverem corretos)
        model.addAttribute("pagamento", pagamento);
        return "resumoPedido";  // Exibe a página de resumo do pedido
    }
}
