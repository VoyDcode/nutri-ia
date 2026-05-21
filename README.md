# 🥗 NutriAI — Assistente Nutricional Inteligente

Aplicação web completa com **Spring Boot + Angular** que utiliza **Spring AI** para oferecer funcionalidades avançadas de inteligência artificial aplicadas à nutrição.

> **Java Advanced — FIAP | Segundo Semestre | Projeto Diamante 03**

---

## 👥 Integrantes e RMs
*   **Victor** — RM560087
*   **Luan Noqueli Klochko** — RM560313
*   **Renato** — RM560928
---

---

## 🎯 Objetivo

NutriAI é uma plataforma de assistência nutricional inteligente que permite ao usuário:
- Tirar dúvidas sobre nutrição em linguagem natural
- Calcular IMC, calorias diárias e necessidade proteica via IA
- Obter análise nutricional estruturada de refeições
- Consultar uma base de conhecimento nutricional indexada
- Interagir com um agente autônomo que monta planos alimentares personalizados

---

## 🏗️ Arquitetura

```
nutri-ai-backend/   → Spring Boot 3 + Spring AI 1.0 (porta 8080)
nutri-ai-frontend/  → Angular 17 (porta 4200)
```

---

## ✅ Requisitos Técnicos Spring AI (cada um vale 20%)

### 1. 💬 Chat — `POST /api/chat`
Conversação simples com LLM (GPT-4o-mini). O assistente responde perguntas sobre nutrição com um prompt de sistema especializado.

**Serviço:** `ChatService` | **Controller:** `ChatController`

---

### 2. 🔧 Tool Calling — `POST /api/tools`
O LLM decide autonomamente quando e quais ferramentas chamar para responder ao usuário. As ferramentas disponíveis são:

| Ferramenta | Descrição |
|---|---|
| `calculateBMI` | Calcula o IMC e retorna a classificação |
| `calculateDailyCalories` | Calcula TDEE com fórmula de Mifflin-St Jeor |
| `calculateProteinNeeds` | Recomendação proteica por objetivo |
| `getFoodNutrition` | Informações nutricionais de alimentos comuns |

**Serviço:** `ToolCallingService` + `NutritionTools` | **Controller:** `ToolCallingController`

---

### 3. 📊 Structured Output — `POST /api/analysis`
O LLM retorna a análise nutricional de uma refeição como um objeto Java tipado (`NutritionAnalysis`), com lista de alimentos, macronutrientes totais, avaliação e recomendações.

**Serviço:** `StructuredOutputService` | **Controller:** `StructuredOutputController`

---

### 4. 📚 RAG — `POST /api/rag`
Retrieval-Augmented Generation com base de conhecimento nutricional indexada em um `SimpleVectorStore`. Ao iniciar, `DataLoaderService` carrega 13 documentos sobre nutrição. O `QuestionAnswerAdvisor` busca os documentos mais relevantes e os injeta no contexto da resposta.

**Serviço:** `RagService` + `DataLoaderService` | **Controller:** `RagController`

---

### 5. 🤖 Agent — `POST /api/agent`
Agente autônomo com:
- **Memória por sessão** (`InMemoryChatMemory` via `MessageChatMemoryAdvisor`)
- **Ferramentas integradas** (mesmas do Tool Calling)
- **Objetivo:** coletar dados do usuário e montar plano alimentar personalizado ao longo da conversa

**Serviço:** `AgentService` | **Controller:** `AgentController`

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.9+
- Node.js 18+ / npm
- Chave de API da OpenAI

### Backend

```bash
cd nutri-ai-backend

# Windows PowerShell
$env:OPENAI_API_KEY = "sk-..."
mvn spring-boot:run

# Linux/Mac
export OPENAI_API_KEY="sk-..."
mvn spring-boot:run
```

Acesse: `http://localhost:8080`

### Frontend

```bash
cd nutri-ai-frontend
npm install
npm start
```

Acesse: `http://localhost:4200`

> O proxy está configurado para redirecionar `/api/*` → `http://localhost:8080/api/*`, eliminando problemas de CORS em desenvolvimento.

---

## 📁 Estrutura do Projeto

```
nutri-ai-backend/src/main/java/br/com/fiap/nutriai/
├── NutriAiApplication.java
├── config/
│   ├── CorsConfig.java          # CORS para Angular
│   └── VectorStoreConfig.java   # SimpleVectorStore para RAG
├── controller/
│   ├── ChatController.java
│   ├── ToolCallingController.java
│   ├── StructuredOutputController.java
│   ├── RagController.java
│   └── AgentController.java
├── service/
│   ├── ChatService.java
│   ├── ToolCallingService.java
│   ├── StructuredOutputService.java
│   ├── RagService.java
│   ├── AgentService.java
│   └── DataLoaderService.java   # Carrega base de conhecimento no VectorStore
├── tools/
│   └── NutritionTools.java      # @Tool methods para Tool Calling e Agent
└── dto/
    ├── ChatRequest.java
    ├── ChatResponse.java
    └── NutritionAnalysis.java   # Record para Structured Output

nutri-ai-frontend/src/app/
├── services/nutri.service.ts    # HttpClient para todos os endpoints
└── components/
    ├── chat/                    # Tela de Chat
    ├── tool-calling/            # Tela de Tool Calling
    ├── analysis/                # Tela de Structured Output
    ├── rag/                     # Tela de RAG
    └── agent/                   # Tela do Agente
```

---

## 🔌 Endpoints da API

| Método | Endpoint | Funcionalidade |
|---|---|---|
| POST | `/api/chat` | Chat simples com LLM |
| POST | `/api/tools` | Chat com Tool Calling |
| POST | `/api/analysis` | Análise nutricional (Structured Output) |
| POST | `/api/rag` | Consulta à base de conhecimento (RAG) |
| POST | `/api/agent` | Agente com memória por sessão |
| DELETE | `/api/agent/{sessionId}` | Limpa sessão do agente |

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Backend | Spring Boot 3.4, Spring AI 1.0 |
| LLM | OpenAI GPT-4o-mini |
| Vector Store | SimpleVectorStore (in-memory) |
| Frontend | Angular 17 (Standalone Components) |
| Build | Maven, npm |
