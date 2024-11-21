package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    // Regex para validar nome completo (deve ter pelo menos duas palavras, com mínimo de 3 letras em cada)
    private final Pattern nomePattern = Pattern.compile("^[A-Za-zÁ-ÿà-ÿ]+(?:\\s[A-Za-zÁ-ÿà-ÿ]+)+$");

    public Cliente salvar(Cliente cliente) {
        // Log de depuração para imprimir o objeto 'cliente'
        logger.info("Tentando salvar o cliente: {}", cliente);
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    private void validarCliente(Cliente cliente) {
        // Valida o e-mail
        logger.info("Validando cliente com email: {}", cliente.getEmail());
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistente.isPresent()) {
            logger.error("Email já cadastrado: {}", cliente.getEmail());
            throw new RuntimeException("Email já cadastrado.");
        }

        // Valida o CPF
        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            logger.error("CPF já cadastrado: {}", cliente.getCpf());
            throw new RuntimeException("CPF já cadastrado.");
        }

        if (!isValidCPF(cliente.getCpf())) {
            logger.error("CPF inválido: {}", cliente.getCpf());
            throw new RuntimeException("CPF inválido.");
        }

        // Valida o nome completo
        if (!nomePattern.matcher(cliente.getNome()).matches()) {
            logger.error("Nome inválido: {}", cliente.getNome());
            throw new RuntimeException("O nome deve ter pelo menos duas palavras, com mínimo de 3 letras em cada.");
        }
    }

    private boolean isValidCPF(String cpf) {
        // Lógica de validação do CPF
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;
        if (digito1 != Integer.parseInt(String.valueOf(cpf.charAt(9)))) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;
        return digito2 == Integer.parseInt(String.valueOf(cpf.charAt(10)));
    }

    public Cliente autenticarCliente(String email, String senha) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            logger.info("Autenticando cliente com email: {}", cliente.getEmail());

            if (cliente.getSenha().equals(senha)) {
                return cliente;
            } else {
                logger.error("Senha incorreta para o email: {}", email);
                throw new RuntimeException("Senha incorreta!");
            }
        } else {
            logger.error("Cliente não encontrado com o email: {}", email);
            throw new RuntimeException("Cliente não encontrado com o email: " + email);
        }
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        logger.info("Buscando cliente por ID: {} - Cliente encontrado: {}", id, cliente);
        return cliente;
    }

    public Cliente buscarPorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);
        logger.info("Buscando cliente por email: {} - Cliente encontrado: {}", email, cliente);
        return cliente;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        logger.info("Listando todos os clientes: {}", clientes);
        return clientes;
    }

    public Cliente atualizarCliente(int id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cliente.getCpf());
            clienteExistente.setEmail(cliente.getEmail());
            logger.info("Atualizando cliente: {}", clienteExistente);
            return clienteRepository.save(clienteExistente);
        } else {
            logger.error("Cliente não encontrado para atualização, ID: {}", id);
            throw new RuntimeException("Cliente não encontrado para atualização");
        }
    }

    public void deletarCliente(int id) {
        clienteRepository.deleteById(id);
        logger.info("Cliente deletado com ID: {}", id);
    }
}
