package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Pagamento;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    // Buscar pagamento por cliente
    public Pagamento buscarPagamentoPorCliente(Cliente cliente) {
        Optional<Pagamento> pagamento = pagamentoRepository.findByCliente(cliente);
        return pagamento.orElse(null);  // Retorna null se não houver pagamento
    }

    // Processar pagamento (validações de exemplo)
    public Pagamento processarPagamento(Pagamento pagamento) {
        if (pagamento.getTipoPagamento() == null || pagamento.getTipoPagamento().isEmpty()) {
            throw new IllegalArgumentException("Forma de pagamento não selecionada");
        }

        if ("cartao".equalsIgnoreCase(pagamento.getTipoPagamento())) {
            // Validação fictícia do número de cartão (verificando o comprimento)
            if (pagamento.getNumeroCartao() == null || pagamento.getNumeroCartao().length() != 16) {
                throw new IllegalArgumentException("Número do cartão inválido");
            }

            // Outras validações de cartão (como código CVV, validade, etc) podem ser adicionadas aqui
        }

        // Salvar o pagamento no banco de dados
        return pagamentoRepository.save(pagamento);
    }

    // Buscar pagamento pelo ID
    public Pagamento buscarPagamentoPorId(Integer idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElse(null);  // Retorna null se o pagamento não for encontrado
    }


    public Pagamento buscarPagamentoPorCliente(int idCliente) {
        // Obtém todos os pagamentos do cliente, ordenados por ID (ou data)
        List<Pagamento> pagamentos = pagamentoRepository.findByCliente_IdOrderByIdPagamentoDesc(idCliente);

        // Verifica se a lista de pagamentos está vazia
        if (pagamentos.isEmpty() || pagamentos.get(0).getIdPagamento() == 0) {
            System.out.println("NAO BUSCOU DIREITO DEU ERRO!");// Retorna null se não houver pagamento ou se for inválido
            return null;
        }

        System.out.println("Pagamentos: "+pagamentos.get(0).getIdPagamento());
        return pagamentos.get(0);
    }


}
