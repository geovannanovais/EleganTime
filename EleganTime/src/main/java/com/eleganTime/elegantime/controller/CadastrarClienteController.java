package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente; // Importe a classe Cliente
import com.eleganTime.elegantime.service.ClienteService; // Importe o serviço ClienteService

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@CrossOrigin("*")
public class CadastrarClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cadastrarCliente")
    public String showCadastroForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastrarCliente"; // Nome da página HTML para o cadastro
    }

    @GetMapping("/editarCliente/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "cadastrarCliente"; // Nome da página HTML para a edição
    }

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente) {
        if (cliente.getIdCliente() > 0) {
            // Se o ID é maior que zero, redireciona para o método de edição
            return "redirect:/editarCliente/" + cliente.getIdCliente();
        }

        clienteService.salvar(cliente);
        return "redirect:/listarClientes"; // Redireciona para a lista de clientes
    }

    @PostMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable int id, @ModelAttribute Cliente cliente) {
        Cliente clienteExistente = clienteService.buscarPorId(id);

        if (clienteExistente != null) {
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cliente.getCpf());
            clienteExistente.setEmail(cliente.getEmail());
            // Atualize outros campos conforme necessário

            clienteService.atualizarCliente(clienteExistente.getIdCliente(), clienteExistente);
            return "redirect:/listarClientes"; // Redireciona para a lista de clientes
        } else {
            return "redirect:/error"; // Redireciona para uma página de erro
        }
    }

    @GetMapping("/listarClientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "listarClientes"; // Nome da página HTML para a lista de clientes
    }
}
