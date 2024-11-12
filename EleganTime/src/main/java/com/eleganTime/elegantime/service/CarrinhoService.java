package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.ItemCarrinho;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.repository.CarrinhoRepository;
import com.eleganTime.elegantime.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    // Map para armazenar carrinhos anônimos
    private final Map<String, Carrinho> carrinhosAnonimos = new HashMap<>();

    // Simulando a obtenção de um carrinho logado (não persistido no banco)
    public Optional<Carrinho> obterCarrinhoPorCliente(String emailCliente) {
        Carrinho carrinho = carrinhosAnonimos.get(emailCliente); // Recupera o carrinho do mapa com base no email
        return Optional.ofNullable(carrinho);
    }

    // Método para salvar o carrinho no banco de dados
    public Carrinho salvarCarrinho(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);  // Salva o carrinho no banco de dados
    }


    // Método para obter carrinho em memória (para usuários não logados)
    public Carrinho getCarrinhoEmMemoria(String email) {
        // Verifica se o carrinho do usuário anônimo já existe
        Carrinho carrinho = carrinhosAnonimos.get(email);
        if (carrinho == null) {
            carrinho = new Carrinho();  // Cria um novo carrinho se não encontrado
            carrinhosAnonimos.put(email, carrinho);  // Armazena o carrinho com o email como chave
        }
        return carrinho;
    }

    // Adiciona item ao carrinho (anônimo ou logado)
    public void adicionarItem(int produtoId, int quantidade, String email) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Carrinho carrinho;
        if (email != null) {
            // Para carrinhos anônimos, usamos o email como chave
            carrinho = carrinhosAnonimos.get(email);
            if (carrinho == null) {
                carrinho = new Carrinho();  // Cria um novo carrinho se não existir
                carrinhosAnonimos.put(email, carrinho);  // Armazena o carrinho para o usuário anônimo
            }
        } else {
            carrinho = new Carrinho();  // Para usuários logados, poderia ser buscado no banco
        }

        // Verifica se o item já existe no carrinho
        Optional<ItemCarrinho> itemExistenteOpt = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId_Produto() == produtoId)
                .findFirst();

        if (itemExistenteOpt.isPresent()) {
            ItemCarrinho itemExistente = itemExistenteOpt.get();
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);  // Atualiza a quantidade
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);
            carrinho.getItens().add(novoItem);  // Adiciona o novo item ao carrinho
        }

        // Atualiza o carrinho anônimo no Map
        if (email != null) {
            carrinhosAnonimos.put(email, carrinho);
        }
    }

    // Remove item do carrinho (anônimo ou logado)
    public void removerItem(int produtoId, String email) {
        Carrinho carrinho;
        if (email != null) {
            carrinho = carrinhosAnonimos.get(email);
            if (carrinho == null) {
                carrinho = new Carrinho();  // Cria um carrinho vazio se não encontrar
            }
        } else {
            carrinho = new Carrinho();  // Para usuários logados, deveria buscar no banco de dados
        }

        carrinho.getItens().removeIf(item -> item.getProduto().getId_Produto() == produtoId);
        if (email != null) {
            carrinhosAnonimos.put(email, carrinho);
        }
    }

    // Calcula o total de um carrinho
    public double calcularTotal(Carrinho carrinho) {
        return carrinho.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
    }

    // Obtém o carrinho (anônimo ou logado)
    public Carrinho obterCarrinho(String email) {
        Carrinho carrinho;
        if (email != null) {
            // Para carrinhos anônimos, usamos o email como chave
            carrinho = carrinhosAnonimos.get(email);
            if (carrinho == null) {
                carrinho = new Carrinho();  // Cria um carrinho vazio se não encontrar
            }
        } else {
            carrinho = new Carrinho();  // Aqui você deveria buscar o carrinho no banco de dados
        }
        return carrinho;
    }
}
