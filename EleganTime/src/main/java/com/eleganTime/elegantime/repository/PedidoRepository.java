package com.eleganTime.elegantime.repository;

import com.eleganTime.elegantime.model.Pedido;
import com.eleganTime.elegantime.model.ItemPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByIdCliente(int idCliente);

    List<Pedido> findAllByOrderByDataCriacaoDesc();

    List<Pedido> findByIdClienteOrderByDataCriacaoDesc(int idCliente);






}