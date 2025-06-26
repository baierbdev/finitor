# 💰 Finitor - Gerenciador de Finanças Pessoais

Finitor é uma aplicação web full stack para controle financeiro pessoal. Com ele, você pode cadastrar receitas e despesas, visualizar saldo disponível.

## 📦 Estrutura do Projeto

```bash
finitor/
├── backend/             # Projeto Java com Spring Boot
├── frontend/            # Projeto Angular
└── README.md
````

---

## ⚙️ Requisitos

* Java 17+
* Node.js 18+
* Angular CLI (`npm install -g @angular/cli`)
* Docker e Docker Compose
* Maven

---

## 🚀 Executando o Projeto

### 🔁 Backend (Spring Boot)

1. **Acesse a pasta do backend** (caso esteja dentro de um subdiretório):

   ```bash
   cd backend
   ```

2. **Execute a aplicação com Maven**:

   ```bash
   ./mvn spring-boot:run
   ```

3. O backend será iniciado em: `http://localhost:8080`


### 🌐 Frontend (Angular)

1. **Acesse a pasta do frontend**:

   ```bash
   cd frontend
   ```

2. **Instale as dependências**:

   ```bash
   npm install
   ```

3. **Inicie o servidor de desenvolvimento**:

   ```bash
   ng serve
   ```

4. Acesse a aplicação em: `http://localhost:4200`

---


## 🛠 Tecnologias

* **Backend**: Java 17, Spring Boot, Spring Security, JPA, JWT
* **Frontend**: Angular 17, TypeScript, RxJS
* **Banco de Dados**: PostgreSQL (via Docker)
* **Autenticação**: JWT

---



