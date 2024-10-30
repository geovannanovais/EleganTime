package com.eleganTime.elegantime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eleganTime.elegantime.model.Imagem;
import com.eleganTime.elegantime.repository.ImagemRepository;

import java.util.List;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public Imagem salvar(Imagem imagem) {
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
}