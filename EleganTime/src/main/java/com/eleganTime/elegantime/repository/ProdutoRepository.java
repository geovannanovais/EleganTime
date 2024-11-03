package com.eleganTime.elegantime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eleganTime.elegantime.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}