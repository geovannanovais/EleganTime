package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Endereco;
import com.eleganTime.elegantime.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
public class CadastrarEnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/cadastrarEndereco")
    public String showCadastroForm(Model model) {
        model.addAttribute("endereco", new Endereco());  // Adiciona um novo objeto Endereco ao modelo
        return "cadastrarEndereco";  // Retorna para o formulário de cadastro de endereço
    }

    @PostMapping("/cadastrarEndereco")
    public String cadastrarEndereco(@ModelAttribute Endereco endereco, Model model) {
        try {
            // Salva o novo endereço
            enderecoService.salvar(endereco);  // Tenta salvar o endereço

            model.addAttribute("errorMessage", null);  // Limpa a mensagem de erro em caso de sucesso
            return "redirect:/listarEnderecos";  // Redireciona para a lista de endereços após o cadastro
        } catch (RuntimeException e) {
            // Caso haja erro, mostra a mensagem de erro
            model.addAttribute("errorMessage", "Erro: " + e.getMessage());
            return "cadastrarEndereco";  // Retorna para o formulário de cadastro com a mensagem de erro
        }
    }

    @GetMapping("/editarEndereco/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Endereco endereco = enderecoService.buscarPorId(id);
        if (endereco == null) {
            return "redirect:/cadastrarEndereco";  // Se o endereço não for encontrado, redireciona para o cadastro
        }
        model.addAttribute("endereco", endereco);  // Adiciona o endereço ao modelo
        return "cadastrarEndereco";  // Retorna para o formulário de edição de endereço
    }

    @PostMapping("/editarEndereco/{id}")
    public String editarEndereco(@PathVariable int id, @ModelAttribute Endereco endereco, Model model) {
        try {
            Endereco enderecoExistente = enderecoService.buscarPorId(id);

            if (enderecoExistente != null) {
                // Atualiza as informações do endereço
                enderecoExistente.setCep(endereco.getCep());
                enderecoExistente.setLogradouro(endereco.getLogradouro());
                enderecoExistente.setNumero(endereco.getNumero());
                enderecoExistente.setComplemento(endereco.getComplemento());
                enderecoExistente.setBairro(endereco.getBairro());
                enderecoExistente.setCidade(endereco.getCidade());
                enderecoExistente.setUf(endereco.getUf());
                enderecoExistente.setPrincipal(endereco.isPrincipal());

                // Salva as alterações no banco de dados
                enderecoService.atualizarEndereco(enderecoExistente.getId(), enderecoExistente);

                model.addAttribute("errorMessage", null);  // Limpa a mensagem de erro após sucesso
                return "redirect:/listarEnderecos";  // Redireciona para a lista de endereços após a edição
            } else {
                model.addAttribute("errorMessage", "Endereço não encontrado.");
                return "cadastrarEndereco";  // Retorna para o formulário de cadastro com a mensagem de erro
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Erro: " + e.getMessage());
            return "cadastrarEndereco";  // Retorna para o formulário de cadastro com a mensagem de erro
        }
    }

    @GetMapping("/listarEnderecos")
    public String listarEnderecos(Model model) {
        model.addAttribute("enderecos", enderecoService.listarEnderecos());  // Lista todos os endereços
        return "listarEnderecos";  // Retorna para a página de listagem de endereços
    }
}