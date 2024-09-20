-- Criação da tabela de diretórios
CREATE TABLE IF NOT EXISTS directories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- AUTO_INCREMENT substituído por IDENTITY para H2
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES directories(id) ON DELETE CASCADE
);

-- Criação da tabela de arquivos
CREATE TABLE IF NOT EXISTS files (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- AUTO_INCREMENT substituído por IDENTITY para H2
    name VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,  -- Usando TEXT para conteúdos grandes
    directory_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (directory_id) REFERENCES directories(id) ON DELETE CASCADE
);
