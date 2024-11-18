package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.model.ItemCarrinho;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.repository.CarrinhoRepository;
import com.eleganTime.elegantime.repository.ClienteRepository;
import com.eleganTime.elegantime.repository.ProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    // Método para adicionar item ao carrinho (com persistência)
    public void adicionarItem(int produtoId, int quantidade, String email, HttpSession session) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Carrinho carrinho = obterCarrinho(email, session);  // Recupera o carrinho (logado ou anônimo)

        // Verifica se o item já existe no carrinho
        Optional<ItemCarrinho> itemExistenteOpt = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId_Produto() == produtoId)
                .findFirst();

        if (itemExistenteOpt.isPresent()) {
            ItemCarrinho itemExistente = itemExistenteOpt.get();
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);  // Atualiza a quantidade
        } else {
            // Cria um novo item e associa o carrinho
            ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);  // Instancia sem o Carrinho
            novoItem.setCarrinho(carrinho);  // Agora associa o carrinho ao item
            carrinho.adicionarItem(novoItem);
        }

        // Salva o carrinho no banco de dados se o usuário estiver logado
        if (email != null) {
            salvarCarrinho(carrinho);  // Chama o método para salvar o carrinho no banco de dados
        } else {
            // Caso contrário, salva o carrinho na sessão para usuários anônimos
            session.setAttribute("carrinho", carrinho);
        }
    }

    // Método para remover item do carrinho (com persistência)
    public void removerItem(int produtoId, String email, HttpSession session) {
        Carrinho carrinho = obterCarrinho(email, session);  // Recupera o carrinho

        // Remove o item com o ID fornecido
        carrinho.removerItem(produtoId);

        // Salva o carrinho atualizado no banco se o usuário estiver logado
        if (email != null) {
            salvarCarrinho(carrinho);  // Chama o método para salvar o carrinho no banco de dados
        } else {
            // Caso contrário, salva o carrinho na sessão para usuários anônimos
            session.setAttribute("carrinho", carrinho);
        }
    }

    public void calcularTotal(Carrinho carrinho) {
        double total = 0.0;

        // Calculando o total dos itens do carrinho
        for (ItemCarrinho item : carrinho.getItens()) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }

        // Adiciona o valor do frete ao total
        if (carrinho.getValorFrete() > 0) {
            total += carrinho.getValorFrete();
        }

        // Atualiza o valor total do carrinho
        carrinho.setValorTotal(total);
    }

    // Método para salvar ou atualizar o carrinho no banco de dados
    public void salvarCarrinho(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }

    public Carrinho obterCarrinho(String email, HttpSession session) {
        Carrinho carrinho;

        if (email != null) {
            // Se o usuário está logado, tentamos recuperar o carrinho do banco de dados
            carrinho = carrinhoRepository.findByCliente_Email(email).orElseGet(() -> {
                // Verifica se o cliente existe, se não, cria um novo carrinho
                Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorEmail(email));
                Cliente cliente = clienteOpt.orElse(null);  // ou trate adequadamente o cliente nulo

                Carrinho novoCarrinho = new Carrinho();
                novoCarrinho.setCliente(cliente);  // Se o cliente for nulo, o carrinho será associado sem cliente
                carrinhoRepository.save(novoCarrinho);  // Salva o novo carrinho no banco
                return novoCarrinho;
            });
        } else {
            // Para usuários anônimos, buscamos o carrinho na sessão
            carrinho = (Carrinho) session.getAttribute("carrinho");

            // Se o carrinho não existe, cria um novo e coloca na sessão
            if (carrinho == null) {
                carrinho = new Carrinho();  // Cria um carrinho vazio se não existir
                session.setAttribute("carrinho", carrinho);  // Armazena na sessão
            }
        }

        return carrinho;
    }

    public Carrinho buscarPorId(int idCarrinho) {
        return carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

}
