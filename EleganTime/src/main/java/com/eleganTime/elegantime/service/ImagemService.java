package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Imagem;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public Imagem salvar(Imagem imagem) {
        if (imagem.getProduto() == null || imagem.getProduto().getId_Produto() == 0) {
            throw new IllegalArgumentException("Produto não pode ser nulo ou inválido.");
        }
        return imagemRepository.save(imagem);
    }

    public List<Imagem> listarImagens() {
        return imagemRepository.findAll();
    }

    public Imagem buscarImagemPorId(int idImagem) {
        return imagemRepository.findById(idImagem).orElse(null);
    }


    public void deletarImagem(int idImagem) {
        imagemRepository.deleteById(idImagem);
    }

    public void deletarImagensPorProduto(Produto produto) {
        if (produto == null || produto.getId_Produto() == 0) {
            throw new IllegalArgumentException("Produto não pode ser nulo ou inválido.");
        }
        List<Imagem> imagens = imagemRepository.findByProduto(produto);
        for (Imagem imagem : imagens) {
            imagemRepository.delete(imagem);
        }
    }
}

