package com.eleganTime.elegantime.repository;

import com.eleganTime.elegantime.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eleganTime.elegantime.model.Imagem;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

    List<Imagem> findByProduto(Produto produto);

    // Busca a imagem principal de um produto específico
    Imagem findByProdutoAndPrincipalTrue(Produto produto);
}
