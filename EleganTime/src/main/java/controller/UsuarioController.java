package controller;

import model.Produto;
import model.Usuario;
import service.UsuarioService;
import utils.SessaoUsuario;

import java.util.List;
import java.util.Scanner;

public class UsuarioController {

    private UsuarioService usuarioService;
    private Scanner scanner;
    private Usuario usuarioLogado;

    public UsuarioController() {
        usuarioService = new UsuarioService();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean loginBemSucedido = false;

        // Loop até que o login seja bem-sucedido ou o usuário decida sair
        while (!loginBemSucedido) {
            if (exibirTelaLogin()) {
                loginBemSucedido = true; // Login bem-sucedido
            } else {
                System.out.println("Falha no login. Deseja tentar novamente? (S/N)");
                String resposta = scanner.nextLine().trim().toLowerCase();
                if (resposta.equals("n")) {
                    System.out.println("Encerrando o sistema...");
                    return; // Encerra o sistema
                }
            }
        }

        // Se o login for bem-sucedido, entra no menu principal
        boolean continuar = true;
        while (continuar) {
            Usuario usuarioLogado = SessaoUsuario.getUsuario();

            if (usuarioLogado == null) {
                System.out.println("Usuário não autenticado. Por favor, faça login.");
                continuar = false;
                return;
            }

            // Exibir opções dependendo do tipo de usuário (Administrador ou Estoquista)
            if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador")) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Gerenciar Usuários");
                System.out.println("2. Gerenciar Produtos");
                System.out.println("3. Logout");
            } else if (usuarioLogado.getGrupo().equalsIgnoreCase("Estoquista")) {
                // Exibir opções restritas para estoquista
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Listar Produtos");
                System.out.println("2. Atualizar Quantidade de Estoque");
                System.out.println("3. Logout");
            }

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador")) {
                        // Administrador acessa menu de usuários
                        menuUsuario();
                    } else if (usuarioLogado.getGrupo().equalsIgnoreCase("Estoquista")) {
                        // Estoquista lista produtos
                        listarProduto();
                    }
                    break;
                case 2:
                    if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador")) {
                        // Administrador acessa menu de produtos
                        menuProduto();
                    } else if (usuarioLogado.getGrupo().equalsIgnoreCase("Estoquista")) {
                        // Estoquista pode atualizar a quantidade de estoque
                        atualizarProduto();
                    }
                    break;
                case 3:
                    SessaoUsuario.logout();  // Limpa a sessão
                    System.out.println("Você foi desconectado.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Tela de login
    private boolean exibirTelaLogin() {
        System.out.println("------------ Tela de Login ------------");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        // Chama o serviço para validar o login
        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario != null) {
            SessaoUsuario.setUsuario(usuario);  // Armazenar o usuário logado na sessão
            System.out.println("Login realizado com sucesso! Bem-vindo, " + usuario.getNome());
            return true;
        } else {
            System.out.println("Email ou senha inválidos.");
            return false;
        }
    }

    private void menuUsuario() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma operação:");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Atualizar Usuário");
            System.out.println("3. Listar Usuários");
            System.out.println("4. Voltar");

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuario();
                    System.out.println();
                    atualizarUsuario();
                    break;
                case 3:
                    listarUsuario();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void menuProduto() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma operação:");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Atualizar Produto");
            System.out.println("3. Listar Produtos");
            System.out.println("4. Voltar");

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Usuario usuarioLogado = SessaoUsuario.getUsuario();

            switch (opcao) {
                case 1:
                    if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador")) {
                        cadastrarProduto();
                    } else {
                        System.out.println("Acesso negado. Apenas administradores podem cadastrar produtos.");
                    }
                    break;
                case 2:
                    listarProduto();
                    System.out.println();
                    if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador") || usuarioLogado.getGrupo().equalsIgnoreCase("Estoquista")) {
                        atualizarProduto();
                    } else {
                        System.out.println("Acesso negado. Apenas administradores e estoquistas podem atualizar produtos.");
                    }
                    break;
                case 3:
                    listarProduto();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarUsuario() {
        // Loop para forçar a preencher o nome
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Erro: O nome não pode estar vazio.");
            }
        } while (nome.isEmpty());

        // Loop para forçar a preencher o cpf
        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (cpf.contains(" ") || cpf.isEmpty()) {
                System.out.println("Erro: O CPF não pode conter espaços ou estar vazio.");
            }
        } while (cpf.contains(" ") || cpf.isEmpty());

        // Loop para forçar a preencher o email
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (email.contains(" ") || email.isEmpty()) {
                System.out.println("Erro: O Email não pode conter espaços ou estar vazio.");
            }
        } while (email.contains(" ") || email.isEmpty());

        // Loop para forçar a preencher o grupo
        String grupo;
        do {
            System.out.print("Grupo (Administrador ou Estoquista): ");
            grupo = scanner.nextLine().trim();
            if (grupo.contains(" ") || grupo.isEmpty() || (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista"))) {
                System.out.println("Erro: Grupo inválido. Escolha 'Administrador' ou 'Estoquista'.");
            }
        } while (grupo.contains(" ") || grupo.isEmpty() || (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista")));

        // Loop para forçar a preencher a senha
        String senha;
        do {
            System.out.print("Senha: ");
            senha = scanner.nextLine().trim();
            if (senha.length() < 5 || senha.contains(" ")) {
                System.out.println("Erro: A senha deve ter pelo menos 5 caracteres e não pode conter espaços.");
            }
        } while (senha.length() < 5 || senha.contains(" "));

        // Loop para forçar a preencher a condicao do usuario (false ou true)
        boolean condicaoDoUsuario;
        String condicaoInput;
        do {
            System.out.print("Condição do Usuário (true/false): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.equals("true")) {
                condicaoDoUsuario = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoUsuario = false;
            } else {
                System.out.println("Erro: A condição do usuário deve ser 'true' ou 'false'.");
                condicaoDoUsuario = false; // Inicializar com um valor válido para continuar o loop
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));

        // Salvando o novo usuario
        Usuario novoUsuario = new Usuario(nome, cpf, email, grupo, senha, condicaoDoUsuario);
        boolean sucesso = usuarioService.cadastrarUsuario(novoUsuario);

        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o usuário.");
        }
    }

    private void atualizarUsuario() {
        System.out.println("ID do Usuário a ser atualizado:");
        int idUsuario = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o número

        // Obtém o usuário pelo ID
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        // Exibe todos os dados do usuário antes de permitir a atualização
        System.out.println("Usuário selecionado:");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("CPF: " + usuario.getCpf());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Grupo: " + usuario.getGrupo());
        System.out.println("Status: " + (usuario.getCondicaoDoUsuario() ? "Ativo" : "Inativo"));

        // Agora o usuário pode alterar as informações
        // Loop para forçar a preencher o nome
        String nome;
        do {
            System.out.print("Novo Nome (ou aperte Enter para manter o mesmo): ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                nome = usuario.getNome(); // Mantém o nome atual se o usuário não inserir nada
            }
        } while (nome.isEmpty());

        // Loop para forçar a preencher o CPF
        String cpf;
        do {
            System.out.print("Novo CPF (ou aperte Enter para manter o mesmo): ");
            cpf = scanner.nextLine().trim();
            if (cpf.isEmpty()) {
                cpf = usuario.getCpf(); // Mantém o CPF atual se o usuário não inserir nada
            } else if (cpf.contains(" ")) {
                System.out.println("Erro: O CPF não pode conter espaços.");
            }
        } while (cpf.contains(" "));

        // Loop para forçar a preencher o email
        // O e-mail será sempre o mesmo do usuário original
        String email = usuario.getEmail();

        // Loop para forçar a preencher o grupo
        String grupo;
        do {
            System.out.print("Novo Grupo (Administrador ou Estoquista) (ou aperte Enter para manter o mesmo): ");
            grupo = scanner.nextLine().trim();
            if (grupo.isEmpty()) {
                grupo = usuario.getGrupo(); // Mantém o grupo atual se o usuário não inserir nada
            } else if (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista")) {
                System.out.println("Erro: Grupo inválido. Escolha 'Administrador' ou 'Estoquista'.");
            }
        } while (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista"));

        // Loop para forçar a preencher a senha
        String senha;
        do {
            System.out.print("Nova Senha (ou aperte Enter para manter a mesma): ");
            senha = scanner.nextLine().trim();
            if (senha.isEmpty()) {
                senha = usuario.getSenha(); // Mantém a senha atual se o usuário não inserir nada
            } else if (senha.length() < 5 || senha.contains(" ")) {
                System.out.println("Erro: A senha deve ter pelo menos 5 caracteres e não pode conter espaços.");
            }
        } while (senha.length() < 5 || senha.contains(" "));

        // Loop para forçar a preencher a condição do usuário
        boolean condicaoDoUsuario;
        String condicaoInput;
        do {
            System.out.print("Condição do Usuário (true/false) (ou aperte Enter para manter o mesmo): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.isEmpty()) {
                condicaoDoUsuario = usuario.getCondicaoDoUsuario(); // Mantém a condição atual se o usuário não inserir nada
            } else if (condicaoInput.equals("true")) {
                condicaoDoUsuario = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoUsuario = false;
            } else {
                System.out.println("Erro: A condição do usuário deve ser 'true' ou 'false'.");
                condicaoDoUsuario = usuario.getCondicaoDoUsuario();
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false") && !condicaoInput.isEmpty());

        // Atualizando o usuário
        Usuario usuarioAtualizado = new Usuario(idUsuario, nome, cpf, email, grupo, senha, condicaoDoUsuario);
        boolean sucesso = usuarioService.atualizarUsuario(usuarioAtualizado);

        if (sucesso) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o usuário.");
        }
    }

    public void listarUsuario() {
        List<Usuario> usuarios = usuarioService.listarUsuario();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        // Cabeçalho da tabela
        System.out.printf("%-10s %-20s %-30s %-15s %-15s%n", "ID", "Nome", "Email", "Status", "Grupo");

        // Linha de separação
        System.out.println("=".repeat(90));

        // Conteúdo da tabela
        for (Usuario usuario : usuarios) {
            System.out.printf("%-10d %-20s %-30s %-15s %-15s%n", usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getCondicaoDoUsuario() ? "Ativo" : "Inativo", usuario.getGrupo());
        }
    }

    private void atualizarProduto() {
        System.out.println("ID do Produto a ser atualizado:");
        int idProduto = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o número

        // Obtém o produto pelo ID
        Produto produto = usuarioService.buscarProdutoPorId(idProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        // Exibe os detalhes do produto
        System.out.println("Produto selecionado:");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Avaliação: " + produto.getAvaliacao());
        System.out.println("Descrição: " + produto.getDescricao());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Quantidade Atual em Estoque: " + produto.getQuantidadeEmEstoque());
        System.out.println("Condição do Produto: " + (produto.getCondicaoDoProduto() ? "Ativo" : "Inativo"));

        Usuario usuarioLogado = SessaoUsuario.getUsuario();

        // Verifica se o usuário é administrador ou estoquista
        if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador")) {
            // Administrador pode alterar todos os campos do produto

            // Alterar Nome
            System.out.print("Novo Nome: ");
            String nome = scanner.nextLine().trim();
            if (!nome.isEmpty()) {
                produto.setNome(nome);
            }

            // Alterar Avaliação
            double avaliacao;
            do {
                System.out.print("Nova Avaliação (1 a 5): ");
                while (!scanner.hasNextDouble()) {
                    System.out.println("Erro: A avaliação deve ser um número decimal.");
                    scanner.next(); // Consumir a entrada inválida
                    System.out.print("Nova Avaliação (1 a 5): ");
                }
                avaliacao = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha após o número
                if (avaliacao < 1 || avaliacao > 5) {
                    System.out.println("Erro: A avaliação deve estar entre 1 e 5.");
                }
            } while (avaliacao < 1 || avaliacao > 5);
            produto.setAvaliacao(avaliacao);

            // Alterar Descrição
            System.out.print("Nova Descrição: ");
            String descricao = scanner.nextLine().trim();
            if (!descricao.isEmpty()) {
                produto.setDescricao(descricao);
            }

            // Alterar Preço
            double preco;
            do {
                System.out.print("Novo Preço: ");
                while (!scanner.hasNextDouble()) {
                    System.out.println("Erro: O preço deve ser um número decimal.");
                    scanner.next(); // Consumir a entrada inválida
                    System.out.print("Novo Preço: ");
                }
                preco = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha após o número
                if (preco < 0) {
                    System.out.println("Erro: O preço não pode ser negativo.");
                }
            } while (preco < 0);
            produto.setPreco(preco);

            // Alterar Condição
            String condicaoInput;
            boolean condicaoProduto;
            do {
                System.out.print("Condição do Produto (true/false): ");
                condicaoInput = scanner.nextLine().trim().toLowerCase();
                if (condicaoInput.equals("true")) {
                    condicaoProduto = true;
                } else if (condicaoInput.equals("false")) {
                    condicaoProduto = false;
                } else {
                    System.out.println("Erro: A condição do produto deve ser 'true' ou 'false'.");
                    condicaoProduto = produto.getCondicaoDoProduto(); // Mantém o valor atual se entrada inválida
                }
            } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));
            produto.setCondicaoDoProduto(condicaoProduto);
        }

        // Estoquista e Administrador podem alterar a quantidade de estoque
        int novaQuantidade;
        do {
            System.out.print("Nova Quantidade em Estoque: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erro: Quantidade em estoque deve ser um número inteiro.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Nova Quantidade em Estoque: ");
            }
            novaQuantidade = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (novaQuantidade < 0) {
                System.out.println("Erro: A quantidade em estoque não pode ser negativa.");
            }
        } while (novaQuantidade < 0);

        // Atualizar a quantidade de estoque do produto
        produto.setQuantidadeEmEstoque(novaQuantidade);

        boolean sucesso;
        if (usuarioLogado.getGrupo().equalsIgnoreCase("Administrador")) {
            // Se for administrador, atualiza todas as informações
            sucesso = usuarioService.atualizarProduto(produto);
        } else {
            // Se for estoquista, atualiza apenas a quantidade de estoque
            sucesso = usuarioService.atualizarProdutoEstoque(produto);
        }

        if (sucesso) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o produto.");
        }
    }

    private void cadastrarProduto() {

        // Loop para forçar a preencher o nome
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Erro: O nome não pode estar vazio.");
            }
        } while (nome.isEmpty());

        // Loop para forçar a preencher a avaliação
        double avaliacao;
        do {
            System.out.print("Avaliação (1 a 5): ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Erro: A avaliação deve ser um número decimal.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Avaliação (1 a 5): ");
            }
            avaliacao = scanner.nextDouble();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (avaliacao < 1 || avaliacao > 5 || (avaliacao * 10) % 5 != 0) {
                System.out.println("Erro: A avaliação deve estar entre 1 e 5.");
            }
        } while (avaliacao < 1 || avaliacao > 5 || (avaliacao * 10) % 5 != 0);

        // Loop para forçar a preencher a descrição
        String descricao;
        do {
            System.out.print("Descrição Detalhada: ");
            descricao = scanner.nextLine().trim();
            if (descricao.isEmpty()) {
                System.out.println("Erro: A descrição não pode estar vazia.");
            }
        } while (descricao.isEmpty());

        // Loop para forçar a preencher o preço
        double preco;
        do {
            System.out.print("Preço: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Erro: Preço deve ser um número decimal.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Preço: ");
            }
            preco = scanner.nextDouble();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (preco < 0) {
                System.out.println("Erro: O preço não pode ser negativo.");
            }
        } while (preco < 0);

        // Loop para forçar a preencher a quantidade em estoque
        int quantidadeEmEstoque;
        do {
            System.out.print("Quantidade em Estoque: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erro: Quantidade em estoque deve ser um número inteiro.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Quantidade em Estoque: ");
            }
            quantidadeEmEstoque = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (quantidadeEmEstoque < 0) {
                System.out.println("Erro: A quantidade em estoque não pode ser negativa.");
            }
        } while (quantidadeEmEstoque < 0);

        // Loop para forçar a preencher a condição do produto
        boolean condicaoDoProduto;
        String condicaoInput;
        do {
            System.out.print("Condição do Produto (true/false): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.equals("true")) {
                condicaoDoProduto = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoProduto = false;
            } else {
                System.out.println("Erro: A condição do produto deve ser 'true' ou 'false'.");
                condicaoDoProduto = false;
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));

        // Cadastrando o produto
        Produto produto = new Produto(nome, avaliacao, descricao, preco, quantidadeEmEstoque, condicaoDoProduto);
        boolean sucesso = UsuarioService.cadastrarProduto(produto);

        if (sucesso) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o produto.");
        }
    }

    public void listarProduto() {
        List<Produto> produtos = usuarioService.listarProduto();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        // Cabeçalho da tabela
        System.out.printf("%-10s %-30s %-10s %-15s %-20s %-10s%n", "ID", "Nome", "Avaliacao", "Valor", "Qtde Estoque", "Status");

        // Linha de separação
        System.out.println("=".repeat(100));

        // Conteúdo da tabela
        for (Produto produto : produtos) {
            System.out.printf("%-10s %-30s %-10s %-15s %-20s %-10s%n", produto.getIdProduto(), produto.getNome(), produto.getAvaliacao(), produto.getPreco(), produto.getQuantidadeEmEstoque(), produto.getCondicaoDoProduto() ? "Ativo" : "Inativo");
        }
    }

}
