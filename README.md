# Documentação do Serviço de Plano de Serviço
```
plano-servico
│   mvnw
│   mvnw.cmd
│   pom.xml
│
└───src
    └───main
        ├───java
        │   └───com
        │       └───example
        │           └───planoservico
        │               │   PlanoServicoApplication.java
        │               │   SecurityConfig.java
        │               │
        │               ├───controller
        │               │       PlanoServicoController.java
        │               │
        │               ├───exception
        │               │       CustomException.java
        │               │       ResourceNotFoundException.java
        │               │       ErrorDetails.java
        │               │       GlobalExceptionHandler.java
        │               │
        │               ├───model
        │               │       PlanoServico.java
        │               │
        │               ├───repository
        │               │       PlanoServicoRepository.java
        │               │
        │               └───service
        │                       PlanoServicoService.java
        │
        └───resources
            │   application.properties
            │
            └───static
            └───templates

```

## PlanoServicoApplication.java
Esta é a classe principal que inicia a aplicação Spring Boot. A anotação @SpringBootApplication configura automaticamente o contexto Spring, a varredura de componentes e outras configurações essenciais.

## SecurityConfig.java
Esta classe configura a segurança da aplicação. Atualmente, está configurada para desabilitar a proteção CSRF e permitir todas as requisições sem necessidade de autenticação. Um PasswordEncoder também está definido, mas não está sendo utilizado no momento.

* **@Configuration:** Indica que a classe pode ser usada pelo Spring IoC container como uma fonte de definições de beans.
* **@Bean:** Indica que um método produz um bean gerenciado pelo Spring.
* **SecurityFilterChain securityFilterChain(HttpSecurity http):** Define a configuração de segurança da aplicação.
  * **http.csrf(csrf -> csrf.disable()):** Desabilita a proteção contra CSRF (Cross-Site Request Forgery).
  * **http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()):** Permite todas as requisições sem autenticação.
  * **http.build():** Constrói o SecurityFilterChain com as configurações especificadas.
* **PasswordEncoder passwordEncoder():** Define um codificador de senhas usando o BCrypt.

## PlanoServico.java
Esta classe representa a entidade de Plano de Serviço. Anotada com @Entity, define os campos e métodos necessários para mapear a entidade para uma tabela no banco de dados. Lombok é usado para gerar automaticamente os métodos getter, setter, toString, equals e hashCode.
* **@Entity:** Indica que a classe é uma entidade JPA.
* **@Id:** Indica o campo de chave primária.
* **@GeneratedValue(strategy = GenerationType.IDENTITY):** Define a estratégia de geração de valores para a chave primária. IDENTITY significa que o valor será gerado pelo banco de dados.
* **Lombok Annotations (@Data):** Gera automaticamente métodos getter, setter, toString, equals e hashCode.

## PlanoServicoRepository.java
Interface que estende JpaRepository para fornecer métodos CRUD para a entidade PlanoServico. Isso permite operações básicas como salvar, buscar, atualizar e deletar planos de serviço sem a necessidade de escrever SQL manualmente.

* **JpaRepository<PlanoServico, Long>:** Interface do Spring Data JPA que fornece métodos CRUD prontos para uso, como save(), findById(), findAll(), deleteById(), entre outros.

## PlanoServicoService.java
Classe de serviço que contém a lógica de negócios para manipulação de PlanoServico. Fornece métodos para criar, buscar, atualizar e deletar planos de serviço. Utiliza o PlanoServicoRepository para interagir com o banco de dados e lança exceções personalizadas quando necessário.


* __@Service:__ Indica que a classe é um componente de serviço do Spring.
* **PlanoServicoRepository repository:** Injeta o repositório de PlanoServico.
* **List<PlanoServico> getAllPlans():** Retorna todos os planos de serviço utilizando repository.findAll().
* **PlanoServico getPlanById(Long id):** Busca um plano de serviço pelo ID, lançando ResourceNotFoundException se não for encontrado.
  * **repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(...)):** Tenta encontrar o plano pelo ID e lança uma exceção personalizada se não encontrado.
