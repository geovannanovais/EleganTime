package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.*;
import com.eleganTime.elegantime.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
public class CadastrarClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/cadastrarCliente")
    public String showCadastroForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastrarCliente";
    }

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente, Model model) {
        try {
            // Verifica se o cliente já existe (por email ou CPF, por exemplo)
            if (clienteService.buscarPorEmail(cliente.getEmail()) != null) {
                model.addAttribute("errorMessage", "Cliente já cadastrado com este e-mail.");
                return "cadastrarCliente";
            }

            // Salva o cliente
            cliente = clienteService.salvar(cliente);  // Salva o cliente no banco

            // Cria um novo carrinho e associa ao cliente
            Carrinho carrinho = new Carrinho();
            carrinho.setCliente(cliente);  // Associa o carrinho ao cliente

            // Salva o carrinho no banco de dados
            carrinhoService.salvarCarrinho(carrinho);  // Método para salvar o carrinho no banco

            // Limpa qualquer mensagem de erro e redireciona para login
            model.addAttribute("errorMessage", null);
            return "redirect:/login";  // Redireciona para a página de login
        } catch (Exception e) {
            // Exibe a mensagem de erro
            model.addAttribute("errorMessage", "Erro ao cadastrar cliente: " + e.getMessage());
            return "cadastrarCliente";  // Retorna ao formulário de cadastro com mensagem de erro
        }
    }

    @GetMapping("/editarCliente/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return "redirect:/cadastrarCliente";
        }
        model.addAttribute("cliente", cliente);
        return "cadastrarCliente";
    }

    @PostMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable int id, @ModelAttribute Cliente cliente, Model model) {
        try {
            Cliente clienteExistente = clienteService.buscarPorId(id);

            if (clienteExistente != null) {
                clienteExistente.setNome(cliente.getNome());
                clienteExistente.setCpf(cliente.getCpf());
                clienteExistente.setEmail(cliente.getEmail());
                clienteService.atualizarCliente(clienteExistente.getIdCliente(), clienteExistente);
                model.addAttribute("errorMessage", null);  // Limpa a mensagem de erro após sucesso
                return "redirect:/login";  // Redireciona para a página de login
            } else {
                model.addAttribute("errorMessage", "Cliente não encontrado.");
                return "cadastrarCliente";  // Retorna para a página de cadastro com a mensagem de erro
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Erro: " + e.getMessage());
            return "cadastrarCliente";  // Retorna para a página de cadastro com a mensagem de erro
        }
    }

    @GetMapping("/listarClientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "login";
    }
}
