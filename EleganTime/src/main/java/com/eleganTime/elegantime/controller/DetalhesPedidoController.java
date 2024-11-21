package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.*;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ClienteService;
import com.eleganTime.elegantime.service.PedidoService;
import com.eleganTime.elegantime.service.PagamentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DetalhesPedidoController {

    private final CarrinhoService carrinhoService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;

    public DetalhesPedidoController(CarrinhoService carrinhoService, ClienteService clienteService, PedidoService pedidoService, PagamentoService pagamentoService) {
        this.carrinhoService = carrinhoService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/detalhesPedido")
    public String mostrarDetalhesPedido(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // Obtém o e-mail do cliente logado
        Cliente cliente = clienteService.buscarPorEmail(email);

        // Recupera o carrinho do cliente
        Carrinho carrinho = carrinhoService.obterCarrinho(email, session);

        // Recupera o pagamento do cliente
        Pagamento pagamento = pagamentoService.buscarPagamentoPorCliente(cliente.getIdCliente());

        // Verifica se o pagamento existe
        if (pagamento == null || pagamento.getIdPagamento() == 0) {
            model.addAttribute("erro", "Pagamento não encontrado para o cliente.");
            return "erro";  // Página de erro caso não tenha um pagamento válido
        }

        // Calcula o valor do frete
        double frete = carrinho.getValorFrete();  // Se o carrinho tem o valor do frete calculado

        // O valor total do carrinho já está calculado e armazenado no carrinho
        double totalGeral = carrinho.getValorTotal() + frete;  // Adiciona o valor do frete ao total

        // Buscar o endereço principal do cliente
        Endereco enderecoPrincipal = cliente.getEnderecos().stream()
                .filter(Endereco::isPrincipal) // Filtra o endereço principal
                .findFirst() // Pega o primeiro endereço que for principal
                .orElse(null);

        String enderecoString = (enderecoPrincipal != null) ? enderecoPrincipal.toString() : "Endereço principal não encontrado.";


        // Passa os dados para a view
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("cliente", cliente);
        model.addAttribute("itens", carrinho.getItens());
        model.addAttribute("totalGeral", totalGeral);  // Total com o frete
        model.addAttribute("frete", frete);  // Passa o valor do frete para a view
        model.addAttribute("formaPagamento", pagamento);  // Passa as informações de pagamento
        model.addAttribute("enderecoEntrega",enderecoString);  // Passa o endereço principal para a view

        return "detalhesPedido";
    }


    @GetMapping("/detalhesPedido/{idPedido}")
    public String mostrarDetalhesPedidoPorId(@PathVariable("idPedido") int idPedido, Model model) {

        Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);

        // Verifica se o pedido existe
        if (pedido == null) {
            model.addAttribute("erro", "Pedido não encontrado.");
            return "erro";  // Retorna a página de erro caso o pedido não exista
        }

        // Busca o cliente que fez o pedido
        Cliente cliente = clienteService.buscarPorId(pedido.getIdCliente());

        // Verifica se o cliente existe
        if (cliente == null) {
            model.addAttribute("erro", "Cliente não encontrado.");
            return "erro";  // Retorna a página de erro caso o cliente não exista
        }

        // Recupera o carrinho do pedido
        Carrinho carrinho = carrinhoService.buscarPorId(pedido.getIdCarrinho());

        // Verifica se o carrinho existe
        if (carrinho == null) {
            model.addAttribute("erro", "Carrinho não encontrado.");
            return "erro";  // Retorna a página de erro caso o carrinho não exista
        }

        // Recupera o pagamento relacionado ao pedido
        Pagamento pagamento = pagamentoService.buscarPagamentoPorId(pedido.getIdPagamento());

        // Verifica se o pagamento existe
        if (pagamento == null) {
            model.addAttribute("erro", "Pagamento não encontrado.");
            return "erro";  // Retorna a página de erro caso o pagamento não exista
        }

        // Calcula o valor total do pedido, incluindo o frete
        double frete = carrinho.getValorFrete();  // Se o carrinho tem o valor do frete calculado
        double totalGeral = pedido.getTotal()-pedido.getFrete();

        String enderecoString = pedido.getEnderecoEntrega(); // Total do pedido já inclui o frete

        // Passa os dados para a view
        model.addAttribute("pedido", pedido);
        model.addAttribute("itens", carrinho.getItens());  // Itens do carrinho
        model.addAttribute("totalGeral", totalGeral);  // Total do pedido
        model.addAttribute("frete", frete);  // Valor do frete
        model.addAttribute("cliente", cliente);  // Dados do cliente
        model.addAttribute("carrinho", carrinho);  // Dados do carrinho
        model.addAttribute("formaPagamento", pagamento);  // Dados do pagamento
        model.addAttribute("enderecoEntrega", enderecoString);

        return "detalhesPedido";  // Retorna a view com os detalhes do pedido
    }





    @PostMapping("/finalizarPedido")
    public String finalizarPedido(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // Obtém o e-mail do cliente logado
        Cliente cliente = clienteService.buscarPorEmail(email);

        // Recupera o carrinho do cliente
        Carrinho carrinho = carrinhoService.obterCarrinho(email, session);

        // Recupera o pagamento do cliente
        Pagamento pagamento = pagamentoService.buscarPagamentoPorCliente(cliente.getIdCliente());

        // Verifica se o pagamento existe
        if (pagamento == null || pagamento.getIdPagamento() == 0) {
            model.addAttribute("erro", "Pagamento não encontrado para o cliente.");
            return "erro";  // Página de erro caso não tenha um pagamento válido
        }

        // Calcula o valor do frete
        double frete = carrinho.getValorFrete();  // Se o carrinho tem o valor do frete calculado

        // O valor total do carrinho já está calculado e armazenado no carrinho
        double totalGeral = carrinho.getValorTotal() + frete;  // Adiciona o valor do frete ao total

        // Buscar o endereço principal do cliente
        Endereco enderecoEntrega = cliente.getEnderecos().stream()
                .filter(Endereco::isPrincipal) // Filtra o endereço principal
                .findFirst() // Pega o primeiro endereço que for principal
                .orElse(null);  // Caso não encontre, retorna null

        // Verifica se o cliente tem um endereço principal
        if (enderecoEntrega == null) {
            model.addAttribute("erro", "Endereço principal não encontrado para o cliente.");
            return "erro";  // Página de erro caso não tenha um endereço principal
        }

        // Cria o pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(cliente.getIdCliente());
        pedido.setIdCarrinho(carrinho.getIdCarrinho());
        pedido.setIdPagamento(pagamento.getIdPagamento());
        pedido.setStatus("Aguardando Pagamento");
        pedido.setEnderecoEntrega(enderecoEntrega.toString()); // Atribui o endereço principal como String
        pedido.setFrete(frete);
        pedido.setTotal(totalGeral);

        int numeroPedido = gerarNumeroPedido();  // Método para gerar número sequencial
        pedido.setNumeroPedido(numeroPedido);

        // Obtém os itens do carrinho
        List<ItemCarrinho> itensCarrinho = carrinho.getItens();  // Obtém a lista de itens do carrinho

        // Salva o pedido e seus itens
        Pedido pedidoSalvo = pedidoService.salvarPedido(pedido, itensCarrinho);

        // Agora, esvaziar o carrinho do cliente após finalizar o pedido
        carrinhoService.limparCarrinho(cliente.getIdCliente());

        // Atualiza a sessão para refletir o carrinho vazio
        session.setAttribute("carrinho", null);  // Limpa o carrinho da sessão

        // Passa os dados para a view
        model.addAttribute("pedido", pedidoSalvo);  // Passa o pedido salvo
        model.addAttribute("itens", itensCarrinho);  // Passa os itens do carrinho
        model.addAttribute("numeroPedido", pedidoSalvo.getNumeroPedido());  // Passa o número do pedido
        model.addAttribute("totalGeral", totalGeral);  // Passa o total atualizado para a view
        model.addAttribute("frete", frete);  // Passa o valor do frete para a view
        model.addAttribute("cliente", cliente);  // Passa as informações do cliente
        model.addAttribute("carrinho", carrinho);  // Passa o carrinho do cliente
        model.addAttribute("formaPagamento", pagamento);  // Passa as informações de pagamento
        model.addAttribute("sucesso", "Pedido criado com sucesso!"); // Passa a variável de sucesso

        return "redirect:/pedidos";  // Redireciona para a lista de pedidos
    }




    // Método para gerar o número sequencial do pedido
    private int gerarNumeroPedido() {
        // Gerar um número sequencial, por exemplo, com base na data e hora ou um contador
        int numeroPedido = (int) (System.currentTimeMillis() % 100000);  // Exemplo simples baseado no tempo atual
        return numeroPedido;
    }
}

