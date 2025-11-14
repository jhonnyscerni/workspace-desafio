-- ======================================================================
-- Script de Inicialização de Dados - Servants Management System
-- ======================================================================
-- Este script popula o banco de dados com dados de exemplo.
-- Execute manualmente em produção ou via docker-compose exec
--
-- Uso:
--   docker-compose exec postgres psql -U postgres -d servants_db -f /init-data.sql
-- ======================================================================

-- Inserir Secretarias (se não existirem)
INSERT INTO secretarias (id, nome, sigla, created_at, updated_at)
SELECT 1, 'Secretaria Municipal de Educação', 'SEMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM secretarias WHERE id = 1);

INSERT INTO secretarias (id, nome, sigla, created_at, updated_at)
SELECT 2, 'Secretaria Municipal de Saúde', 'SEMSA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM secretarias WHERE id = 2);

INSERT INTO secretarias (id, nome, sigla, created_at, updated_at)
SELECT 3, 'Secretaria Municipal de Obras e Urbanismo', 'SEMOB', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM secretarias WHERE id = 3);

INSERT INTO secretarias (id, nome, sigla, created_at, updated_at)
SELECT 4, 'Secretaria Municipal de Fazenda', 'SEMFAZ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM secretarias WHERE id = 4);

INSERT INTO secretarias (id, nome, sigla, created_at, updated_at)
SELECT 5, 'Secretaria Municipal de Assistência Social', 'SEMAS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM secretarias WHERE id = 5);

-- Ajustar sequence
SELECT setval('secretarias_id_seq', (SELECT MAX(id) FROM secretarias));

-- Inserir Servidores (se não existirem)
INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 1, 'João Silva Santos', 'joao.silva@prefeitura.gov.br', '1985-03-15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 1);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 2, 'Maria Oliveira Costa', 'maria.oliveira@prefeitura.gov.br', '1990-07-22', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 2);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 3, 'Pedro Souza Lima', 'pedro.souza@prefeitura.gov.br', '1988-11-05', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 3);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 4, 'Ana Paula Ferreira', 'ana.ferreira@prefeitura.gov.br', '1992-04-18', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 4);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 5, 'Carlos Eduardo Alves', 'carlos.alves@prefeitura.gov.br', '1987-09-30', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 5);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 6, 'Juliana Martins Rocha', 'juliana.martins@prefeitura.gov.br', '1995-01-12', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 6);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 7, 'Ricardo Pereira Dias', 'ricardo.pereira@prefeitura.gov.br', '1983-06-25', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 7);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 8, 'Fernanda Costa Ribeiro', 'fernanda.costa@prefeitura.gov.br', '1991-12-08', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 8);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 9, 'Bruno Henrique Cardoso', 'bruno.cardoso@prefeitura.gov.br', '1989-02-14', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 9);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 10, 'Patricia Gomes Nascimento', 'patricia.gomes@prefeitura.gov.br', '1994-08-20', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 10);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 11, 'Marcos Vinicius Araújo', 'marcos.araujo@prefeitura.gov.br', '1986-05-03', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 11);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 12, 'Camila Rodrigues Batista', 'camila.rodrigues@prefeitura.gov.br', '1993-10-17', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 12);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 13, 'Roberto Carlos Teixeira', 'roberto.teixeira@prefeitura.gov.br', '1984-03-28', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 13);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 14, 'Luciana Mendes Pinto', 'luciana.mendes@prefeitura.gov.br', '1996-07-09', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 14);

INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at)
SELECT 15, 'André Luiz Barbosa', 'andre.barbosa@prefeitura.gov.br', '1990-11-22', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM servidores WHERE id = 15);

-- Ajustar sequence
SELECT setval('servidores_id_seq', (SELECT MAX(id) FROM servidores));

-- Confirmar inserções
SELECT 'Secretarias inseridas:' AS message, COUNT(*) AS total FROM secretarias;
SELECT 'Servidores inseridos:' AS message, COUNT(*) AS total FROM servidores;
