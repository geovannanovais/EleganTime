package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Pedido;
import com.eleganTime.elegantime.model.ItemPedido;
import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.PedidoService;
import com.eleganTime.elegantime.service.UsuarioService;  // Serviço para verificar usuário (Admin, Estoquista)
import com.eleganTime.elegantime.service.ClienteService;  // Serviço para verificar cliente
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ListaPedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;  // Para verificar se é um usuário (Admin ou Estoquista)
    private final ClienteService clienteService;  // Para verificar se é um cliente

    // Injeção de dependência via construtor
    public ListaPedidoController(PedidoService pedidoService, UsuarioService usuarioService, ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        // Obtém o email do usuário logado
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();

        // Verifica se o usuário logado é um "usuário" (Admin ou Estoquista)
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(email); // Busca o usuário pelo email

        List<Pedido> pedidos;

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Se for um "usuário" (Admin ou Estoquista), exibe todos os pedidos
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR")) ||
                    user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTOQUISTA"))) {
                pedidos = pedidoService.buscarTodosPedidos();
            } else {
                // Se for um "usuário" mas não for Admin ou Estoquista, não deve ver pedidos
                pedidos = List.of();  // Retorna uma lista vazia
            }
        } else {
            // Se não for um "usuário", vamos verificar se é um "cliente"
            Cliente cliente = clienteService.buscarPorEmail(email);  // Busca o cliente pelo email

            if (cliente != null) {

                // Se for cliente, exibe apenas os pedidos do cliente
                pedidos = pedidoService.buscarPedidosPorIdCliente(cliente.getIdCliente());
            } else {
                // Caso não encontre um cliente associado ao usuário
                pedidos = List.of();  // Retorna uma lista vazia
            }
        }

        // Carregar os produtos de cada pedido
        for (Pedido pedido : pedidos) {
            // Supondo que você tem uma lista de itens ou produtos no pedido
            List<ItemPedido> itensPedido = pedidoService.buscarItensPorPedido(pedido.getId());
            pedido.setItens(itensPedido);  // Adicionando os itens ao pedido
        }


        // Passa a lista de pedidos para o modelo
        model.addAttribute("pedidos", pedidos);
        return "listaPedidos";  // Nome do template Thymeleaf
    }

    // Serviço para acessar dados do pedido

    @PostMapping("/alterarStatusPedido")
    public String alterarStatusPedido(@RequestParam Map<String, String> parametros, Model model) {
        for (Map.Entry<String, String> entry : parametros.entrySet()) {
            if (entry.getKey().startsWith("status_")) {
                // Extrai o ID do pedido a partir do nome do campo "status_{id}"
                int pedidoId = Integer.parseInt(entry.getKey().substring(7)); // Extrai o ID do pedido
                String novoStatus = entry.getValue(); // Obtém o novo status enviado
                pedidoService.atualizarStatus(pedidoId, novoStatus); // Atualiza o status no banco de dados
            }
        }

        // Após a atualização, recarregue os pedidos novamente para garantir que o modelo seja atualizado
        // Obtém o email do usuário logado
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();

        List<Pedido> pedidos;

        // Verifica se o usuário logado é um "usuário" (Admin ou Estoquista)
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(email); // Busca o usuário pelo email

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Se for um "usuário" (Admin ou Estoquista), exibe todos os pedidos
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR")) ||
                    user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTOQUISTA"))) {
                pedidos = pedidoService.buscarTodosPedidos();
            } else {
                // Se for um "usuário" mas não for Admin ou Estoquista, não deve ver pedidos
                pedidos = List.of();  // Retorna uma lista vazia
            }
        } else {
            // Se não for um "usuário", vamos verificar se é um "cliente"
            Cliente cliente = clienteService.buscarPorEmail(email);  // Busca o cliente pelo email

            if (cliente != null) {
                // Se for cliente, exibe apenas os pedidos do cliente
                pedidos = pedidoService.buscarPedidosPorIdCliente(cliente.getIdCliente());
            } else {
                // Caso não encontre um cliente associado ao usuário
                pedidos = List.of();  // Retorna uma lista vazia
            }
        }

        // Passa a lista de pedidos atualizada para o modelo
        model.addAttribute("pedidos", pedidos);
        return "redirect:/pedidos";  // Redireciona para a lista de pedidos ou outra página
    }




}
