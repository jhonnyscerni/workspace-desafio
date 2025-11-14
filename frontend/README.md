# Frontend - Sistema de Gestão de Servidores Públicos Municipais

Aplicação web desenvolvida com Angular 19 e PrimeNG para gerenciamento de servidores públicos municipais e suas respectivas secretarias.

## 🛠️ Tecnologias

- **Angular 19**
- **PrimeNG 19.0** (Componentes UI)
- **@primeng/themes 20.3** (Sistema de Temas)
- **PrimeIcons 7.0** (Ícones)
- **PrimeFlex 3.3** (CSS Utilities)
- **RxJS 7.8** (Programação Reativa)
- **TypeScript 5.5**

## 📋 Funcionalidades

### Interface de Secretarias
- ✅ Listagem com tabela paginada e ordenável
- ✅ Formulário de criação/edição com validação
- ✅ Exclusão com diálogo de confirmação
- ✅ Exportação para CSV
- ✅ Validação de sigla única (letras maiúsculas)

### Interface de Servidores
- ✅ Listagem com tabela paginada mostrando idade calculada
- ✅ Formulário de criação/edição com validação
- ✅ Seleção de secretaria via dropdown
- ✅ Calendário para seleção de data de nascimento
- ✅ Cálculo de idade em tempo real
- ✅ Validação de idade (18 a 75 anos)
- ✅ Exclusão com diálogo de confirmação
- ✅ Exportação para CSV

### UX Aprimorada
- ✅ Loading spinner global durante requisições HTTP
- ✅ Toasts de notificação (sucesso/erro)
- ✅ Validação visual de formulários
- ✅ Mensagens de erro amigáveis
- ✅ Confirmação de ações destrutivas
- ✅ Navegação intuitiva com menu

## 🏗️ Arquitetura

```
src/app/
├── core/                           # Serviços singleton e interceptors
│   ├── interceptors/
│   │   ├── error.interceptor.ts   # Tratamento global de erros
│   │   └── loading.interceptor.ts # Loading spinner automático
│   ├── models/
│   │   ├── secretaria.model.ts
│   │   ├── servidor.model.ts
│   │   └── error-response.model.ts
│   └── services/
│       ├── http.service.ts        # Wrapper do HttpClient
│       ├── loading.service.ts     # Controle de loading
│       └── notification.service.ts# Toasts (wrapper MessageService)
├── features/                       # Módulos de features
│   ├── secretaria/
│   │   ├── components/
│   │   │   ├── secretaria-list/
│   │   │   └── secretaria-form/
│   │   └── services/
│   │       └── secretaria.service.ts
│   └── servidor/
│       ├── components/
│       │   ├── servidor-list/
│       │   └── servidor-form/
│       └── services/
│           └── servidor.service.ts
├── shared/                         # Código reutilizável
│   └── validators/
│       └── age-range.validator.ts # Validador de idade
├── app.component.ts               # Componente raiz com layout
├── app.config.ts                  # Configuração da aplicação + tema PrimeNG
└── app.routes.ts                  # Rotas da aplicação
```

## 🚀 Como Executar

### Pré-requisitos
- Node.js 20+ e npm 10+
- Backend rodando em `http://localhost:8080`

### Passos

1. **Instale as dependências**:
```bash
cd frontend
npm install
```

> **Nota**: Este projeto usa PrimeNG 19 que requer Angular 19. Se você estiver usando uma versão diferente do Angular, consulte a [documentação de compatibilidade do PrimeNG](https://primeng.org).

2. **Execute a aplicação**:
```bash
npm start
```

3. **Acesse no navegador**:
```
http://localhost:4200
```

## 📡 Integração com API

A aplicação consome a API REST do backend configurada em:
- **URL Base**: `http://localhost:8080`
- **Configuração**: `src/environments/environment.ts`

### Endpoints Utilizados

**Secretarias:**
- `GET /api/secretarias` - Listar todas
- `GET /api/secretarias/{id}` - Buscar por ID
- `POST /api/secretarias` - Criar
- `PUT /api/secretarias/{id}` - Atualizar
- `DELETE /api/secretarias/{id}` - Excluir
- `GET /api/secretarias/export/csv` - Exportar CSV

**Servidores:**
- `GET /api/servidores` - Listar todos
- `GET /api/servidores/{id}` - Buscar por ID
- `POST /api/servidores` - Criar
- `PUT /api/servidores/{id}` - Atualizar
- `DELETE /api/servidores/{id}` - Excluir
- `GET /api/servidores/export/csv` - Exportar CSV

## ✅ Validações Implementadas

### Secretaria
- **Nome**: Obrigatório, máximo 100 caracteres
- **Sigla**: Obrigatória, 2-10 caracteres, apenas letras maiúsculas, única

### Servidor
- **Nome**: Obrigatório, máximo 100 caracteres
- **Email**: Obrigatório, formato válido, único
- **Data de Nascimento**: Obrigatória, idade entre 18 e 75 anos (calculada automaticamente)
- **Secretaria**: Obrigatória

## 🔄 Interceptors

### ErrorInterceptor
- Captura todos os erros HTTP
- Formata mensagens de erro do backend
- Exibe toasts de erro automaticamente
- Trata erros 400, 404, 422, 500

## 🎯 Funcionalidades Diferenciais

### 1. Cálculo de Idade em Tempo Real
Enquanto o usuário seleciona a data de nascimento, a idade é calculada e exibida instantaneamente com validação de faixa (18-75 anos).

### 2. Exportação CSV
Botão de exportação que faz download direto do CSV gerado pelo backend, incluindo todos os dados formatados.

### 3. UX Aprimorada
- Loading automático durante requisições
- Toasts informativos para todas as ações
- Confirmação antes de exclusões
- Validação visual inline nos formulários
- Empty states nas tabelas
- Tooltips nos botões de ação

### 4. Validação Customizada
Validador de idade que considera ano bissexto e calcula idade exata baseada na data atual.

## 📝 Estrutura de Componentes

### Smart Components (Containers)
- **secretaria-list.component**: Lista e gerencia secretarias
- **secretaria-form.component**: Form para criar/editar secretarias
- **servidor-list.component**: Lista e gerencia servidores
- **servidor-form.component**: Form para criar/editar servidores

### Dumb Components
Todos os componentes são standalone (Angular 19) e não utilizam NgModule.

## 🔧 Build para Produção

```bash
npm run build
```

Os arquivos otimizados serão gerados em `dist/servants-management-frontend/`.

### Deploy Sugerido
- **Frontend**: Netlify, Vercel, Firebase Hosting, AWS S3 + CloudFront
- **Configurar `environment.production.ts`** com URL da API de produção

## 📄 Licença

Projeto desenvolvido para desafio técnico.