* **PlanoServico createPlan(PlanoServico plan):** Cria um novo plano de serviço utilizando repository.save(plan).
* **PlanoServico updatePlan(Long id, PlanoServico planDetails):** Atualiza um plano de serviço existente.
  * **repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(...)):** Verifica se o plano existe antes de atualizar.
  * **repository.save(plan):** Salva as atualizações do plano.
* **void deletePlan(Long id):** Deleta um plano de serviço pelo ID.
  * **repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(...)):** Verifica se o plano existe antes de deletar.
  * **repository.delete(plan):** Deleta o plano encontrado.


## PlanoServicoController.java
Controlador REST que expõe os endpoints para o serviço de Plano de Serviço. Manipula requisições HTTP para criar, listar, buscar, atualizar e deletar planos de serviço. Utiliza o PlanoServicoService para executar a lógica de negócios e retorna respostas apropriadas para o cliente.

* **@RestController:** Indica que a classe é um controlador onde cada método retorna um objeto de domínio em vez de uma visão.
* **@RequestMapping("/api/plans"):** Mapeia requisições HTTP para os métodos manipuladores de controle.
* **ResponseEntity<List<PlanoServico>> getAllPlans():** Manipula requisições GET para listar todos os planos de serviço.
  * **service.getAllPlans():** Chama o serviço para obter todos os planos.
  * **ResponseEntity.ok(plans):** Retorna a lista de planos com status HTTP 200.
* **ResponseEntity<PlanoServico> getPlanById(@PathVariable Long id):** Manipula requisições GET para obter um plano de serviço pelo ID.
  * **service.getPlanById(id):** Chama o serviço para obter o plano pelo ID.
  * **ResponseEntity.ok(plan):** Retorna o plano encontrado com status HTTP 200.
* **ResponseEntity<PlanoServico> createPlan(@RequestBody PlanoServico plan):** Manipula requisições POST para criar um novo plano de serviço.
  * **service.createPlan(plan):** Chama o serviço para criar o plano.
  * **ResponseEntity.ok(createdPlan):** Retorna o plano criado com status HTTP 200.
* **ResponseEntity<PlanoServico> updatePlan(@PathVariable Long id, @RequestBody PlanoServico planDetails):** Manipula requisições PUT para atualizar um plano de serviço existente.
  * **service.updatePlan(id, planDetails):** Chama o serviço para atualizar o plano.
  * **ResponseEntity.ok(updatedPlan):** Retorna o plano atualizado com status HTTP 200, ou status HTTP 404 se não encontrado.
* **ResponseEntity<Void> deletePlan(@PathVariable Long id):** Manipula requisições DELETE para deletar um plano de serviço pelo ID.
  * **service.deletePlan(id):** Chama o serviço para deletar o plano.
  * **ResponseEntity.noContent().build():** Retorna status HTTP 204 (No Content) se a deleção for bem-sucedida.

# Exceções Personalizadas
## CustomException.java
Classe base para exceções personalizadas, permitindo a criação de exceções específicas com mensagens personalizadas e causas subjacentes.

## ResourceNotFoundException.java
Exceção personalizada lançada quando um recurso (plano de serviço) não é encontrado no banco de dados.

## Tratamento Global de Exceções
## ErrorDetails.java
Classe que encapsula detalhes dos erros, incluindo timestamp, mensagem e detalhes adicionais. Utilizada para formatar respostas de erro de forma consistente.

## GlobalExceptionHandler.java
Classe que manipula exceções globais e formata as respostas de erro. Define métodos anotados com @ExceptionHandler para capturar exceções específicas e gerais, retornando respostas HTTP apropriadas.

* **handleResourceNotFoundException(ResourceNotFoundException ex):** Captura exceções ResourceNotFoundException e retorna um ErrorDetails com status HTTP 404.
* **handleCustomException(CustomException ex):** Captura exceções CustomException e retorna um ErrorDetails com status HTTP 500.
* **handleGlobalException(Exception ex):** Captura exceções gerais e retorna um ErrorDetails com status HTTP 500.