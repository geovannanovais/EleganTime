package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.FormaPagamento;
import com.eleganTime.elegantime.model.ItemCarrinho;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ClienteService;
import com.eleganTime.elegantime.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ResumoPedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/resumoPedido")
    public String resumoPedido(@RequestParam("clienteId") int clienteId, Model model) {
        // Recupera o cliente pelo ID
        Cliente cliente = clienteService.buscarPorId(clienteId);

        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        // Recupera o carrinho do cliente
        Carrinho carrinho = carrinhoService.obterCarrinhoPorCliente(clienteId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Lista de produtos no carrinho
        List<ItemCarrinho> itensCarrinho = carrinho.getItens();

        // Calcula o total do carrinho
        double totalGeral = carrinhoService.calcularTotal(carrinho.getIdCarrinho());

        // Calcula o frete
        double frete = calcularFrete(totalGeral);

        // Adiciona o valor do frete ao total geral
        totalGeral += frete;

        // Busca a forma de pagamento associada ao cliente (se existirem, ou podemos ajustar conforme a lógica)
        FormaPagamento formaPagamento = new FormaPagamento(); // Aqui você poderia buscar a forma de pagamento

        // Prepara o modelo com as informações
        model.addAttribute("cliente", cliente);
        model.addAttribute("itens", itensCarrinho);
        model.addAttribute("totalGeral", totalGeral);
        model.addAttribute("frete", frete);
        model.addAttribute("enderecoEntrega", cliente.getEnderecoEntrega());
        model.addAttribute("formaPagamento", formaPagamento);

        return "resumoPedido";
    }

    private double calcularFrete(double totalGeral) {
        // Lógica para calcular o frete
        if (totalGeral < 100) {
            return 10.0; // Frete fixo para pedidos abaixo de 100
        } else {
            return 0.0; // Frete grátis para pedidos acima de 100
        }
    }
}
