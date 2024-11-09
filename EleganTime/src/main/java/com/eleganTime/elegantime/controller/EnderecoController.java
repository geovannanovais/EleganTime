package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnderecoController {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Exibe o formulário de endereço de entrega para um cliente.
     *
     * @param clienteId ID do cliente (passado na URL)
     * @param model     Modelo que será utilizado no Thymeleaf
     * @return a página do formulário de endereço
     */
    @GetMapping("/enderecos/{clienteId}")
    public String exibirFormularioEndereco(@PathVariable("clienteId") int clienteId, Model model) {
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Passa o objeto Cliente para o Thymeleaf
        model.addAttribute("cliente", cliente);

        return "formEndereco";  // Página Thymeleaf que irá exibir o formulário
    }

    /**
     * Processa o envio do formulário de endereço de entrega.
     *
     * @param clienteId     ID do cliente
     * @param cliente       Objeto de cliente preenchido com as informações do endereço
     * @return Redireciona para uma página de confirmação ou sucesso
     */
    @PostMapping("/enderecos")
    public String salvarEndereco(@RequestParam("clienteId") int clienteId, Cliente cliente) {
        // Busca o cliente
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Atualiza os dados de endereço
        clienteExistente.setLogradouro(cliente.getLogradouro());
        clienteExistente.setNumero(cliente.getNumero());
        clienteExistente.setComplemento(cliente.getComplemento());
        clienteExistente.setBairro(cliente.getBairro());
        clienteExistente.setCidade(cliente.getCidade());
        clienteExistente.setUf(cliente.getUf());
        clienteExistente.setCep(cliente.getCep());
        clienteExistente.setEnderecoEntrega(cliente.getEnderecoEntrega());

        // Salva as alterações no banco de dados
        clienteRepository.save(clienteExistente);

        // Redireciona para a página do carrinho ou uma página de sucesso
        return "redirect:/pagamento/";
    }
}
