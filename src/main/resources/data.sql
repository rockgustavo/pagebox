-- Inserção de dados na tabela de diretórios
INSERT INTO directories (name, parent_id, created_at) VALUES ('dir1', NULL, CURRENT_TIMESTAMP);  -- Diretório root
INSERT INTO directories (name, parent_id, created_at) VALUES ('dir2', NULL, CURRENT_TIMESTAMP);  -- Diretório root
INSERT INTO directories (name, parent_id, created_at) VALUES ('subdir1', 2, CURRENT_TIMESTAMP);  -- Subdiretório dentro de dir2

-- Inserção de dados na tabela de arquivos
INSERT INTO files (name, content, directory_id, created_at) VALUES ('file1', 'https://unsplash.com/pt-br/fotografias/dois-iphones-lado-a-lado-em-um-fundo-cinza-lDWTfYhZ85w', 1, CURRENT_TIMESTAMP); -- Arquivo dentro de dir1
INSERT INTO files (name, content, directory_id, created_at) VALUES ('file2', 'https://unsplash.com/pt-br/fotografias/um-tunel-de-metro-vazio-com-um-relogio-na-parede-sjaQTvScVyQ', 1, CURRENT_TIMESTAMP); -- Arquivo dentro de dir1
INSERT INTO files (name, content, directory_id, created_at) VALUES ('file3', 'https://unsplash.com/pt-br/fotografias/uma-mesa-com-um-prato-de-comida-e-uma-xicara-de-cafe-Oty3XVeR-qc', 3, CURRENT_TIMESTAMP); -- Arquivo dentro dir2 no subdir1
