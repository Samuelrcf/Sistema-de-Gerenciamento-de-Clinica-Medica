# Sistema de Gestão de Atendimentos Médicos
## Visão Geral
Este projeto é um sistema para gestão de atendimentos médicos, englobando o cadastro de pacientes, médicos e agendamentos, além da autenticação de usuários para ter acesso ao sistema. O sistema permite o cadastro de novos usuários, recuperação de acesso por e-mail e autenticação usando JWT.

## Funcionalidades
### Cadastro de Usuários
- Campos: Nome, Email, Senha
- Qualquer usuário pode se cadastrar
- Usuário inicial é o administrador

### Autenticação
- Login com nome e senha
- Geração de token JWT

### Recuperação de Senha
- Envio de código de recuperação por email
- Alteração de senha usando código de recuperação

### Cadastro e Gestão de Pacientes e Médicos
- Relacionamento OneToMany entre PacienteEntity e MedicoEntity com AgendamentoMedicoEntity

### Agendamentos Médicos
- Relacionamento ManyToOne entre AgendamentoMedicoEntity com PacienteEntity e MedicoEntity

## Estrutura do Projeto
### Entidades
- PessoaEntity: Classe base com atributos comuns.
- UsuarioEntity: Representa os usuários do sistema.
- PacienteEntity e MedicoEntity: Herda de PessoaEntity.
- AgendamentoMedicoEntity: Representa os agendamentos médicos.
### Repositórios
- Utiliza JpaRepository.
- Queries JPQL para buscar projeções das entidades.
- Paginação nas buscas.
### Serviços
- Implementações seguem interfaces padronizadas.
- Exceções padronizadas e tratadas com ControllerAdvice.
- Mapeamento manual de entidades para DTOs.
### Segurança
- Sistema STATLESS usando tokens JWT.
- Implementação de TokenService para geração e validação de tokens.
- SecurityFilter para filtrar requisições e validar tokens.

## Instalação e Execução
### Pré-requisitos
- Java 17+
- Maven
- Banco de dados PostgreSQL (ou outro configurado no application.properties)

### Passos
- Clone o repositório:
-- git clone https://github.com/seu-usuario/clinic-management.git
-- cd clinic-management
- Configure o banco de dados em src/main/resources/application.properties.
- Execute o projeto:
-- mvn spring-boot:run

### Testes
- Para executar os testes unitários:
-- mvn test

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- Spring Boot Starter Mail
- Lombok
- JUnit
- Mockito
- Validation
- HATEOAS
