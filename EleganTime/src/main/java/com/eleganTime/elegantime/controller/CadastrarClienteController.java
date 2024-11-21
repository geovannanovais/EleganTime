package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.model.Endereco;
import com.eleganTime.elegantime.service.ClienteService;
import com.eleganTime.elegantime.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
public class CadastrarClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // Método para validar o formato do CEP sem hífen
    private boolean validarCep(String cep) {
        // Expressão regular para validar o formato do CEP sem hífen (somente 8 dígitos)
        String regex = "^[0-9]{8}$";  // Formato do CEP: 12345678
        return cep.matches(regex);  // Retorna true se o CEP estiver no formato correto
    }

    // Exibe o formulário para cadastro de cliente e seus endereços
    @GetMapping("/cadastrarCliente")
    public String showCadastroForm(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEnderecos(new ArrayList<>());  // Inicializa a lista de endereços vazia

        model.addAttribute("cliente", cliente);  // Passa o cliente com a lista de endereços
        model.addAttribute("enderecos", cliente.getEnderecos());  // Passa a lista de endereços para o formulário
        return "cadastrarCliente";  // Retorna para o formulário de cadastro
    }

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente,
                                   @RequestParam List<String> logradouro,
                                   @RequestParam List<String> numero,
                                   @RequestParam List<String> complemento,
                                   @RequestParam List<String> bairro,
                                   @RequestParam List<String> cidade,
                                   @RequestParam List<String> uf,
                                   @RequestParam List<String> cep,
                                   @RequestParam(value = "principal", required = false) Integer enderecoPrincipal,
                                   Model model) {
        try {
            // Verifica se os campos obrigatórios de endereço foram preenchidos
            if (logradouro.isEmpty() || numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || uf.isEmpty()) {
                model.addAttribute("errorMessage", "Erro: Endereço incompleto.");
                return "cadastrarCliente";  // Retorna para o formulário com mensagem de erro
            }

            // Valida os CEPs
            for (String c : cep) {
                if (!validarCep(c)) {
                    model.addAttribute("errorMessage", "Erro: O CEP " + c + " é inválido.");
                    return "cadastrarCliente";  // Retorna com a mensagem de erro de CEP inválido
                }
            }

            cliente.setCondicaoDoCliente(true);
            clienteService.salvar(cliente);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    cliente.getEmail(), cliente.getSenha());
            var authentication = authenticationManager.authenticate(authToken);

            // Definir o usuário autenticado no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Itera sobre os endereços e salva cada um
            for (int i = 0; i < logradouro.size(); i++) {
                Endereco endereco = new Endereco();
                endereco.setLogradouro(logradouro.get(i));
                endereco.setNumero(numero.get(i));
                endereco.setComplemento(complemento.get(i));
                endereco.setBairro(bairro.get(i));
                endereco.setCidade(cidade.get(i));
                endereco.setUf(uf.get(i));
                endereco.setCep(cep.get(i));
                endereco.setCliente(cliente);  // Associa o endereço ao cliente

                // Define o endereço principala
                if (enderecoPrincipal != null && enderecoPrincipal == i) {
                    endereco.setPrincipal(true);  // Marca este endereço como principal
                } else if (i == 0) {  // Caso tenha apenas um endereço, ele será o principal
                    endereco.setPrincipal(true);
                } else {
                    endereco.setPrincipal(false);  // Marca como não principal
                }

                enderecoService.salvar(endereco);  // Salva o endereço
            }

            return "redirect:/enderecos?origem=cadastro";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Erro: " + e.getMessage());
            return "cadastrarCliente";  // Retorna ao formulário com mensagem de erro
        }
    }

    @GetMapping("/editarCliente/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        // Busca o cliente pelo id
        Cliente cliente = clienteService.buscarPorId(id);

        // Verifica se o cliente existe
        if (cliente == null) {
            model.addAttribute("errorMessage", "Cliente não encontrado.");
            return "redirect:/cadastrarCliente";  // Caso não encontre o cliente, redireciona para o cadastro
        }

        // Adiciona o cliente e seus endereços ao modelo para ser usado na view
        model.addAttribute("cliente", cliente);
        model.addAttribute("enderecos", cliente.getEnderecos());  // Passa os endereços do cliente para o formulário

        // Retorna a página para editar o cliente
        return "cadastrarCliente";
    }

    @PostMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable int id,
                                @ModelAttribute Cliente cliente,
                                @RequestParam List<String> logradouro,
                                @RequestParam List<String> numero,
                                @RequestParam List<String> complemento,
                                @RequestParam List<String> bairro,
                                @RequestParam List<String> cidade,
                                @RequestParam List<String> uf,
                                @RequestParam List<String> cep,
                                @RequestParam(value = "principal", required = false) Integer enderecoPrincipal,
                                Model model) {
        try {
            Cliente clienteExistente = clienteService.buscarPorId(id);

            if (clienteExistente != null) {
                // Atualiza os dados do cliente
                clienteExistente.setNome(cliente.getNome());
                clienteExistente.setCpf(cliente.getCpf());
                clienteExistente.setEmail(cliente.getEmail());
                clienteExistente.setDataNascimento(cliente.getDataNascimento());
                clienteExistente.setGenero(cliente.getGenero());

                // Atualiza a senha caso tenha sido modificada
                if (cliente.getSenha() != null && !cliente.getSenha().isEmpty()) {
                    clienteExistente.setSenha(cliente.getSenha());
                }

                clienteService.atualizarCliente(clienteExistente.getIdCliente(), clienteExistente);

                // Valida os CEPs
                for (String c : cep) {
                    if (!validarCep(c)) {
                        model.addAttribute("errorMessage", "Erro: O CEP " + c + " é inválido.");
                        return "cadastrarCliente";  // Retorna com a mensagem de erro de CEP inválido
                    }
                }

                // Atualiza os endereços do cliente
                for (int i = 0; i < logradouro.size(); i++) {
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(logradouro.get(i));
                    endereco.setNumero(numero.get(i));
                    endereco.setComplemento(complemento.get(i));
                    endereco.setBairro(bairro.get(i));
                    endereco.setCidade(cidade.get(i));
                    endereco.setUf(uf.get(i));
                    endereco.setCep(cep.get(i));
                    endereco.setCliente(clienteExistente);  // Associa o endereço ao cliente

                    // Define o endereço principal
                    if (enderecoPrincipal != null && enderecoPrincipal == i) {
                        endereco.setPrincipal(true);  // Marca este endereço como principal
                    } else {
                        endereco.setPrincipal(false);  // Marca como não principal
                    }

                    enderecoService.salvar(endereco);
                }

                return "redirect:/enderecos?origem=cadastro";
            } else {
                model.addAttribute("errorMessage", "Cliente não encontrado.");
                return "cadastrarCliente";  // Retorna para a página de cadastro com a mensagem de erro
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Erro: " + e.getMessage());
            return "cadastrarCliente";  // Retorna para a página de cadastro com a mensagem de erro
        }
    }

    // Lista todos os clientes
    @GetMapping("/listarClientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "login";  // Retorna para a página de login
    }
}
