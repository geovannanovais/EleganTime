package com.eleganTime.elegantime.repository;

import com.eleganTime.elegantime.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    // Buscar endereços de um cliente
    List<Endereco> findByClienteId(int clienteId);

    // Atualizar o status de principal para os endereços de um cliente
    @Modifying
    @Query("UPDATE Endereco e SET e.principal = :status WHERE e.cliente.id = :clienteId")
    void updatePrincipalStatus(int clienteId, boolean status);

    List<Endereco> findByClienteIdAndPrincipalTrue(int clienteId);

}
