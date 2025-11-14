# Guia de Deploy com Docker - Servants Management System

Sistema completo de containerização para o Sistema de Gestão de Servidores Municipais.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Pré-requisitos](#pré-requisitos)
- [Arquitetura](#arquitetura)
- [Configuração Rápida](#configuração-rápida)
- [Comandos Docker](#comandos-docker)
- [Endpoints e Acessos](#endpoints-e-acessos)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Desenvolvimento Local](#desenvolvimento-local)
- [Troubleshooting](#troubleshooting)
- [Monitoramento e Health Checks](#monitoramento-e-health-checks)

---

## 🎯 Visão Geral

O deploy utiliza **Docker Compose** para orquestrar 3 containers:

1. **PostgreSQL 15** - Banco de dados relacional
2. **Spring Boot Backend** - API REST em Java 17
3. **Angular + Nginx Frontend** - Interface web com proxy reverso

### Características

✅ Multi-stage builds otimizados
✅ Health checks integrados
✅ Persistência de dados com volumes
✅ Profiles separados (dev/prod)
✅ Nginx como reverse proxy
✅ Network isolada para comunicação entre containers
✅ Configuração via variáveis de ambiente

---

## 📦 Pré-requisitos

### Software Necessário

- **Docker**: versão 20.10 ou superior
- **Docker Compose**: versão 2.0 ou superior

### Verificar Instalação

```bash
docker --version
docker-compose --version
```

### Portas Requeridas

Certifique-se de que as seguintes portas estejam disponíveis:

- `80` - Frontend (Nginx)
- `8080` - Backend (Spring Boot)
- `5432` - PostgreSQL

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                     Docker Network                       │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Frontend   │  │   Backend    │  │  PostgreSQL  │  │
│  │  Angular 19  │→→│ Spring Boot  │→→│      15      │  │
│  │   + Nginx    │  │   Java 17    │  │              │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
│       :80              :8080             :5432          │
└─────────────────────────────────────────────────────────┘
         ↓                  ↓                 ↓
    Proxy /api/*       REST API          Persistent
       para              JSON           Volume Storage
      Backend         Validation
```

### Fluxo de Requisições

1. **Usuário** acessa `http://localhost` (porta 80)
2. **Nginx** serve arquivos estáticos do Angular
3. Requisições `/api/*` são **proxy** para `http://backend:8080/api/*`
4. **Backend** processa e consulta **PostgreSQL**
5. Resposta retorna através do proxy para o usuário

---

## 🚀 Configuração Rápida

### 1. Criar Arquivo de Variáveis

```bash
# Copiar template de variáveis de ambiente
cp .env.example .env
```

Edite `.env` conforme necessário:

```env
DB_NAME=servants_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_PORT=5432
BACKEND_PORT=8080
FRONTEND_PORT=80
SPRING_PROFILES_ACTIVE=prod
```

### 2. Build e Start

```bash
# Build de todas as imagens e start dos containers
docker-compose up --build
```

**Aguarde aproximadamente 3-5 minutos** para:
- Download das imagens base
- Build do backend (Maven)
- Build do frontend (npm)
- Inicialização dos serviços

### 3. Verificar Status

```bash
# Ver logs de todos os containers
docker-compose logs -f

# Verificar health checks
docker ps
```

### 4. Acessar Aplicação

Abra o navegador em: **http://localhost**

---

## 🌐 Endpoints e Acessos

### Frontend

| Endpoint | Descrição |
|----------|-----------|
| http://localhost | Aplicação Angular |
| http://localhost/secretarias | Gestão de Secretarias |
| http://localhost/servidores | Gestão de Servidores |

### Backend API

| Endpoint | Descrição |
|----------|-----------|
| http://localhost:8080/api/secretarias | CRUD Secretarias |
| http://localhost:8080/api/servidores | CRUD Servidores |
| http://localhost:8080/swagger-ui.html | Documentação Swagger |
| http://localhost:8080/api-docs | OpenAPI JSON |
| http://localhost:8080/health | Health Check |
| http://localhost:8080/ready | Readiness Probe |
| http://localhost:8080/info | Informações da Aplicação |

### Database

```bash
# Conectar ao PostgreSQL
docker-compose exec postgres psql -U postgres -d servants_db

# Comandos úteis dentro do psql
\dt          # Listar tabelas
\d secretaria # Descrever tabela
SELECT * FROM secretaria;
\q           # Sair
```

---

## ⚙️ Variáveis de Ambiente

### Arquivo `.env`

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `DB_NAME` | servants_db | Nome do banco de dados |
| `DB_USERNAME` | postgres | Usuário do PostgreSQL |
| `DB_PASSWORD` | postgres | Senha do PostgreSQL |
| `DB_PORT` | 5432 | Porta do PostgreSQL no host |
| `BACKEND_PORT` | 8080 | Porta do backend no host |
| `FRONTEND_PORT` | 80 | Porta do frontend no host |
| `SPRING_PROFILES_ACTIVE` | prod | Profile Spring (dev/prod) |

### Sobrescrever Valores

```bash
# Via linha de comando
DB_PASSWORD=senhasegura docker-compose up

# Ou editando .env antes de iniciar
```

---

## 💻 Desenvolvimento Local

### Modo Desenvolvimento (sem Docker)

#### Backend

```bash
cd backend

# Com H2 (perfil dev - padrão)
./mvnw spring-boot:run

# Acessos:
# API: http://localhost:8080
# H2 Console: http://localhost:8080/h2-console
# Swagger: http://localhost:8080/swagger-ui.html
```

#### Frontend

```bash
cd frontend

# Instalar dependências
npm install

# Start dev server
npm start

# Acesso: http://localhost:4200
```

### Modo Híbrido

```bash
# Apenas PostgreSQL via Docker
docker-compose up postgres

# Backend local conectando ao PostgreSQL
cd backend
SPRING_PROFILES_ACTIVE=prod \
DB_HOST=localhost \
./mvnw spring-boot:run

# Frontend local
cd frontend
npm start
```

---


## 📊 Monitoramento e Health Checks

### Health Check Endpoints

#### Backend

```bash
# Status básico
curl http://localhost:8080/health

# Readiness (inclui check de DB)
curl http://localhost:8080/ready

# Informações detalhadas
curl http://localhost:8080/info
```

#### Frontend

```bash
# Verificar se Nginx está respondendo
curl http://localhost/

# Status code 200 = OK
```

#### PostgreSQL

```bash
# Dentro do container
docker-compose exec postgres pg_isready -U postgres

# Via psql
docker-compose exec postgres psql -U postgres -c "SELECT version();"
```
---

## 🔐 Segurança

### Boas Práticas Implementadas

✅ **Non-root users** em todos os containers
✅ **Multi-stage builds** para imagens menores
✅ **Secrets via environment variables**
✅ **Network isolation** entre containers
✅ **Health checks** para detectar problemas
✅ **Read-only file systems** onde possível

### Recomendações para Produção

1. **Alterar senhas padrão** no arquivo `.env`
2. **Usar secrets management** (Docker Secrets, Vault)
3. **Habilitar HTTPS** com certificados SSL
4. **Configurar firewall** e limitar portas expostas
5. **Implementar rate limiting** no Nginx
6. **Adicionar autenticação** nas APIs
7. **Configurar backup** automático do PostgreSQL


---

## 📝 Notas Adicionais

### Tamanho das Imagens

- **Backend**: ~350MB (JRE 17 + JAR)
- **Frontend**: ~25MB (Nginx Alpine + Angular build)
- **PostgreSQL**: ~250MB (PostgreSQL 15 Alpine)

### Dados Iniciais

O banco é populado automaticamente com:
- **5 Secretarias**: SEMED, SEMSA, SEMOB, SEMFAZ, SEMAS
- **15 Servidores**: Distribuídos entre as secretarias

---

**Desenvolvido com ❤️ para a Prefeitura Municipal**
