package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    protected ClienteRepository clienteRepository; // Usando o repositório de Cliente

    public Cliente salvar(Cliente cliente) {
        if (cliente.getIdCliente() != 0) {
            Cliente clienteExistente = buscarPorId(cliente.getIdCliente());
            if (clienteExistente != null) {
                return atualizarCliente(cliente.getIdCliente(), cliente);
            } else {
                throw new RuntimeException("Cliente não encontrado para atualização");
            }
        } else {
            // Criação de um novo cliente
            return clienteRepository.save(cliente);
        }
    }

    public Cliente autenticarCliente(String email, String senha) {

        Cliente cliente = clienteRepository.findByEmailAndSenha(email, senha);
        return cliente;
    }

    public Cliente atualizarCliente(int id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cliente.getCpf());
            clienteExistente.setEmail(cliente.getEmail());
            // Adicione outros campos que você tenha na classe Cliente

            return clienteRepository.save(clienteExistente);
        } else {
            throw new RuntimeException("Cliente não encontrado para atualização");
        }
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public void deletarCliente(int id) {
        clienteRepository.deleteById(id);
    }

    public Cliente buscarPorId(int id) {
        return clienteRepository.findById(id).orElse(null);
    }

}
