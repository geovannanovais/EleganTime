package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    /**
     * Exibe o carrinho de compras do cliente.
     *
     * @param clienteId o ID do cliente
     * @param model o modelo para passar dados para a visão
     * @return a visão do carrinho
     */
    @GetMapping("/carrinho")
    public String verCarrinho(@RequestParam("clienteId") int clienteId, Model model) {
        // Recupera o carrinho do cliente, caso exista
        Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(clienteId);
        Carrinho carrinho = carrinhoOpt.orElseGet(Carrinho::new);

        // Adiciona o carrinho e o total no modelo
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinhoService.calcularTotal(carrinho.getIdCarrinho()));
        model.addAttribute("clienteId", clienteId); // Adiciona o clienteId ao modelo para ser utilizado na visualização

        return "carrinho";  // Retorna a visão do carrinho
    }

    /**
     * Adiciona um item ao carrinho
     *
     * @param produtoId o ID do produto
     * @param quantidade a quantidade do produto
     * @param clienteId o ID do cliente
     * @return redireciona para a página do carrinho
     */
    @PostMapping("/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade,
                                      @RequestParam("clienteId") int clienteId) {
        // Recupera o carrinho do cliente
        Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(clienteId);
        Carrinho carrinho = carrinhoOpt.orElseGet(Carrinho::new);

        // Adiciona o item ao carrinho
        carrinhoService.adicionarItem(carrinho.getIdCarrinho(), produtoId, quantidade);

        // Redireciona para o carrinho do cliente, mantendo o clienteId na URL
        return "redirect:/carrinho?clienteId=" + clienteId;
    }

    /**
     * Remove um item do carrinho
     *
     * @param produtoId o ID do produto
     * @param clienteId o ID do cliente
     * @return redireciona para o carrinho atualizado
     */


    @GetMapping("/carrinho/remover/{produtoId}")
    public String removerUmItem(@PathVariable int produtoId,
                                @RequestParam("clienteId") int clienteId) {
        // Recupera o carrinho do cliente
        Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(clienteId);
        Carrinho carrinho = carrinhoOpt.orElseGet(Carrinho::new);

        // Chama o serviço para remover 1 unidade do produto no carrinho
        carrinhoService.removerItem(carrinho.getIdCarrinho(), produtoId);

        // Redireciona para o carrinho atualizado, mantendo o clienteId na URL
        return "redirect:/carrinho?clienteId=" + clienteId;
    }

}
