package com.eleganTime.elegantime.repository;

import com.eleganTime.elegantime.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {

    Optional<Carrinho> findByClienteId(int clienteId);

    Optional<Carrinho> findByCliente_Email(String email);

    Optional<Carrinho> findById(int id);


}
