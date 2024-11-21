package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ClienteService clienteService;

    // Método para ver o carrinho
    @GetMapping("/carrinho")
    public String verCarrinho(Model model, Authentication authentication, HttpSession session) {
        String email = authentication != null ? authentication.getName() : null;

        // Obtemos o carrinho baseado no email (anônimo ou logado)
        Carrinho carrinho = carrinhoService.obterCarrinho(email, session);

        // Calcular o valor total (itens + frete)
        carrinhoService.calcularTotal(carrinho); // Atualiza o valorTotal no carrinho

        // Adiciona o carrinho e o total à view
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinho.getValorTotal()); // Atribui o valor total (com frete)

        // Se o usuário estiver logado, oferece opções de frete fixas
        if (authentication != null && authentication.isAuthenticated()) {
            String[] opcoesFrete = {"Sedex", "PAC", "Motoboy"};  // Exemplos de frete
            model.addAttribute("opcoesFrete", opcoesFrete);
            model.addAttribute("livreEscolha", false); // Usuário logado, não há escolha livre
        } else {
            // Para usuários não logados, o frete será de livre escolha
            model.addAttribute("livreEscolha", true);
        }

        return "carrinho";  // Redireciona para a página do carrinho
    }

    // Método para adicionar item ao carrinho
    @PostMapping("/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade,
                                      Authentication authentication,
                                      HttpSession session) {
        String email = authentication != null ? authentication.getName() : null;
        carrinhoService.adicionarItem(produtoId, quantidade, email, session); // Passa a sessão também
        return "redirect:/carrinho";  // Redireciona para a página do carrinho
    }

    // Método para remover item do carrinho
    @GetMapping("/carrinho/remover/{produtoId}")
    public String removerItem(@PathVariable int produtoId, Authentication authentication, HttpSession session) {
        String email = authentication != null ? authentication.getName() : null;
        carrinhoService.removerItem(produtoId, email, session);  // Passa a sessão também
        return "redirect:/carrinho";  // Redireciona para a página do carrinho
    }

    // Método para finalizar a compra (salva o carrinho no banco e redireciona para os endereços)
    @PostMapping("/finalizarCarrinho")
    public String finalizarCarrinho(Authentication authentication, HttpSession session,
                                    @RequestParam double frete) {
        String email = authentication != null ? authentication.getName() : null;

        // Recupera o carrinho atual
        Carrinho carrinho = carrinhoService.obterCarrinho(email, session);

        if(carrinho.getValorFrete() != 0 ) {

            carrinho.setValorTotal(carrinho.getValorTotal()- carrinho.getValorFrete());
            carrinhoService.calcularTotal(carrinho);


        }else {

            carrinho.setValorFrete(frete);

            // Calcula o total novamente após adicionar o frete
            carrinhoService.calcularTotal(carrinho);

        }

        // Salvar o carrinho no banco de dados se o usuário estiver logado
        if (email != null) {
            carrinhoService.salvarCarrinho(carrinho);  // Salva o carrinho com o valor total e frete
        } else {
            // Para usuários não logados, salva o carrinho na sessão
            session.setAttribute("carrinho", carrinho);
        }

        // Redireciona para a página de endereços
        return "redirect:/enderecos";  // Redirecionamento para a página de endereços
    }

}
