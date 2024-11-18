package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Pedido;
import com.eleganTime.elegantime.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido salvarPedido(Pedido pedido) {
        // Salva o pedido, que já possui apenas os IDs das entidades relacionadas
        return pedidoRepository.save(pedido);  // Salva o pedido no banco
    }
}