package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final Pattern nomePattern = Pattern.compile("^[A-Za-zÁ-ÿà-ÿ]+(?:\\s[A-Za-zÁ-ÿà-ÿ]+)+$");


    public Cliente salvar(Cliente cliente) {
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    private void validarCliente(Cliente cliente) {
        // Verifica se o email já existe
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new RuntimeException("Email já cadastrado.");
        }

        // Verifica se o CPF já existe
        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        // Valida o CPF
        if (!isValidCPF(cliente.getCpf())) {
            throw new RuntimeException("CPF inválido.");
        }

        // Valida o nome completo
        if (!nomePattern.matcher(cliente.getNome()).matches()) {
            throw new RuntimeException("O nome deve ter pelo menos duas palavras, com mínimo de 3 letras em cada.");
        }

        // Valida o endereço de faturamento
        if (!validarEndereco(cliente)) {
            throw new RuntimeException("Endereço de faturamento incompleto.");
        }

        // Valida o CEP
        if (!validarCep(cliente.getCep())) {
            throw new RuntimeException("CEP inválido.");
        }
    }

    private boolean isValidCPF(String cpf) {
        // Implemente sua lógica de validação de CPF aqui
        return true; // Retornar true ou false conforme o CPF seja válido
    }

    private boolean validarEndereco(Cliente cliente) {
        return cliente.getLogradouro() != null && cliente.getNumero() != null &&
                cliente.getBairro() != null && cliente.getCidade() != null &&
                cliente.getUf() != null;
    }

    private boolean validarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, String> response = restTemplate.getForObject(url, Map.class);
            return response != null && response.get("erro") == null;
        } catch (Exception e) {
            return false;
        }
    }

    public Cliente autenticarCliente(String email, String senha) {
        Cliente cliente = clienteRepository.findByEmail(email);

        return cliente;
    }

    public Cliente buscarPorId(int id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente(int id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cliente.getCpf());
            clienteExistente.setEmail(cliente.getEmail());
            return clienteRepository.save(clienteExistente);
        } else {
            throw new RuntimeException("Cliente não encontrado para atualização");
        }
    }

    public void deletarCliente(int id) {
        clienteRepository.deleteById(id);
    }
}
