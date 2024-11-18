package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnderecoController {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Exibe o formulário de endereço de entrega para o cliente logado.
     *
     * @param model Modelo que será utilizado no Thymeleaf
     * @param authentication Objeto Authentication que contém os dados do usuário logado
     * @return a página do formulário de endereço
     */
    @GetMapping("/enderecos")
    public String exibirFormularioEndereco(Model model, Authentication authentication) {
        // Obtém o email do cliente logado
        String email = authentication != null ? authentication.getName() : null;

        // Busca o cliente no banco de dados pelo email
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Passa o objeto Cliente para o Thymeleaf, incluindo os dados do endereço
        model.addAttribute("cliente", cliente);
        model.addAttribute("cep", cliente.getCep());  // Passa o CEP do cliente para o formulário
        model.addAttribute("enderecoEntrega", cliente.getEnderecoEntrega());  // Passa o endereço de entrega
        model.addAttribute("logradouro", cliente.getLogradouro());  // Passa o logradouro
        model.addAttribute("numero", cliente.getNumero());  // Passa o número
        model.addAttribute("complemento", cliente.getComplemento());  // Passa o complemento
        model.addAttribute("bairro", cliente.getBairro());  // Passa o bairro
        model.addAttribute("cidade", cliente.getCidade());  // Passa a cidade
        model.addAttribute("uf", cliente.getUf());  // Passa a UF

        return "formEndereco";  // Página Thymeleaf que irá exibir o formulário
    }

    @PostMapping("/enderecos")
    public String salvarEndereco(@RequestParam("clienteId") int clienteId, Cliente cliente, Authentication authentication) {
        // Obtém o email do cliente logado
        String email = authentication != null ? authentication.getName() : null;

        // Busca o cliente no banco de dados pelo email
        Cliente clienteExistente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Atualiza os dados de endereço com os novos valores do formulário
        clienteExistente.setLogradouro(cliente.getLogradouro());
        clienteExistente.setNumero(cliente.getNumero());
        clienteExistente.setComplemento(cliente.getComplemento());
        clienteExistente.setBairro(cliente.getBairro());
        clienteExistente.setCidade(cliente.getCidade());
        clienteExistente.setUf(cliente.getUf());
        clienteExistente.setCep(cliente.getCep());
        clienteExistente.setEnderecoEntrega(cliente.getEnderecoEntrega());  // Atualiza o endereço de entrega

        // Salva as alterações no banco de dados
        clienteRepository.save(clienteExistente);

        // Redireciona para a página de pagamento ou sucesso
        return "redirect:/formaPagamento";  // Redireciona para a página de pagamento ou outro destino
    }
}
