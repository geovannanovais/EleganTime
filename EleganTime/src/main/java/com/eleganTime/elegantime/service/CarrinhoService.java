package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.ItemCarrinho;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.repository.CarrinhoRepository;
import com.eleganTime.elegantime.repository.ItemCarrinhoRepository;
import com.eleganTime.elegantime.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço para o Carrinho de Compras
 */
@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;  // Repositório do Carrinho

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;  // Repositório dos itens do Carrinho

    @Autowired
    private ProdutoRepository produtoRepository;  // Repositório de Produto


    public void salvarCarrinho(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }

    /**
     * Adiciona um item ao carrinho e persiste no banco.
     *
     * @param carrinhoId  o ID do carrinho
     * @param produtoId   o ID do produto a ser adicionado
     * @param quantidade  a quantidade do produto a ser adicionada
     * @throws RuntimeException se o produto ou carrinho não forem encontrados
     */
    public void adicionarItem(int carrinhoId, int produtoId, int quantidade) {
        // Busca o carrinho pelo ID
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Busca o produto no banco de dados
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o item já existe no carrinho
        Optional<ItemCarrinho> itemExistenteOpt = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto);

        if (itemExistenteOpt.isPresent()) {
            // Se o item já existe, atualiza a quantidade
            ItemCarrinho itemExistente = itemExistenteOpt.get();
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            itemCarrinhoRepository.save(itemExistente);  // Atualiza no banco
        } else {
            // Se não existe, cria um novo item no carrinho
            ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);
            novoItem.setCarrinho(carrinho);  // Relaciona com o carrinho
            itemCarrinhoRepository.save(novoItem);  // Salva o item no banco
        }
    }

    /**
     * Remove um item do carrinho.
     *
     * @param carrinhoId o ID do carrinho
     * @param produtoId  o ID do produto a ser removido
     */
    public void removerItem(int carrinhoId, int produtoId) {
        // Busca o carrinho pelo ID
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Busca o produto no banco de dados pelo ID
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Busca o item do carrinho com o produto e carrinho específicos
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto)
                .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho"));

        // Se a quantidade do item for maior que 1, diminui a quantidade
        if (itemCarrinho.getQuantidade() > 1) {
            itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
            itemCarrinhoRepository.save(itemCarrinho);  // Salva a nova quantidade no banco de dados
        } else {
            // Se a quantidade for 1, remove o item completamente
            itemCarrinhoRepository.delete(itemCarrinho);
        }
    }

    /**
     * Calcula o valor total do carrinho.
     *
     * @param carrinhoId o ID do carrinho
     * @return o valor total do carrinho
     */
    public double calcularTotal(int carrinhoId) {
        // Busca o carrinho pelo ID
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Calcula o total com base nos itens do carrinho
        return carrinho.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
    }

    /**
     * Obtém o carrinho pelo ID.
     *
     * @param carrinhoId o ID do carrinho
     * @return o carrinho
     */
    public Carrinho obterCarrinho(int carrinhoId) {
        return carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    public Optional<Carrinho> obterCarrinhoPorCliente(int clienteId) {
        return carrinhoRepository.findByClienteId(clienteId);
    }

}
