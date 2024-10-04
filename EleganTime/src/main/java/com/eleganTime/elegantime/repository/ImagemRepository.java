package com.eleganTime.elegantime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eleganTime.elegantime.model.Imagem;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

}