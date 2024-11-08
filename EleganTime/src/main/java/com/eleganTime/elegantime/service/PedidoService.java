package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Pedido;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    public Pedido getPedidoAtual() {
        Pedido pedido = new Pedido();
        // Definir produtos, frete, total geral, endereço, forma de pagamento, etc.
        return pedido;
    }
}
