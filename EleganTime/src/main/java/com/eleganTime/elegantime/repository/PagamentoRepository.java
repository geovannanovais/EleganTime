package com.eleganTime.elegantime.repository;

import com.eleganTime.elegantime.model.Pagamento;
import com.eleganTime.elegantime.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    // Buscar pagamento por cliente
    Optional<Pagamento> findByCliente(Cliente cliente);

    // Buscar pagamentos do cliente ordenados por idPagamento de forma decrescente
    Optional<List<Pagamento>> findByClienteOrderByIdPagamentoDesc(Cliente cliente);

}
