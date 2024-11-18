package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Pagamento;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

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

    // Buscar a última forma de pagamento do cliente (o pagamento com o maior ID)
    public Pagamento buscarUltimaFormaPagamento(Cliente cliente) {
        // Consultar a lista de pagamentos do cliente ordenados por idPagamento de forma decrescente
        Optional<List<Pagamento>> pagamentos = pagamentoRepository.findByClienteOrderByIdPagamentoDesc(cliente);
        return pagamentos.filter(p -> !p.isEmpty())  // Verifica se a lista não está vazia
                .map(p -> p.get(0))  // Retorna o primeiro pagamento da lista (mais recente)
                .orElse(null);  // Retorna null se não houver pagamentos
    }

    // Buscar último pagamento por cliente (ou seja, o mais recente)
    public Pagamento buscarUltimoPagamentoPorCliente(Cliente cliente) {
        Optional<List<Pagamento>> pagamentos = pagamentoRepository.findByClienteOrderByIdPagamentoDesc(cliente);
        return pagamentos.filter(p -> !p.isEmpty())
                .map(p -> p.get(0))
                .orElse(null);
    }
}
