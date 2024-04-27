# Avaliação para admissão de Desenvolvedores para a Neurotech

# Projeto de Avaliação de Crédito - Neurotech

Este projeto é uma API RESTful desenvolvida com Spring Boot, que visa avaliar e aplicar diferentes modalidades de crédito para clientes pessoa física com base em critérios específicos.

## Funcionalidades

- **Cadastro de Clientes:** Permite o cadastro de clientes, armazenando informações como nome, idade e renda.
- **Consulta de Clientes:** Permite consultar os dados de um cliente específico por ID.
- **Verificação de Elegibilidade:** Verifica a elegibilidade de um cliente para contratação de credito automotivo.
- **Deleta Cliente:** Deleta um cliente específico por ID.
- **Verificação de Tipo de Crédito:** Verifica o tipo de crédito definido para o cliente com base em critérios como idade e renda.
- **Consulta de Perfis com Elegibilidade para Veiculos do tipo Hatch e Suv:** Retorna uma lista de clientes elegíveis para crédito automotivo do tipo Hatch e Suv.
- **Testes Automatizados:** Inclui testes unitários e de integração para garantir a qualidade do código.
- **Dockerização:** A aplicação está preparada para ser executada em contêineres Docker, facilitando o deploy e a escalabilidade.

## Tecnologias Utilizadas

- **Spring Boot:** Framework para desenvolvimento de aplicações Java.
- **Java:** Versão 21.
- **Maven:** Gerenciador de dependências e build.
- **H2 Database:** Banco de dados em memória para facilitar os testes e desenvolvimento.
- **Docker:** Suporte a contêineres para facilitar o deploy e a execução em diferentes ambientes.

## Configuração e Execução

### Pré-Requisitos

Antes de começar, você precisa ter instalado em sua máquina as seguintes ferramentas:
- Git
- JDK 21
- Maven
- Docker (opcional para execução em contêineres)

### Clonando o Projeto

```bash
git clone https://url_para_seu_projeto/repo.git
cd nome_do_projeto
```

### Executando o Projeto

Para executar o projeto, você pode usar o Maven ou o Docker.

#### Usando Maven
```bash
mvn spring-boot:run
```
### Usando Docker

Para executar o projeto em um contêiner Docker, você pode usar o comando abaixo:

```bash
docker build -t myapplication .  
docker run -p 5000:5000 myapplication
```

### Acessando a Documentação da API
A Documentação da API pode ser acessada em `http://localhost:5000/swagger-ui/index.html`.

### Executando testes

Para executar os testes unitários e de integração, utilize o comando abaixo:

```bash
mvn -Dtest=ClientControllerTest test
mvn -Dtest=CreditControllerTest test
mvn -Dtest=NeurotechClientRepositoryTests test
mvn -Dtest=ClientServiceImplTest test
mvn -Dtest=CreditServiceImplTest test
```

### OBSERVAÇÃO

A regra de negocio utilizada na montagem da avaliação abre margens para melhorias e erros:
A regra montada para a definição dos tipos de crédito abre margem para perfis de clientes que não se encaixam em nenhuma das modalidades de crédito.
E a mesma regra entra em conflito com a regra de negocio do endpoint extra.