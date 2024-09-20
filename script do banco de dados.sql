

-- Criando o banco de dados
CREATE DATABASE elegantime;

-- Usando o banco de dados recém-criado
USE elegantime;

-- Criando a tabela Usuario com todos os campos
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf BIGINT NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE, -- email tem que ser único cadastrado
    grupo ENUM('Administrador', 'Estoquista') NOT NULL,
    senha VARCHAR(100) NOT NULL CHECK (LENGTH(senha) >= 5), -- senha tem que ser maior que 5
    condicaoDoUsuario BOOLEAN NOT NULL DEFAULT TRUE
);

-- Criando a tabela Produto
CREATE TABLE Produto (
    idProduto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    avaliacao DECIMAL(2,1) NOT NULL CHECK (avaliacao >= 1 AND avaliacao <= 5),
    descricao VARCHAR(2000) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidadeEmEstoque INT NOT NULL,
    condicaoDoProduto BOOLEAN NOT NULL DEFAULT TRUE
);

-- Criando a tabela ImagemProduto para armazenar imagens relacionadas aos produtos
CREATE TABLE ImagemProduto (
    idImagem INT AUTO_INCREMENT PRIMARY KEY,
    caminhoImagem VARCHAR(255) NOT NULL, -- Caminho da imagem no sistema
    idProduto INT NOT NULL, -- Chave estrangeira para vincular ao produto
    isPrincipal BOOLEAN NOT NULL DEFAULT FALSE, -- Indica se a imagem é a principal
    FOREIGN KEY (idProduto) REFERENCES Produto(idProduto) -- Relacionamento com a tabela Produto
);

-- Inserindo um registro na tabela Usuario
INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicaoDoUsuario) 
VALUES ('Admin', 12345678900, 'admin@admin.com.br', 'Administrador', '12345', TRUE);

-- Inserindo um registro na tabela Produto
INSERT INTO Produto (nome, avaliacao, descricao, preco, quantidadeEmEstoque, condicaoDoProduto) 
VALUES ('Relógio Feminino Lince Urban', 2, 'O relógio feminino da marca Lince é um acessório elegante e sofisticado que certamente chamará a atenção.', 400.00, 50, TRUE);

-- Inserindo imagens para o produto na tabela ImagemProduto
INSERT INTO ImagemProduto (caminhoImagem, idProduto, isPrincipal) 
VALUES ('/imagens/relogio_feminino_lince_urban_01.jpg', 1, TRUE),
       ('/imagens/relogio_feminino_lince_urban_02.jpg', 1, FALSE);

-- Selecionando todos os registros da tabela Usuario
SELECT * FROM Usuario;

-- Selecionando todos os registros da tabela Produto
SELECT * FROM Produto;

-- Selecionando todos os registros da tabela ImagemProduto
SELECT * FROM ImagemProduto;
