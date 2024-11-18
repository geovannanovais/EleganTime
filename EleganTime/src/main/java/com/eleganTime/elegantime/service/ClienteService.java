package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
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
        logger.info("Tentando salvar o cliente: {}", cliente);  // Mostra o objeto cliente completo
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    private void validarCliente(Cliente cliente) {
        // Log para verificar o email sendo validado
        logger.info("Validando cliente com email: {}", cliente.getEmail());  // Imprime o email sendo validado

        // Verifica se o email já existe
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistente.isPresent()) {
            logger.error("Email já cadastrado: {}", clienteExistente.get().getEmail());  // Loga o email já cadastrado
            throw new RuntimeException("Email já cadastrado.");
        }

        // Verifica se o CPF já existe
        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            logger.error("CPF já cadastrado: {}", cliente.getCpf());  // Imprime o CPF já existente
            throw new RuntimeException("CPF já cadastrado.");
        }

        // Valida o CPF
        if (!isValidCPF(cliente.getCpf())) {
            logger.error("CPF inválido: {}", cliente.getCpf());  // Imprime CPF inválido
            throw new RuntimeException("CPF inválido.");
        }

        // Valida o nome completo (deve ter pelo menos 2 palavras, com no mínimo 3 letras em cada palavra)
        if (!nomePattern.matcher(cliente.getNome()).matches()) {
            logger.error("Nome inválido: {}", cliente.getNome());  // Imprime o nome que falhou na validação
            throw new RuntimeException("O nome deve ter pelo menos duas palavras, com mínimo de 3 letras em cada.");
        }

        // Valida o endereço de faturamento
        if (!validarEndereco(cliente)) {
            logger.error("Endereço de faturamento incompleto para: {}", cliente.getNome());  // Imprime nome de cliente com endereço incompleto
            throw new RuntimeException("Endereço de faturamento incompleto.");
        }

        // Valida o CEP
        if (!validarCep(cliente.getCep())) {
            logger.error("CEP inválido para o cliente {}: {}", cliente.getNome(), cliente.getCep());  // Imprime CEP inválido
            throw new RuntimeException("CEP inválido.");
        }
    }

    // Método para validar o CPF
    private boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false; // CPF precisa ter 11 dígitos
        }

        // Verifica se todos os números são iguais (ex: 111.111.111-11)
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999")) {
            return false; // CPF com números sequenciais iguais é inválido
        }

        // Validação do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;
        if (digito1 != Integer.parseInt(String.valueOf(cpf.charAt(9)))) {
            return false; // O primeiro dígito verificador não corresponde
        }

        // Validação do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;
        return digito2 == Integer.parseInt(String.valueOf(cpf.charAt(10))); // O segundo dígito verificador não corresponde
    }

    private boolean validarEndereco(Cliente cliente) {
        // Verifica se o endereço completo foi informado
        return cliente.getLogradouro() != null && cliente.getNumero() != null &&
                cliente.getBairro() != null && cliente.getCidade() != null &&
                cliente.getUf() != null;
    }

    private boolean validarCep(String cep) {
        // Valida se o CEP informado existe
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
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);

        // Verifica se o cliente foi encontrado
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            logger.info("Autenticando cliente com email: {}", cliente.getEmail());  // Loga o cliente que está sendo autenticado

            // Verifica a senha
            if (cliente.getSenha().equals(senha)) {
                return cliente;
            } else {
                logger.error("Senha incorreta para o email: {}", email);  // Senha incorreta
                throw new RuntimeException("Senha incorreta!");
            }
        } else {
            logger.error("Cliente não encontrado com o email: {}", email);  // Cliente não encontrado
            throw new RuntimeException("Cliente não encontrado com o email: " + email);
        }
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        logger.info("Buscando cliente por ID: {} - Cliente encontrado: {}", id, cliente);  // Mostra o cliente encontrado ou nulo
        return cliente;
    }

    public Cliente buscarPorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);
        logger.info("Buscando cliente por email: {} - Cliente encontrado: {}", email, cliente);  // Mostra o cliente encontrado ou nulo
        return cliente;
    }


    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        logger.info("Listando todos os clientes: {}", clientes);  // Imprime a lista de clientes
        return clientes;
    }

    public Cliente atualizarCliente(int id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cliente.getCpf());
            clienteExistente.setEmail(cliente.getEmail());
            logger.info("Atualizando cliente: {}", clienteExistente);  // Imprime o cliente atualizado
            return clienteRepository.save(clienteExistente);
        } else {
            logger.error("Cliente não encontrado para atualização, ID: {}", id);  // Cliente não encontrado para atualização
            throw new RuntimeException("Cliente não encontrado para atualização");
        }
    }

    public void deletarCliente(int id) {
        clienteRepository.deleteById(id);
        logger.info("Cliente deletado com ID: {}", id);  // Imprime quando o cliente é deletado
    }
}