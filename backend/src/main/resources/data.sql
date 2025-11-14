-- Inserir Secretarias
INSERT INTO secretarias (id, nome, sigla, created_at, updated_at) VALUES
(1, 'Secretaria Municipal de Educação', 'SEMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Secretaria Municipal de Saúde', 'SEMSA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Secretaria Municipal de Obras e Urbanismo', 'SEMOB', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Secretaria Municipal de Fazenda', 'SEMFAZ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Secretaria Municipal de Assistência Social', 'SEMAS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir Servidores
INSERT INTO servidores (id, nome, email, data_nascimento, secretaria_id, created_at, updated_at) VALUES
-- SEMED
(1, 'João Silva Santos', 'joao.silva@semed.gov.br', '1985-03-15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Maria Oliveira Lima', 'maria.oliveira@semed.gov.br', '1990-07-22', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Pedro Henrique Costa', 'pedro.costa@semed.gov.br', '1978-11-08', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- SEMSA
(4, 'Ana Paula Ferreira', 'ana.ferreira@semsa.gov.br', '1988-05-30', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Carlos Eduardo Alves', 'carlos.alves@semsa.gov.br', '1975-09-12', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Juliana Rodrigues Souza', 'juliana.souza@semsa.gov.br', '1992-01-25', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Roberto Santos Pereira', 'roberto.pereira@semsa.gov.br', '1980-06-18', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- SEMOB
(8, 'Fernanda Martins Silva', 'fernanda.silva@semob.gov.br', '1987-10-03', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Ricardo Gomes Barbosa', 'ricardo.barbosa@semob.gov.br', '1983-04-27', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- SEMFAZ
(10, 'Patrícia Almeida Santos', 'patricia.santos@semfaz.gov.br', '1991-08-14', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Leonardo Carvalho Dias', 'leonardo.dias@semfaz.gov.br', '1986-12-09', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Camila Ferreira Costa', 'camila.costa@semfaz.gov.br', '1989-02-20', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- SEMAS
(13, 'Rafael Silva Oliveira', 'rafael.oliveira@semas.gov.br', '1984-07-11', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Beatriz Santos Lima', 'beatriz.lima@semas.gov.br', '1993-03-28', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'Gabriel Rodrigues Souza', 'gabriel.souza@semas.gov.br', '1981-11-16', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Resetar as sequências de auto-incremento para os próximos IDs
ALTER TABLE secretarias ALTER COLUMN id RESTART WITH 6;
ALTER TABLE servidores ALTER COLUMN id RESTART WITH 16;
