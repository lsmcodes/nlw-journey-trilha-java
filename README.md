# NLW Journey - Planner de Viagens

<div style="display:inline-block">
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Java-black?style=for-the-badge&logo=OpenJDK&logoColor=white">
                <img src="https://img.shields.io/badge/Java-white?style=for-the-badge&logo=OpenJDK&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Maven-black?style=for-the-badge&logo=ApacheMaven&logoColor=white">
                <img src="https://img.shields.io/badge/Maven-white?style=for-the-badge&logo=ApacheMaven&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Spring_Boot-black?style=for-the-badge&logo=SpringBoot&logoColor=white">
                <img src="https://img.shields.io/badge/Spring_Boot-white?style=for-the-badge&logo=SpringBoot&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/FlyWay-black?style=for-the-badge&logo=FlyWay&logoColor=white">
                <img src="https://img.shields.io/badge/FlyWay-white?style=for-the-badge&logo=FlyWay&logoColor=black" />
        </picture>
</div>

## Visão Geral

Este repositório contém uma API REST Java de planejamento de viagens, desenvolvida durante o evento `NLW Journey` oferecido pela `Rocketseat`.  As três aulas ministradas durante o evento cobriram os seguintes assuntos:

* Banco em memória H2;
* JPA;
* Migrations com FlyWay;
* Uso de records para transferência de dados;
* Lombok.

## Desafios

Ao final da terceira aula da trilha Java, os desafios a seguir foram propostos:

* Validar os campos de data durante a criação de uma viagem para que a data de início de uma viagem não seja inferior à data de término;
* Validar o campo de data durante a criação de uma atividade para garantir que a data de realização de uma viagem seja durante o período da viagem a qual ela pertence;
* Adição de mapeamento e tratamento de exceções personalizadas.

## **Endpoints**

### Trips

| URI                      | Método | Ação                   | Body                        |
| ------------------------ | ------- | ------------------------ | --------------------------- |
| /trips                   | POST    | Criar uma viagem         | [JSON](#criaratualizar-viagem) |
| /trips/{id}              | GET     | Buscar uma viagem        | N/A                         |
| /trips/{id}              | PUT     | Atualizar uma viagem     | [JSON](#criaratualizar-viagem) |
| /trips/{id}/confirm      | POST    | Confirmar uma viagem     | N/A                         |
| /trips/{id}/invite       | POST    | Convidar um participante | [JSON](#convidar-participante) |
| /trips/{id}/participants | GET     | Buscar participantes     | N/A                         |
| /trips/{id}/activities   | POST    | Adicionar uma atividade  | [JSON](#criar-atividade)       |
| /trips/{id}/activities   | GET     | Buscar atividades        | N/A                         |
| /trips/{id}/links        | POST    | Adicionar um link        | [JSON](#criar-link)            |
| /trips/{id}/links        | GET     | Buscar links             | N/A                         |

### Participants

| URI                        | Método | Ação                    | Body |
| -------------------------- | ------- | ------------------------- | ---- |
| /participants/{id}/confirm | POST    | Confirmar um participante | N/A  |

## Body

A seguir estão os modelos que devem ser utilizados para cada uma das requisições que os exigirem:

### Criar/Atualizar Viagem

```json
{
  "destination":"São Paulo, SP",
  "startsAt":"2024-06-25T06:00:00.000",
  "endsAt":"2024-06-30T21:00:00.000",
  "emailsToInvite": [
    "participante@gmail.com"
  ],
  "ownerName":"Criador da viagem",
  "ownerEmail":"criador@gmail.com"
}
```

### Convidar Participante

```json
{
  "email":"participante@gmail.com"
}
```

### Confirmar Participante

```json
{
  "name":"Participante"
}
```

### Criar Atividade

```json
{
  "title":"Ir ao Parque Ibirapuera",
  "occursAt":"2024-06-27T09:00:00.000"
}
```

### Criar Link

```json
{
  "title":"O que fazer: São Paulo",
  "url":"https://www.tripadvisor.com.br/Attractions-g303631-Activities-Sao_Paulo_State_of_Sao_Paulo.html"
}
```
