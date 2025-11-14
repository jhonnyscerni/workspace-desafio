# Como Popular o Banco de Dados em Produção

O banco PostgreSQL no ambiente Docker inicia **vazio** por padrão. Este guia mostra como inserir dados de exemplo.

## Método 1: Script SQL Automático (Recomendado)

```bash
# 1. Copiar script para o container PostgreSQL
docker cp backend/init-data.sql servants-postgres:/init-data.sql

# 2. Executar script no banco
docker-compose exec postgres psql -U postgres -d servants_db -f /init-data.sql

# 3. Verificar inserção
docker-compose exec postgres psql -U postgres -d servants_db -c "SELECT COUNT(*) FROM secretarias;"
docker-compose exec postgres psql -U postgres -d servants_db -c "SELECT COUNT(*) FROM servidores;"
```

## Método 2: Via Interface Web

1. Acesse **http://localhost**
2. Navegue para **Secretarias**
3. Clique em **"+ Nova Secretaria"**
4. Cadastre as secretarias manualmente
5. Navegue para **Servidores**
6. Cadastre os servidores vinculando às secretarias

## Método 3: Via API (curl)

### Criar Secretarias

```bash
# SEMED
curl -X POST http://localhost:8080/api/secretarias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Secretaria Municipal de Educação","sigla":"SEMED"}'

# SEMSA
curl -X POST http://localhost:8080/api/secretarias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Secretaria Municipal de Saúde","sigla":"SEMSA"}'

# SEMOB
curl -X POST http://localhost:8080/api/secretarias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Secretaria Municipal de Obras e Urbanismo","sigla":"SEMOB"}'

# SEMFAZ
curl -X POST http://localhost:8080/api/secretarias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Secretaria Municipal de Fazenda","sigla":"SEMFAZ"}'

# SEMAS
curl -X POST http://localhost:8080/api/secretarias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Secretaria Municipal de Assistência Social","sigla":"SEMAS"}'
```

### Criar Servidores

```bash
# Exemplo: Servidor na SEMED (secretariaId: 1)
curl -X POST http://localhost:8080/api/servidores \
  -H "Content-Type: application/json" \
  -d '{
    "nome":"João Silva Santos",
    "email":"joao.silva@prefeitura.gov.br",
    "dataNascimento":"1985-03-15",
    "secretariaId":1
  }'
```

## Método 4: SQL Direto no PostgreSQL

```bash
# Conectar ao PostgreSQL
docker-compose exec postgres psql -U postgres -d servants_db

# Dentro do psql, execute:
INSERT INTO secretarias (nome, sigla, created_at, updated_at)
VALUES ('Secretaria Municipal de Educação', 'SEMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# Listar secretarias
SELECT * FROM secretarias;

# Sair
\q
```

## Dados de Exemplo Incluídos no Script

### 5 Secretarias
1. **SEMED** - Secretaria Municipal de Educação
2. **SEMSA** - Secretaria Municipal de Saúde
3. **SEMOB** - Secretaria Municipal de Obras e Urbanismo
4. **SEMFAZ** - Secretaria Municipal de Fazenda
5. **SEMAS** - Secretaria Municipal de Assistência Social

### 15 Servidores
- 3 servidores na SEMED
- 3 servidores na SEMSA
- 3 servidores na SEMOB
- 3 servidores na SEMFAZ
- 3 servidores na SEMAS

## Verificação

Após popular o banco, verifique via:

```bash
# API
curl http://localhost:8080/api/secretarias
curl http://localhost:8080/api/servidores

# Ou acesse via browser
http://localhost
```

## Resetar Banco de Dados

Para começar do zero:

```bash
# Parar containers e remover volumes
docker-compose down -v

# Iniciar novamente
docker-compose up -d

# Popular novamente (se desejar)
docker cp backend/init-data.sql servants-postgres:/init-data.sql
docker-compose exec postgres psql -U postgres -d servants_db -f /init-data.sql
```
