-- Criando o banco de dados
CREATE DATABASE elegantime;

-- Usando o banco de dados recém-criado
USE elegantime;

-- Criando a tabela Usuario com todos os campos
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf BIGINT NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE, -- email tem que ser unico cadastrado
    grupo ENUM('Administrador', 'Estoquista') NOT NULL,
    senha VARCHAR(100) NOT NULL CHECK (LENGTH(senha) >= 5), -- senha tem que ser maior que 5
    condicaoDoUsuario BOOLEAN NOT NULL DEFAULT TRUE
);

-- Inserindo um registro na tabela Usuario
INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicaoDoUsuario) 
VALUES ('Admin', 12345678900, 'admin@admin.com.br', 'Administrador', '12345', TRUE);

-- Selecionando todos os registros da tabela Usuario
SELECT * FROM Usuario;
