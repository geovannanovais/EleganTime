package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.model.Imagem;
import com.eleganTime.elegantime.service.ImagemService;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CadastrarProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImagemService imagemService;

    @GetMapping("/cadastrarProduto")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastroProduto";
    }

    @GetMapping("/editarProduto/{id}")
    public String editarProduto(@PathVariable int id, Model model) {
        Optional<Produto> produtoOpt = produtoService.buscarProdutoPorId(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            model.addAttribute("produto", produto);
            model.addAttribute("imagens", produto.getImagens());  // Passa as imagens do produto para o template
        } else {
            model.addAttribute("errorMessage", "Produto não encontrado com id: " + id);
            model.addAttribute("produto", new Produto());
        }
        return "cadastroProduto";
    }

    @PostMapping("/cadastrarProduto")
    public String cadastrarProduto(@ModelAttribute Produto produto,
                                   @RequestParam("imagensUpload") MultipartFile[] imagensUpload,  // Recebe os arquivos de imagem
                                   @RequestParam(value = "imagensPrincipal", required = false) boolean[] imagensPrincipal) {

        produtoService.salvar(produto);  // Salva o produto

        // Salva as imagens
        for (int i = 0; i < imagensUpload.length; i++) {
            if (!imagensUpload[i].isEmpty()) {
                String caminhoImagem = "/img/" + imagensUpload[i].getOriginalFilename();
                Imagem img = new Imagem(imagensUpload[i].getOriginalFilename(), caminhoImagem,
                        imagensPrincipal != null && imagensPrincipal.length > i && imagensPrincipal[i], produto);
                imagemService.salvar(img);  // Salva a imagem no banco de dados
            }
        }

        return "redirect:/listaProduto";
    }

    @PostMapping("/editarProduto/{id}")
    public String editarProdutoPost(@PathVariable int id,
                                    @ModelAttribute Produto produto,
                                    @RequestParam("imagensUpload") MultipartFile[] imagensUpload,
                                    @RequestParam(value = "imagensPrincipal", required = false) boolean[] imagensPrincipal) {

        // Recupera o produto existente no banco de dados
        Produto produtoExistente = produtoService.buscarProdutoPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));

        // Atualiza os dados do produto
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setAvaliacao(produto.getAvaliacao());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque());
        produtoExistente.setCondicaoDoProduto(produto.isCondicaoDoProduto());
        produtoService.salvar(produtoExistente);

        // Desmarcando todas as imagens como principal antes de salvar a nova imagem principal
        if (imagensPrincipal != null && imagensPrincipal.length > 0) {
            for (Imagem img : produtoExistente.getImagens()) {
                img.setPrincipal(false); // Desmarca todas as imagens existentes como principal
                imagemService.salvar(img); // Salva as alterações
            }
        }

        // Processa as novas imagens e define se elas são principais
        for (int i = 0; i < imagensUpload.length; i++) {
            if (!imagensUpload[i].isEmpty()) {
                String caminhoImagem = "/img/" + imagensUpload[i].getOriginalFilename();
                boolean principal = (imagensPrincipal != null && imagensPrincipal.length > i && imagensPrincipal[i]);
                Imagem img = new Imagem(imagensUpload[i].getOriginalFilename(), caminhoImagem, principal, produtoExistente);
                imagemService.salvar(img); // Salva a imagem no banco
            }
        }

        return "redirect:/listaProduto"; // Redireciona após salvar
    }

}

