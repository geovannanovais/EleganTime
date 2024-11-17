package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.model.Imagem;
import com.eleganTime.elegantime.service.ImagemService;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        } else {
            model.addAttribute("errorMessage", "Produto não encontrado com id: " + id);
            model.addAttribute("produto", new Produto());
        }
        return "cadastroProduto";
    }

    @PostMapping("/cadastrarProduto")
    public String cadastrarProduto(@ModelAttribute Produto produto,
            @RequestParam("imagensNome") String[] imagensNome, // pega o nome
            @RequestParam("imagensCaminho") String[] imagensCaminho, // pega o caminho
            @RequestParam(value = "imagensPrincipal", required = false) boolean[] imagensPrincipal) { // pega se é
                                                                                                      // principal

        produtoService.salvar(produto);

        List<Imagem> novasImagens = new ArrayList<>();
        for (int i = 0; i < imagensNome.length; i++) {
            if (!imagensNome[i].isEmpty() && !imagensCaminho[i].isEmpty()) {
                Imagem img = new Imagem(imagensNome[i], imagensCaminho[i], imagensPrincipal[i], produto);
                imagemService.salvar(img);
                novasImagens.add(img);
            }
        }

        produto.setImagens(novasImagens);

        return "redirect:/listaProduto";
    }

    @PostMapping("/editarProduto/{id}")
    public String editarProdutoPost(@PathVariable int id, @ModelAttribute Produto produto,
            @RequestParam("imagensNome") String[] imagensNome,
            @RequestParam("imagensCaminho") String[] imagensCaminho,
            @RequestParam(value = "imagensPrincipal", required = false) boolean[] imagensPrincipal) {

        Produto produtoExistente = produtoService.buscarProdutoPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setAvaliacao(produto.getAvaliacao());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque());
        produtoExistente.setCondicaoDoProduto(produto.isCondicaoDoProduto());

        produtoService.salvar(produtoExistente);

        System.out.println("--------------- Imagem:" + produtoExistente.getImagens());

        List<Imagem> novasImagens = new ArrayList<>();
        for (int i = 0; i < imagensNome.length; i++) {
            if (!imagensNome[i].isEmpty() && !imagensCaminho[i].isEmpty()) {
                Imagem img = new Imagem(imagensNome[i], imagensCaminho[i], imagensPrincipal[i], produtoExistente);
                imagemService.salvar(img);
                novasImagens.add(img);
            }
        }

        System.out.println("--------------- Imagem:" + produtoExistente.getImagens());

        produtoExistente.setImagens(novasImagens);

        return "redirect:/listaProduto";
    }
}