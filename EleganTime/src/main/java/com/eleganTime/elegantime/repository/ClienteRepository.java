package com.eleganTime.elegantime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import com.eleganTime.elegantime.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByEmail(String email);

    Cliente findByEmailAndSenha(String email, String senha);

    Cliente findByCpf(String cpf);
}
