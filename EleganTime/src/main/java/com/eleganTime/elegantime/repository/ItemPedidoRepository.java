package com.eleganTime.elegantime.repository;

import com.eleganTime.elegantime.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findByPedido_Id(int idPedido);  // Alterado para 'pedido.id'


}