CREATE DATABASE elegantime;

USE elegantime;

CREATE TABLE Usuario (
    IdUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf BIGINT NOT NULL,
    email VARCHAR(100) NOT NULL,
    grupo ENUM('Administrador', 'Estoquista') NOT NULL,
    senha INT NOT NULL
);

-- Colocar as informacoes na tabela --
INSERT INTO Usuario (nome, cpf, email, grupo, senha) 
VALUES ('Admin', 12345678900, 'admin@admin.com.br', 'Administrador', 1234);

select * from Usuario;
