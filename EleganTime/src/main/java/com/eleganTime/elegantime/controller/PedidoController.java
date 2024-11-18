package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.model.ItemCarrinho;
import com.eleganTime.elegantime.model.Pagamento;
import com.eleganTime.elegantime.model.Pedido;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ClienteService;
import com.eleganTime.elegantime.service.PagamentoService;
import com.eleganTime.elegantime.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PedidoController {

    private final CarrinhoService carrinhoService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;

    public PedidoController(CarrinhoService carrinhoService, ClienteService clienteService, PedidoService pedidoService, PagamentoService pagamentoService) {
        this.carrinhoService = carrinhoService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/finalizarPedido")
    public String finalizarPedido(
            HttpSession session,
            Integer idPagamento) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // Obtém o e-mail do cliente logado
        Cliente cliente = clienteService.buscarPorEmail(email);

        // Recupera o carrinho do cliente
        Carrinho carrinho = carrinhoService.obterCarrinho(email, session);

        // Cria o pedido com os dados do carrinho e do cliente (usando somente IDs)
        Pedido pedido = new Pedido();
        pedido.setIdCliente(cliente.getIdCliente());  // Usa o ID do cliente
        pedido.setIdCarrinho(carrinho.getIdCarrinho());  // Usa o ID do carrinho
        pedido.setIdPagamento(idPagamento != null ? idPagamento : null);  // Usa o ID do pagamento (pode ser nulo)
        pedido.setStatus("Pendente");  // Status padrão para o pedido

        // Salva o pedido no banco de dados
        pedidoService.salvarPedido(pedido);

        return "redirect:/compraConcluida";  // Página de confirmação de compra
    }
}

