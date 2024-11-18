
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

-- Criação da tabela Cliente
CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
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
    data_Nascimento VARCHAR(100) NOT NULL,          -- Novo campo para a data de nascimento
    genero VARCHAR(10) NOT NULL,           -- Novo campo para gênero
    cep VARCHAR(10) NOT NULL,              -- Novo campo para CEP
    endereco_Entrega VARCHAR(255) NOT NULL -- Novo campo para endereço de entrega
);

-- Criação da tabela Carrinho
CREATE TABLE Carrinho (
    id_Carrinho INT AUTO_INCREMENT PRIMARY KEY,
    id_Cliente INT NOT NULL,
    valor_frete DOUBLE(10,2) DEFAULT 0.00,   -- Campo para o valor do frete
    total DOUBLE(10,2) DEFAULT 0.00,   -- Campo para o valor total (produto + frete)
    FOREIGN KEY (id_Cliente) REFERENCES Cliente(id)
);


-- Criação da tabela ItemCarrinho
CREATE TABLE Item_Carrinho (
    id_item_carrinho INT AUTO_INCREMENT PRIMARY KEY,
    id_carrinho INT NOT NULL,
    id_Produto INT NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (id_carrinho) REFERENCES Carrinho(id_Carrinho),
    FOREIGN KEY (id_Produto) REFERENCES Produto(id_Produto)
);

-- Criação da tabela Pagamento com os campos que podem ser nulos dependendo do tipo de pagamento
-- Criação da tabela Pagamento com campos que aceitam nulos dependendo do tipo de pagamento
CREATE TABLE Pagamento (
    id_Pagamento INT AUTO_INCREMENT PRIMARY KEY,
    id_Cliente INT NOT NULL,                       -- ID do Cliente, chave estrangeira
    tipo_Pagamento VARCHAR(20) NOT NULL,             -- Tipo de pagamento (cartão ou boleto)
    numero_Cartao VARCHAR(16) DEFAULT NULL,         -- Número do cartão de crédito (16 dígitos), pode ser nulo
    codigoCVV VARCHAR(3) DEFAULT NULL,             -- Código CVV do cartão (3 dígitos), pode ser nulo
    nome_Cartao VARCHAR(100) DEFAULT NULL,          -- Nome do titular do cartão, pode ser nulo
    data_Vencimento VARCHAR(50) DEFAULT NULL,        -- Data de vencimento do cartão (MM/AA), pode ser nulo
    parcelas INT DEFAULT NULL,                     -- Quantidade de parcelas, pode ser nulo (para boleto será nulo)
    valor DOUBLE(10,2) NOT NULL,                   -- Valor do pagamento
    condicao_Do_Pagamento BOOLEAN NOT NULL DEFAULT TRUE,  -- Status do pagamento (ex: pago ou não pago)
    FOREIGN KEY (id_Cliente) REFERENCES Cliente(id) -- Relacionamento com o Cliente
);

CREATE TABLE Pedido (
    id_Pedido INT AUTO_INCREMENT PRIMARY KEY,         -- Identificador único do pedido
    id_Cliente INT NOT NULL,                           -- ID do Cliente (chave estrangeira)
    id_Carrinho INT NOT NULL,                          -- ID do Carrinho (chave estrangeira)
    id_Pagamento INT,                                  -- ID do Pagamento (opcional, pode ser nulo)
    status VARCHAR(20) DEFAULT 'Pendente',             -- Status do pedido (Pendente, Processado, Enviado, etc)
    
    FOREIGN KEY (id_Cliente) REFERENCES Cliente(id),   -- Relacionamento com Cliente
    FOREIGN KEY (id_Carrinho) REFERENCES Carrinho(id_Carrinho), -- Relacionamento com Carrinho
    FOREIGN KEY (id_Pagamento) REFERENCES Pagamento(id_Pagamento) -- Relacionamento com Pagamento (opcional)
);




-- Inserção de dados na tabela Usuario
INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicao_Do_Usuario) 
VALUES 
('Admin', '68112417059', 'admin@admin.com', 'administrador', '12345', TRUE),
('Estoquista', '68112417022', 'estoquista@estoquista.com', 'estoquista', '12345', TRUE);

-- Inserção de dados na tabela Produto
INSERT INTO Produto (nome, avaliacao, descricao, preco, quantidade_Em_Estoque, condicao_Do_Produto) 
VALUES 
('Relógio Feminino Lince Urban', 2, 'O relógio feminino da marca Lince é um acessório elegante e sofisticado que certamente chamará a atenção.', 400.00, 50, TRUE),
('Relógio Masculino Lince Urban', 4, 'Relógio masculino Lince, perfeito para qualquer ocasião.', 450.00, 30, TRUE),
('Relógio Digital Lince', 3, 'Relógio digital com diversas funcionalidades.', 300.00, 20, TRUE);

-- Inserção de dados na tabela Imagem
INSERT INTO Imagem(caminho, nome, id_Produto, principal) 
VALUES 
('img/img1.jpg', 'imagem1.jpg', 1, TRUE),
('img/img2.jpg', 'imagem2.jpg', 1, FALSE),
('img/img3.jpg', 'imagem3.jpg', 2, TRUE),
('img/img4.jpg', 'imagem4.jpg', 3, TRUE);

-- Inserção de dados na tabela Cliente
INSERT INTO Cliente (nome, cpf, email, senha, logradouro, numero, complemento, bairro, cidade, uf, condicao_Do_Cliente, data_Nascimento, genero, cep, endereco_Entrega) 
VALUES 
('João Silva', '12345678909', 'joao.silva@email.com', 'senha123', 'Rua das Flores', '100', 'Apto 202', 'Centro', 'São Paulo', 'SP', TRUE, '1985-10-01', 'Masculino', '01010000', 'Rua das Flores, 100');

-- Consulta todos os registros das tabelas
SELECT * FROM Usuario;
SELECT * FROM Produto;
SELECT * FROM Imagem;
SELECT * FROM Cliente;
SELECT * FROM Carrinho;
SELECT * FROM Item_Carrinho;
SELECT * FROM Pagamento;
SELECT * FROM Pedido;
