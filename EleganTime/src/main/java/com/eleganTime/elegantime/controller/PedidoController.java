package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Pedido;
import com.eleganTime.elegantime.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/resumoPedido")
    public String resumoPedido(Model model) {
        // Carregar o resumo do pedido da sessão ou do banco de dados
        Pedido pedido = pedidoService.getPedidoAtual();

        model.addAttribute("produtos", pedido.getProdutos());
        model.addAttribute("frete", pedido.getFrete());
        model.addAttribute("totalGeral", pedido.getTotalGeral());
        model.addAttribute("enderecoEntrega", pedido.getEnderecoEntrega());
        model.addAttribute("formaPagamento", pedido.getFormaPagamento());

        return "resumoPedido";
    }
}
