# Fiona e os 5 gatinhos de Botas

Este é um projeto de refatoração de um sistema para alocação de clientes em mesas de restaurante. O sistema original foi desenvolvido na disciplina de Programação Modular e, nesta versão, passou por refatoração para incorporar padrões de projeto aprendidos na disciplina de Projeto de Software. Foi utilizado um padrão Singleton para todas as classes Manager, delegando todas as chamadas de métodos à classe RestauranteFacade de acordo com o projeto estrutural denominado Facade

### Descrição do Sistema

O sistema aloca clientes em mesas de um restaurante com 10 mesas de diferentes capacidades (4, 6 e 8 pessoas). Os clientes informam a quantidade de pessoas na chegada, e o sistema aloca uma mesa ou coloca o cliente na fila de espera, se necessário. Cada requisição registra a data e hora de entrada e saída. Os clientes podem realizar pedidos, que são incrementados no valor total que a mesa deverá pagar antes de se retirarem do restaurante. 

### Estrutura do sistema

```
C:.
├───codigo
│   └───src
└───docs
    └───diagramas
```

Dentro da aba "src" é possível encontrar todo o código fonte do projeto. O sistema é composto por várias classes gerenciadoras:

- **`RestauranteFacade`** – Centraliza todas as operações do restaurante.  
- **`ReqManager`** – Gerencia as requisições dos clientes.  
- **`MesaManager`** – Gerencia a ocupação e liberação das mesas.  
- **`FilaManager`** – Controla a fila de espera.  
- **`ProdutoManager`** – Administra os produtos disponíveis no cardápio.  
- **`Main`** – Executa o programa e exibe o menu para interação do usuário.  


### Instruções de uso

A interface do código é toda fornecida através do terminal. Para a execução correta do código, basta executar:
```
  javac Main.java
  java Main
```

Ao iniciar o sistema, o usuário será apresentado a este menu:

```
============== MENU ==================
|   1. Fazer requisição              |
|   2. Sair da mesa                  |
|   3. Sair do programa              |
|   4. Consultar fila de espera      |
|   5. Cancelar requisição em espera |
|   6. Realizar um pedido            |
|   7. Finalizar um pedido           |
|   8. Consultar um pedido           |
|====================================|
   Escolha uma opção: 
```

O usuário pode digitar o número da opção desejada e pressionar Enter para interagir com o sistema.


### Alunos envolvidos no refatoramento

* Lucas Giovine
* Vitor Rebula

### Alunos envolvidos no projeto original

* Maria Eduarda Ferraz
* Albert Luís
* Lucas Giovine
* Pedro Porto
* Thiago Cury
* Vitor Rebula

### Professor responsável pelo refatoramento

* João Paulo Carneiro Aramuni

### Professores responsáveis pelo projeto original

* Danilo Boechat Seufitelli
