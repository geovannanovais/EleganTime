package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.ItemPedido;
import com.eleganTime.elegantime.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public ItemPedido salvarItemPedido(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);  // Salva o item do pedido no banco
    }
}