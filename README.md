# 📦 Estoque API

API REST para gerenciamento de estoque desenvolvida com Java e Spring Boot.

O projeto foi criado com foco em:

* organização em camadas
* regras de negócio
* validações
* testes unitários
* documentação profissional
* boas práticas de desenvolvimento backend

---

# 🚀 Tecnologias utilizadas

* Java 17
* Spring Boot 3
* Spring Web
* Spring Data JPA
* Bean Validation
* H2 Database
* Lombok
* Swagger / OpenAPI
* JUnit 5
* Mockito
* Maven
* Git e GitHub

---

# 📁 Estrutura do projeto

```txt
src/main/java/com/jhonatan/estoque
│
├── controller
├── dto
├── exception
├── model
├── repository
├── service
│
└── EstoqueApiApplication
```

## 📌 Camadas

### Controller

Responsável por receber as requisições HTTP e retornar as respostas da API.

### Service

Contém as regras de negócio da aplicação.

### Repository

Responsável pela comunicação com o banco de dados através do Spring Data JPA.

### DTO

Objetos utilizados para transferência de dados entre cliente e API.

### Exception

Tratamento centralizado de erros da aplicação.

---

# ⚙️ Funcionalidades

## 📂 Categorias

* Criar categoria
* Listar categorias ativas
* Buscar categoria por ID
* Atualizar categoria
* Desativar categoria
* Impedir categorias duplicadas
* Impedir desativação de categorias com produtos ativos

---

## 📦 Produtos

* Criar produto
* Listar produtos ativos
* Buscar produto por ID
* Buscar produto por SKU
* Atualizar produto
* Desativar produto
* Validação de SKU único
* Associação com categorias

---

## 📊 Controle de estoque

* Registrar entrada de estoque
* Registrar saída de estoque
* Consultar saldo do produto
* Listar movimentações
* Listar movimentações por produto
* Listar produtos abaixo do estoque mínimo
* Bloquear saída com saldo insuficiente

---

# ✅ Regras de negócio implementadas

* Não permitir categorias duplicadas
* Não permitir produtos com SKU duplicado
* Não permitir saída maior que o saldo disponível
* Não permitir desativação de categoria com produtos ativos vinculados
* Retornar produtos abaixo do estoque mínimo

---

# 🛡️ Validações

A aplicação utiliza Bean Validation para validar os dados recebidos.

Exemplos:

```java
@NotBlank(message = "O nome do produto é obrigatório")
```

```java
@Positive(message = "O preço deve ser um valor positivo")
```

---

# ⚠️ Tratamento de exceções

A API possui tratamento centralizado utilizando:

```txt
@ControllerAdvice
```

Exceções implementadas:

* ConflitoException
* RecursoNaoEncontradoException
* RegraNegocioException

---

# 🧪 Testes unitários

O projeto possui testes unitários utilizando:

* JUnit 5
* Mockito

## Testes implementados

### CategoriaServiceTest

* Criar categoria com sucesso
* Bloquear categoria duplicada
* Buscar categoria por ID
* Lançar erro ao buscar categoria inexistente
* Bloquear desativação de categoria com produtos ativos

### EstoqueServiceTest

* Registrar entrada com sucesso
* Bloquear saída com saldo insuficiente
* Consultar saldo corretamente

---

# 📄 Documentação Swagger

A documentação da API pode ser acessada em:

```txt
http://localhost:8080/swagger-ui/index.html
```

---

# ▶️ Como executar o projeto

## 1. Clonar repositório

```bash
git clone https://github.com/Jhonatan-Nowicki/controle-estoque-api.git
```

---

## 2. Entrar na pasta

```bash
cd controle-estoque-api
```

---

## 3. Executar projeto

### Windows

```bash
./mvnw spring-boot:run
```

---

# 🧪 Executar testes

```bash
./mvnw test
```

---

# 🗄️ Banco de dados

O projeto utiliza banco H2 em memória.

Console H2:

```txt
http://localhost:8080/h2-console
```

---

# 📌 Exemplos de endpoints

## Criar categoria

```http
POST /api/categorias
```

```json
{
  "nome": "Eletrônicos",
  "descricao": "Produtos eletrônicos"
}
```

---

## Criar produto

```http
POST /api/produtos
```

```json
{
  "nome": "Notebook Dell",
  "descricao": "Notebook i5",
  "codigoSku": "NOTE123",
  "preco": 3500,
  "quantidadeMinima": 5,
  "categoria": {
    "id": 1
  }
}
```

---

## Registrar entrada

```http
POST /api/estoque/entrada
```

```json
{
  "produtoId": 1,
  "quantidade": 10,
  "observacao": "Entrada inicial"
}
```

---

## Registrar saída

```http
POST /api/estoque/saida
```

```json
{
  "produtoId": 1,
  "quantidade": 3,
  "observacao": "Venda"
}
```

---

# 👨‍💻 Autor

Desenvolvido por Jhonatan Nowicki.

Projeto desenvolvido para fins de estudo, prática de Spring Boot e demonstração de conhecimentos em desenvolvimento backend.
