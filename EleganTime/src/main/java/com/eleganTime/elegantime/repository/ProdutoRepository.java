package com.eleganTime.elegantime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eleganTime.elegantime.model.Produto;
import java.util.List;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByCondicaoDoProduto(boolean condicaoDoProduto);
}