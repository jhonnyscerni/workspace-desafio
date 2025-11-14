# Backend - Sistema de Gestão de Servidores Públicos Municipais

API REST desenvolvida com Spring Boot para gerenciamento de servidores públicos municipais e suas respectivas secretarias.

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Spring Validation**
- **H2 Database** (em memória)
- **Lombok**
- **MapStruct 1.5.5**
- **Maven**

## 📋 Funcionalidades

### Secretarias
- ✅ Listar todas as secretarias
- ✅ Buscar secretaria por ID
- ✅ Criar nova secretaria
- ✅ Atualizar secretaria existente
- ✅ Excluir secretaria (apenas se não tiver servidores vinculados)
- ✅ Exportar secretarias para CSV

### Servidores
- ✅ Listar todos os servidores
- ✅ Buscar servidor por ID
- ✅ Criar novo servidor
- ✅ Atualizar servidor existente
- ✅ Excluir servidor
- ✅ Exportar servidores para CSV
- ✅ Validação de idade (18 a 75 anos) calculada automaticamente

## 🏗️ Arquitetura

```
src/main/java/com/municipality/servants/
├── config/                          # Configurações (CORS)
├── core/                            # Infraestrutura core
│   ├── exception/                   # Tratamento de exceções
│   └── validation/                  # Validadores customizados
├── features/
│   ├── secretaria/                  # Feature module Secretaria
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── entity/
│   │   ├── dto/
│   │   └── mapper/
│   └── servidor/                    # Feature module Servidor
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       └── mapper/
└── ServantsManagementApplication.java
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Passos

1. **Clone o repositório** (se aplicável):
```bash
cd backend
```

2. **Compile o projeto**:
```bash
mvnw clean install
```

3. **Execute a aplicação**:
```bash
mvnw spring-boot:run
```

4. **Acesse a aplicação**:
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:servants_db`
  - Username: `sa`
  - Password: (deixe em branco)

## 📡 Endpoints da API

### Secretarias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/secretarias` | Lista todas as secretarias |
| GET | `/api/secretarias/{id}` | Busca secretaria por ID |
| POST | `/api/secretarias` | Cria nova secretaria |
| PUT | `/api/secretarias/{id}` | Atualiza secretaria |
| DELETE | `/api/secretarias/{id}` | Exclui secretaria |
| GET | `/api/secretarias/export/csv` | Exporta para CSV |

### Servidores

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/servidores` | Lista todos os servidores |
| GET | `/api/servidores/{id}` | Busca servidor por ID |
| POST | `/api/servidores` | Cria novo servidor |
| PUT | `/api/servidores/{id}` | Atualiza servidor |
| DELETE | `/api/servidores/{id}` | Exclui servidor |
| GET | `/api/servidores/export/csv` | Exporta para CSV |

## 📄 Exemplos de Request/Response

### Criar Secretaria
**POST** `/api/secretarias`
```json
{
  "nome": "Secretaria Municipal de Educação",
  "sigla": "SEMED"
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "nome": "Secretaria Municipal de Educação",
  "sigla": "SEMED",
  "createdAt": "2025-01-13T10:00:00",
  "updatedAt": "2025-01-13T10:00:00"
}
```

### Criar Servidor
**POST** `/api/servidores`
```json
{
  "nome": "João Silva Santos",
  "email": "joao.silva@semed.gov.br",
  "dataNascimento": "1985-03-15",
  "secretariaId": 1
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "nome": "João Silva Santos",
  "email": "joao.silva@semed.gov.br",
  "dataNascimento": "1985-03-15",
  "idade": 39,
  "secretaria": {
    "id": 1,
    "nome": "Secretaria Municipal de Educação",
    "sigla": "SEMED",
    "createdAt": "2025-01-13T10:00:00",
    "updatedAt": "2025-01-13T10:00:00"
  },
  "createdAt": "2025-01-13T10:05:00",
  "updatedAt": "2025-01-13T10:05:00"
}
```

## 🔒 Validações

### Secretaria
- **Nome**: Obrigatório, máximo 100 caracteres
- **Sigla**: Obrigatória, 2-10 caracteres, apenas letras maiúsculas, única no sistema

### Servidor
- **Nome**: Obrigatório, máximo 100 caracteres
- **Email**: Obrigatório, formato válido, único no sistema
- **Data de Nascimento**: Obrigatória, no passado, idade entre 18 e 75 anos
- **Secretaria**: Obrigatória, deve existir no sistema

## ⚠️ Tratamento de Erros

### 400 - Bad Request (Validação)
```json
{
  "timestamp": "2025-01-13T10:00:00",
  "message": "Validation failed",
  "errors": {
    "email": "Email inválido",
    "nome": "Nome é obrigatório"
  }
}
```

### 404 - Not Found
```json
{
  "timestamp": "2025-01-13T10:00:00",
  "message": "Servidor com ID 99 não encontrado",
  "errors": {}
}
```

### 422 - Unprocessable Entity (Regra de Negócio)
```json
{
  "timestamp": "2025-01-13T10:00:00",
  "message": "Já existe um servidor com o email 'joao@email.com'",
  "errors": {}
}
```

## 📊 Dados de Teste

A aplicação é inicializada com dados de exemplo:
- 5 Secretarias municipais
- 15 Servidores distribuídos entre as secretarias

## 🧪 Testes

Para executar os testes:
```bash
mvnw test
```

## 📝 Notas

- Banco de dados H2 em memória: **dados são perdidos ao reiniciar a aplicação**
- CORS habilitado para `http://localhost:4200` (Angular dev server)
- Logs detalhados em modo DEBUG para desenvolvimento
