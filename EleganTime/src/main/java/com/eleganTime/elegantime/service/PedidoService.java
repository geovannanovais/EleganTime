package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.ItemCarrinho;
import com.eleganTime.elegantime.model.ItemPedido;
import com.eleganTime.elegantime.model.Pedido;
import com.eleganTime.elegantime.repository.ItemPedidoRepository;
import com.eleganTime.elegantime.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {


    private final PedidoRepository pedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoService itemPedidoService, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoService = itemPedidoService;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Pedido salvarPedido(Pedido pedido, List<ItemCarrinho> itensCarrinho) {
        // Define a data de criação automaticamente
        pedido.setDataCriacao(new Date());  // Define a data de criação com o timestamp atual

        // Salva o pedido (sem itens ainda)
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Agora, criamos os itens do pedido a partir dos itens do carrinho
        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedidoSalvo);  // Relaciona o item com o pedido
            itemPedido.setProduto(itemCarrinho.getProduto());  // Produto do carrinho
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());  // Quantidade do item
            itemPedido.setValorUnitario(itemCarrinho.getProduto().getPreco());  // Preço do carrinho
            itemPedido.setValorTotal(itemCarrinho.getProduto().getPreco() * itemCarrinho.getQuantidade());  // Total do item

            // Salva o ItemPedido no banco
            itemPedidoService.salvarItemPedido(itemPedido);
        }

        return pedidoSalvo;  // Retorna o pedido já salvo
    }

    // Busca todos os pedidos
    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAllByOrderByDataCriacaoDesc();
    }

    // Busca pedidos por idCliente
    public List<Pedido> buscarPedidosPorIdCliente(int idCliente) {
        return pedidoRepository.findByIdClienteOrderByDataCriacaoDesc(idCliente);
    }

    public Pedido buscarPedidoPorId(int idPedido) {

        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
        return pedido.orElse(null);
    }

    public void atualizarStatus(int pedidoId, String novoStatus) {
        // Busca o pedido pelo ID
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        // Atualiza o status do pedido
        pedido.setStatus(novoStatus);

        // Salva o pedido com o novo status
        pedidoRepository.save(pedido);
    }

    public List<ItemPedido> buscarItensPorPedido(int idPedido) {
        // Retorna todos os itens associados a um pedido
        return itemPedidoRepository.findByPedido_Id(idPedido);  // Chamando o repositório corrigido
    }



}
