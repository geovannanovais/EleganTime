package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Endereco;
import com.eleganTime.elegantime.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service  // A anotação @Service para que o Spring reconheça o serviço
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Salva o endereço
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Valida se o endereço está completo (todos os campos obrigatórios)
    public boolean validarEndereco(Endereco endereco) {
        return endereco.getLogradouro() != null && endereco.getNumero() != null &&
                endereco.getBairro() != null && endereco.getCidade() != null &&
                endereco.getUf() != null;
    }

    // Valida o CEP utilizando a API ViaCEP
    public boolean validarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();  // Cria uma nova instância do RestTemplate
        try {
            // Faz a requisição para a API ViaCEP
            Map<String, String> response = restTemplate.getForObject(url, Map.class);
            // Verifica se a resposta é válida
            return response != null && response.get("erro") == null;
        } catch (Exception e) {
            // Se ocorrer qualquer erro na requisição, o CEP é considerado inválido
            return false;
        }
    }

    // Busca o endereço pelo ID
    public Endereco buscarPorId(int id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElse(null);  // Retorna o endereço ou null se não encontrado
    }

    // Atualiza um endereço existente
    public void atualizarEndereco(int idEndereco, Endereco endereco) {
        endereco.setId(idEndereco);  // Atualiza o ID do endereço
        enderecoRepository.save(endereco);   // Salva as alterações
    }

    // Lista todos os endereços cadastrados
    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();  // Retorna todos os endereços cadastrados
    }

    public List<Endereco> buscarEnderecosPorCliente(int clienteId) {
        return enderecoRepository.findByClienteId(clienteId);
    }

    public void marcarEnderecoComoPrincipal(int enderecoId, int clienteId) {
        // Desmarcar todos os endereços como principal
        List<Endereco> enderecos = enderecoRepository.findByClienteIdAndPrincipalTrue(clienteId);
        for (Endereco e : enderecos) {
            e.setPrincipal(false);
            enderecoRepository.save(e);
        }

        // Marcar o endereço selecionado como principal
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        endereco.setPrincipal(true);
        enderecoRepository.save(endereco);
    }



}
