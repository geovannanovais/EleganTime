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
        return "cadastrarCliente";
    }

    @GetMapping("/editarCliente/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            // Caso o cliente não exista, redireciona para a página de cadastro
            return "redirect:/cadastrarCliente";
        }
        model.addAttribute("cliente", cliente);
        return "cadastrarCliente";
    }
    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente) {
        if (cliente.getIdCliente() > 0) {

            return "redirect:/editarCliente/" + cliente.getIdCliente();
        }

        clienteService.salvar(cliente);
        return "redirect:/login";
    }

    @PostMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable int id, @ModelAttribute Cliente cliente) {
        Cliente clienteExistente = clienteService.buscarPorId(id);

        if (clienteExistente != null) {
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cliente.getCpf());
            clienteExistente.setEmail(cliente.getEmail());


            clienteService.atualizarCliente(clienteExistente.getIdCliente(), clienteExistente);
            return "redirect:/login";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/listarClientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "login";
    }
}
