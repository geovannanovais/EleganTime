package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.service.ClienteService;
import com.eleganTime.elegantime.service.EnderecoService;
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
    private ClienteService clienteService;

    @Autowired
    private EnderecoService enderecoService;



    // Exibe a lista de endereços para o cadastro ou carrinho
    @GetMapping("/enderecos")
    public String exibirListaEnderecos2(Model model, Authentication authentication, @RequestParam(required = false) String origem) {
        String email = authentication != null ? authentication.getName() : null;
        Cliente cliente = clienteService.buscarPorEmail(email);  // Buscando o cliente pelo e-mail
        var enderecos = enderecoService.buscarEnderecosPorCliente(cliente.getIdCliente());  // Buscando os endereços do cliente
        model.addAttribute("enderecos", enderecos);  // Adicionando os endereços no modelo
        model.addAttribute("origem", origem); // Adicionando a origem no modelo
        return "formEndereco";  // Retorna o nome da view para o Thymeleaf (formEndereco.html)
    }

    // Seleciona o endereço e marca-o como principal
    @PostMapping("/selecionarEndereco")
    public String selecionarEndereco2(@RequestParam("enderecoId") int enderecoId,
                                      @RequestParam(required = false) String origem,
                                      Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        Cliente cliente = clienteService.buscarPorEmail(email);  // Buscando cliente por email
        enderecoService.marcarEnderecoComoPrincipal(enderecoId, cliente.getIdCliente());  // Marcando o endereço como principal

        if ("cadastro".equals(origem)) {
            return "redirect:/login";
        } else {
            return "redirect:/formaPagamento";
        }
    }


}
