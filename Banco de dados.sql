-- Deleta o banco de dados se existir
DROP DATABASE IF EXISTS elegantime;

-- Cria o banco de dados
CREATE DATABASE elegantime;

-- Utiliza o banco de dados recém-criado
USE elegantime;

-- Criação da tabela Usuario
CREATE TABLE Usuario (
    id_Usuario INT AUTO_INCREMENT PRIMARY KEY,  -- Nome do campo conforme a convenção Java
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(22) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,  -- Email único
    grupo VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL, 
    condicao_Do_Usuario BOOLEAN NOT NULL DEFAULT TRUE  -- Condição do usuário
);

-- Criação da tabela Produto
CREATE TABLE Produto (
    id_Produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    avaliacao DOUBLE(2,1) NOT NULL CHECK (avaliacao >= 1 AND avaliacao <= 5),
    descricao VARCHAR(2000) NOT NULL,
    preco DOUBLE(10,2) NOT NULL,
    quantidade_Em_Estoque INT NOT NULL,
    condicao_Do_Produto BOOLEAN NOT NULL DEFAULT TRUE
);

-- Criação da tabela ImagemProduto
CREATE TABLE Imagem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    caminho VARCHAR(255) NOT NULL,
    id_Produto INT NOT NULL,  -- Relacionamento com Produto
    principal BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (id_Produto) REFERENCES Produto(id_Produto)  -- Chave estrangeira
);

-- Inserção de dados na tabela Usuario
INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicao_Do_Usuario) 
VALUES ('Admin', '68112417059', 'admin@admin.com', 'Administrador', '$2a$10$3wNYGSZKsN871KDvj2BIjuyXk4FIYRjx5219f7d2dfaWYQWzHFoom', TRUE);

-- Inserção de dados na tabela Produto
INSERT INTO Produto (nome, avaliacao, descricao, preco, quantidade_Em_Estoque, condicao_Do_Produto) 
VALUES ('Relógio Feminino Lince Urban', 2, 'O relógio feminino da marca Lince é um acessório elegante e sofisticado que certamente chamará a atenção.', 400.00, 50, TRUE);

-- Inserção de dados na tabela ImagemProduto
INSERT INTO Imagem(caminho, id_Produto, principal) 
VALUES ('/imagens/relogio_feminino_lince_urban_01.jpg', 1, TRUE),
       ('/imagens/relogio_feminino_lince_urban_02.jpg', 1, FALSE);

-- Consulta todos os registros da tabela Usuario
SELECT * FROM Usuario;

-- Consulta todos os registros da tabela Produto
SELECT * FROM Produto;

-- Consulta todos os registros da tabela ImagemProduto
SELECT * FROM Imagem;