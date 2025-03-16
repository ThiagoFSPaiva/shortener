# 📌 URL Shortener - TDS Company

Bem-vindo ao projeto **URL Shortener**, desenvolvido para o desafio backend da **TDS Company**. 

## 📜 Sobre o Projeto
Essa API REST permite o encurtamento de URLs e o rastreamento do número de acessos.

### ✅ Funcionalidades Implementadas:
- **Cadastrar URL**: Retorna uma URL encurtada.
- **Acessar URL encurtada**: Redireciona para a URL original.
- **Visualizar estatísticas**: Exibe o total de acessos e a média de acessos por dia.
- **Swagger UI**: Documentação interativa da API.
- **Versionamento da API**.
- **Teste Unitário**.

---

## 🏗 Arquitetura do Projeto
Este projeto segue a **Arquitetura Hexagonal**, organizando o código da seguinte maneira:

```text
./src/main/java/br/com/thiagofspaiva/shortener
├── adapters
│   ├── inbound
│   │   └── controller
│   ├── outbound
│   │   ├── strategy
│   │   └── repository
├── application
│   ├── dto
│   ├── factory
│   └── service
├── core
│   ├── domain
│   ├── enums
│   └── ports
```

## 📌 Camadas  

- `application` contém os serviços da aplicação, que implementam a lógica de negócio. Inclui fábricas e DTOs para organizar os dados e operações essenciais.  
- `adapters` contém as implementações que conectam a aplicação ao mundo externo, como controladores REST (inbound) e repositórios de persistência (outbound).  
- `core` contém os modelos de domínio, regras de negócio e interfaces de repositórios, garantindo a independência da lógica de negócio.  

## 🛠 Tecnologias Utilizadas
- **Java 17 + Spring Boot**
- **PostgreSQL**
- **Docker e Docker Compose**
- **Arquitetura Hexagonal**
- **Padrões Strategy, Decorator e Factory Method**
- **Swagger para documentação**
- **Junit + Mockito para teste unitário**

---

## 🚀 Como Executar o Projeto

### **Pré-requisitos**
Certifique-se de ter instalados:
- **JDK 17**
- **Maven**
- **Docker e Docker Compose**

### **Passos para rodar a aplicação**

1️⃣ **Clonar o repositório**:
```bash
git clone https://github.com/ThiagoFSPaiva/shortener.git
```

2️⃣ **Compilar o projeto**:
```bash
mvn clean package
```

3️⃣ **Subir os containers Docker**:
```bash
docker-compose up --build
```

4️⃣ **Acesse a API**:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔍 Endpoints Principais

### 🔹 **Encurtar uma URL**
```http
POST /api/v1/urls/shorten
```
**Parâmetros:**
```json
{
  "url": "https://www.exemplo.com"
}
```
**Resposta:**
```json
{
  "shortUrl": "http://localhost:8080/abcd123"
}
```

### 🔹 **Acessar URL encurtada**
```http
GET /{shortUrl}
```
**Resposta:** Redireciona para a URL original.

### 🔹 **Obter estatísticas**
```http
GET /api/v1/urls/stats/{shortUrl}
```
**Resposta:**
```json
{
  "totalAccess": 10,
  "averageAccessPerDay": 2.5
}
```

---

## 📌 Considerações Finais

O código está organizado seguindo boas práticas, SOLID e padrões de design, garantindo escalabilidade e manutenção simplificada. 

- Explore o código-fonte para entender melhor as implementações.
- Use o **Swagger UI** para testar os endpoints de maneira interativa.

