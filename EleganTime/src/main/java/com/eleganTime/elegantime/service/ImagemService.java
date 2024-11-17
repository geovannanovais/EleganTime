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

    public Imagem salvar(Imagem novaImagem) {
        if (novaImagem.getProduto() == null || novaImagem.getProduto().getId_Produto() == 0) {
            throw new IllegalArgumentException("Produto não pode ser nulo ou inválido.");
        }

        // Verifica se a nova imagem é marcada como principal
        if (novaImagem.isPrincipal()) {
            // Busca a imagem principal atual do produto
            Imagem imagemPrincipalAtual = imagemRepository.findByProdutoAndPrincipalTrue(novaImagem.getProduto());

            // Define a imagem atual como não principal, se existir
            if (imagemPrincipalAtual != null) {
                imagemPrincipalAtual.setPrincipal(false);
                imagemRepository.save(imagemPrincipalAtual);
            }
        }

        // Verifica se o caminho e o nome da nova imagem foram preenchidos
        if (novaImagem.getCaminho() == null || novaImagem.getCaminho().isEmpty()) {
            throw new IllegalArgumentException("O caminho da imagem não pode ser vazio.");
        }
        if (novaImagem.getNome() == null || novaImagem.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome da imagem não pode ser vazio.");
        }

        // Salva a nova imagem
        return imagemRepository.save(novaImagem);
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
