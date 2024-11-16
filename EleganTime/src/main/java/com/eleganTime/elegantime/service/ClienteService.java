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
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

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
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        // CPF inválido se todos os dígitos são iguais (exemplo: 11111111111)
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Valida os dois dígitos verificadores
        int[] peso1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int primeiroDigito = calcularDigito(cpf.substring(0, 9), peso1);
        int segundoDigito = calcularDigito(cpf.substring(0, 9) + primeiroDigito, peso2);

        // Compara com os dígitos verificadores informados
        return cpf.equals(cpf.substring(0, 9) + primeiroDigito + segundoDigito);
    }

    private int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            int digito = Character.getNumericValue(str.charAt(i));
            soma += digito * peso[i];
        }
        int resto = 11 - (soma % 11);
        return (resto > 9) ? 0 : resto;
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

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

}
