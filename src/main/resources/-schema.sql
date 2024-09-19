-- Criação do banco de dados
CREATE DATABASE pagebox;

-- Usando o banco de dados recém-criado
USE pagebox;

-- Criação da tabela de diretórios
CREATE TABLE IF NOT EXISTS directories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- AUTO_INCREMENT para IDs gerados automaticamente
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES directories(id) ON DELETE CASCADE
);

-- Criação da tabela de arquivos
CREATE TABLE IF NOT EXISTS files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- AUTO_INCREMENT para IDs gerados automaticamente
    name VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,  -- Usando TEXT para permitir conteúdos maiores
    directory_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (directory_id) REFERENCES directories(id) ON DELETE CASCADE
);