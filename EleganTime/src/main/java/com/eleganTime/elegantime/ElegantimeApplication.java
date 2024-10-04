package com.eleganTime.elegantime;

import java.util.Scanner;

import com.eleganTime.elegantime.console.LoginConsole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eleganTime.elegantime.console.ProdutoConsole;
import com.eleganTime.elegantime.console.UsuarioConsole;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ElegantimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElegantimeApplication.class, args);
    }


    @Bean
    public CommandLineRunner run(LoginConsole loginConsole, UsuarioConsole usuarioConsole, ProdutoConsole produtoConsole) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            try {
                while (true) {
                    loginConsole.Login();
                    boolean logged = true;

                   while(logged) {
                       System.out.println("==== Menu Principal ====");
                       System.out.println("1. Gerenciar Usuários");
                       System.out.println("2. Gerenciar Produtos");
                       System.out.println("0. Sair");
                       System.out.print("Escolha uma opção: ");

                       int escolha = scanner.nextInt();
                       scanner.nextLine();

                       switch (escolha) {
                           case 1:
                               usuarioConsole.exibirMenu();
                               logged = true;
                               continue;
                           case 2:
                               produtoConsole.exibirMenu();
                               logged = true;
                               continue;
                           case 0:
                               System.out.println("Saindo...");
                               logged = false;
                               break;
                           default:
                               System.out.println("Opção inválida. Tente novamente.");
                       }
                   }
                }
            } finally {
                scanner.close();
            }
        };
    }



}