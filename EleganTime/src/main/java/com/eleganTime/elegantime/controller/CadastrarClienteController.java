package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente, Model model) {
        try {
            clienteService.salvar(cliente);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "cadastrarCliente";
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
        model.addAttribute("clientes", clienteService.listarClientes());
        return "login";
    }
}
