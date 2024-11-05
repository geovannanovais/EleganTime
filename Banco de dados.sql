-- Deleta o banco de dados se existir
DROP DATABASE IF EXISTS elegantime;

-- Cria o banco de dados
CREATE DATABASE elegantime;

-- Utiliza o banco de dados recém-criado
USE elegantime;

-- Criação da tabela Usuario
CREATE TABLE Usuario (
    id_Usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(22) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    grupo VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL, 
    condicao_Do_Usuario BOOLEAN NOT NULL DEFAULT TRUE
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
    id_Produto INT NOT NULL,
    principal BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (id_Produto) REFERENCES Produto(id_Produto)
);

-- Inserção de dados na tabela Usuario
INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicao_Do_Usuario) 
VALUES ('Admin', '68112417059', 'admin@admin.com', 'administrador', '12345', TRUE);

INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicao_Do_Usuario) 
VALUES ('Estoquista', '68112417022', 'estoquista@estoquista.com', 'estoquista', '12345', TRUE);

-- Inserção de dados na tabela Produto
INSERT INTO Produto (nome, avaliacao, descricao, preco, quantidade_Em_Estoque, condicao_Do_Produto) 
VALUES 
('Relógio Feminino Lince Urban', 2, 'O relógio feminino da marca Lince é um acessório elegante e sofisticado que certamente chamará a atenção.', 400.00, 50, TRUE),
('Relógio Masculino Lince Urban', 4, 'Relógio masculino Lince, perfeito para qualquer ocasião.', 450.00, 30, TRUE),
('Relógio Digital Lince', 3, 'Relógio digital com diversas funcionalidades.', 300.00, 20, TRUE);

-- Inserção de dados na tabela Imagem com caminho e nome da imagem
INSERT INTO Imagem(caminho, nome, id_Produto, principal) 
VALUES 
('img/img1.jpg', 'imagem1.jpg', 1, TRUE),
('img/img2.jpg', 'imagem2.jpg', 1, FALSE),
('img/img3.jpg', 'imagem3.jpg', 2, TRUE),
('img/img4.jpg', 'imagem4.jpg', 3, TRUE);

-- Consulta todos os registros da tabela Usuario
SELECT * FROM Usuario;

-- Consulta todos os registros da tabela Produto
SELECT * FROM Produto;

-- Consulta todos os registros da tabela Imagem
SELECT * FROM Imagem;
-- Inserção de dados na tabela Usuario

-- Criação da tabela Cliente
CREATE TABLE Cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(22) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    condicao_Do_Cliente BOOLEAN NOT NULL DEFAULT TRUE,
    dataNascimento DATE NOT NULL,          -- Novo campo para a data de nascimento
    genero VARCHAR(10) NOT NULL,           -- Novo campo para gênero
    cep VARCHAR(10) NOT NULL,              -- Novo campo para CEP
    enderecoEntrega VARCHAR(255) NOT NULL -- Novo campo para endereço de entrega
);

-- Inserção de dados na tabela Cliente
INSERT INTO Cliente (nome, cpf, email, senha, logradouro, numero, complemento, bairro, cidade, uf, condicao_Do_Cliente, dataNascimento, genero, cep, enderecoEntrega) 
VALUES 
('João Silva', '12345678909', 'joao.silva@email.com', 'senha123', 'Rua das Flores', '100', 'Apto 202', 'Centro', 'São Paulo', 'SP', TRUE, '1985-10-01', 'Masculino', '01010000', 'Rua das Flores, 100');

SELECT * FROM Cliente;