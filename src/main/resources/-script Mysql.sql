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

-- Inserção de dados na tabela de diretórios
INSERT INTO directories (name, parent_id) VALUES ('dir1', NULL);  -- Diretório root
INSERT INTO directories (name, parent_id) VALUES ('dir2', NULL);  -- Diretório root
INSERT INTO directories (name, parent_id) VALUES ('subdir1', 2);  -- Subdiretório dentro de dir2

-- Inserção de dados na tabela de arquivos
INSERT INTO files (name, content, directory_id) VALUES ('file1', 'https://unsplash.com/pt-br/fotografias/dois-iphones-lado-a-lado-em-um-fundo-cinza-lDWTfYhZ85w', 1); -- Arquivo dentro de dir1
INSERT INTO files (name, content, directory_id) VALUES ('file2', 'https://unsplash.com/pt-br/fotografias/um-tunel-de-metro-vazio-com-um-relogio-na-parede-sjaQTvScVyQ', 1); -- Arquivo dentro de dir1
INSERT INTO files (name, content, directory_id) VALUES ('file3', 'https://unsplash.com/pt-br/fotografias/uma-mesa-com-um-prato-de-comida-e-uma-xicara-de-cafe-Oty3XVeR-qc', 3); -- Arquivo dentro dir2 no subdir1