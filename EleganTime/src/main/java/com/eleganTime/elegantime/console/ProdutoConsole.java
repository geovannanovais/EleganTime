package com.eleganTime.elegantime.console;

import com.eleganTime.elegantime.controller.ProdutoController;
import com.eleganTime.elegantime.model.Imagem;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ProdutoConsole {

    private final ProdutoController produtoController;
    private final ImagemService imagemService;
    private final Scanner scanner = new Scanner(System.in);
    private final LoginConsole loginConsole;

    @Autowired
    public ProdutoConsole(ProdutoController produtoController, ImagemService imagemService, LoginConsole loginConsole) {
        this.produtoController = produtoController;
        this.imagemService = imagemService;
        this.loginConsole = loginConsole;
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("==== Menu de Produtos ====");
            System.out.println("1. Listar Produtos");
            if (isEstoquista() || isAdmin()) {
                System.out.println("2. Adicionar Produto");
                System.out.println("3. Atualizar Produto");
                System.out.println("4. Deletar Produto");
                System.out.println("5. Gerenciar Imagens do Produto"); // Nova opção
            }
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    listarProdutos();
                    break;
                case 2:
                    if (isEstoquista() || isAdmin()) {
                        adicionarProduto();
                    } else {
                        System.out.println("Você não tem permissão para adicionar produtos.");
                    }
                    break;
                case 3:
                    if (isEstoquista() || isAdmin()) {
                        atualizarProduto();
                    } else {
                        System.out.println("Você não tem permissão para atualizar produtos.");
                    }
                    break;
                case 4:
                    if (isEstoquista() || isAdmin()) {
                        deletarProduto();
                    } else {
                        System.out.println("Você não tem permissão para deletar produtos.");
                    }
                    break;
                case 5:
                    if (isEstoquista() || isAdmin()) {
                        gerenciarImagensProduto();
                    } else {
                        System.out.println("Você não tem permissão para gerenciar imagens.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void gerenciarImagensProduto() {
        System.out.print("ID do produto: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine();

        Optional<Produto> produtoOpt = produtoController.buscarProdutoPorId(idProduto);
        if (produtoOpt.isEmpty()) {
            System.out.println("Produto não encontrado.");
            return;
        }

        Produto produto = produtoOpt.get();

        while (true) {
            System.out.println("==== Gerenciar Imagens do Produto: " + produto.getNome() + " ====");
            System.out.println("1. Listar Imagens");
            System.out.println("2. Adicionar Imagem");
            System.out.println("3. Editar Imagem");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    listarImagens(produto);
                    break;
                case 2:
                    adicionarImagem(produto);
                    break;
                case 3:
                    editarImagem(produto);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void listarImagens(Produto produto) {
        List<Imagem> imagens = imagemService.listarImagens();
        if (imagens.isEmpty()) {
            System.out.println("Nenhuma imagem encontrada para o produto " + produto.getNome());
            return;
        }
        System.out.printf("%-10s %-30s %-20s %-10s%n", "ID", "Nome", "Caminho", "Principal");
        for (Imagem imagem : imagens) {
            if (imagem.getProduto().getId_Produto() == produto.getId_Produto()) {
                System.out.printf("%-10d %-30s %-20s %-10s%n",
                        imagem.getId(), imagem.getNome(), imagem.getCaminho(), imagem.isPrincipal() ? "Sim" : "Não");
            }
        }
    }

    private void adicionarImagem(Produto produto) {
        Imagem imagem = new Imagem();
        System.out.print("Nome da imagem: ");
        imagem.setNome(scanner.nextLine());

        System.out.print("Caminho da imagem: ");
        imagem.setCaminho(scanner.nextLine());

        System.out.print("É a imagem principal? (true/false): ");
        boolean isPrincipal = scanner.nextBoolean();
        scanner.nextLine();


        if (isPrincipal) {
            List<Imagem> imagens = imagemService.listarImagens();
            for (Imagem img : imagens) {
                if (img.getProduto().getId_Produto() == produto.getId_Produto() && img.isPrincipal()) {
                    img.setPrincipal(false);
                    imagemService.salvar(img);
                    break;
                }
            }
        }

        imagem.setPrincipal(isPrincipal);
        imagem.setProduto(produto);
        imagemService.salvar(imagem);
        System.out.println("Imagem adicionada com sucesso!");
    }


    private void editarImagem(Produto produto) {
        System.out.print("ID da imagem a ser editada: ");
        int idImagem = scanner.nextInt();
        scanner.nextLine();
        Imagem imagem = imagemService.buscarImagemPorId(idImagem);
        if (imagem == null || imagem.getProduto().getId_Produto() != produto.getId_Produto()) {
            System.out.println("Imagem não encontrada ou não pertence ao produto.");
            return;
        }


        System.out.println("Editando imagem: " + imagem.getNome());
        System.out.print("Novo Nome (atual: " + imagem.getNome() + "): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isEmpty()) imagem.setNome(novoNome);

        System.out.print("Novo Caminho (atual: " + imagem.getCaminho() + "): ");
        String novoCaminho = scanner.nextLine();
        if (!novoCaminho.isEmpty()) imagem.setCaminho(novoCaminho);

        System.out.print("É a imagem principal? (true/false, atual: " + imagem.isPrincipal() + "): ");
        boolean novaCondicao = scanner.nextBoolean();
        scanner.nextLine();


        if (novaCondicao && !imagem.isPrincipal()) {

            List<Imagem> imagens = imagemService.listarImagens();
            for (Imagem img : imagens) {
                if (img.getProduto().getId_Produto() == produto.getId_Produto() && img.isPrincipal()) {
                    img.setPrincipal(false);
                    imagemService.salvar(img);
                    break;
                }
            }
        }

        imagem.setPrincipal(novaCondicao);
        imagemService.salvar(imagem);
        System.out.println("Imagem atualizada com sucesso!");
    }

    private boolean isEstoquista() {
        return "Estoquista".equalsIgnoreCase(loginConsole.getUsuarioAutenticado().getGrupo());
    }

    private boolean isAdmin() {
        return "Administrador".equalsIgnoreCase(loginConsole.getUsuarioAutenticado().getGrupo());
    }

    private void listarProdutos() {
        List<Produto> produtos = produtoController.listarProdutosInterno();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        System.out.printf("%-10s %-30s %-10s %-15s %-20s %-10s%n", "ID", "Nome", "Avaliação", "Preço", "Qtde Estoque", "Status");
        System.out.println("=".repeat(100));
        for (Produto produto : produtos) {
            System.out.printf("%-10d %-30s %-10.1f %-15.2f %-20d %-10s%n",
                    produto.getId_Produto(), produto.getNome(), produto.getAvaliacao(), produto.getPreco(), produto.getQuantidadeEmEstoque(), produto.isCondicaoDoProduto() ? "Ativo" : "Inativo");
        }
    }

    private void adicionarProduto() {
        Produto produto = new Produto();
        System.out.print("Nome: ");
        produto.setNome(scanner.nextLine());

        System.out.print("Avaliação (1 a 5): ");
        produto.setAvaliacao(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Descrição: ");
        produto.setDescricao(scanner.nextLine());

        System.out.print("Preço: ");
        produto.setPreco(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Quantidade em Estoque: ");
        produto.setQuantidadeEmEstoque(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Condição do Produto (true/false): ");
        produto.setCondicaoDoProduto(scanner.nextBoolean());
        scanner.nextLine();

        produtoController.salvar(produto);
        System.out.println("Produto adicionado com sucesso!");
    }

    private void atualizarProduto() {
        System.out.print("ID do produto a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Produto> produtoOpt = produtoController.buscarProdutoPorId(id);
        if (produtoOpt.isEmpty()) {
            System.out.println("Produto não encontrado.");
            return;
        }

        Produto produto = produtoOpt.get();
        System.out.println("Atualizando produto: " + produto.getNome());

        System.out.print("Novo Nome (atual: " + produto.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) produto.setNome(nome);

        System.out.print("Nova Avaliação (atual: " + produto.getAvaliacao() + "): ");
        double avaliacao = scanner.nextDouble();
        produto.setAvaliacao(avaliacao);
        scanner.nextLine();

        System.out.print("Nova Descrição (atual: " + produto.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        if (!descricao.isEmpty()) produto.setDescricao(descricao);

        System.out.print("Novo Preço (atual: " + produto.getPreco() + "): ");
        double preco = scanner.nextDouble();
        produto.setPreco(preco);
        scanner.nextLine();

        System.out.print("Nova Quantidade em Estoque (atual: " + produto.getQuantidadeEmEstoque() + "): ");
        int quantidade = scanner.nextInt();
        produto.setQuantidadeEmEstoque(quantidade);
        scanner.nextLine();

        System.out.print("Nova Condição do Produto (true/false): ");
        produto.setCondicaoDoProduto(scanner.nextBoolean());
        scanner.nextLine();

        produtoController.atualizarProdutoInterno(id, produto);
        System.out.println("Produto atualizado com sucesso!");
    }

    private void deletarProduto() {
        System.out.print("ID do produto a ser deletado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        produtoController.deletarProdutoInterno(id);
        System.out.println("Produto deletado com sucesso!");
    }
}
