# Ideia
 - Um sistema de bike compartilhada

# O que precisa ser implementado
- Servidor http
- Schema/spec (Utilizar plumatic/schema)
- - Usar na camada de logica
-  Interceptor
- Teste
- Testes dos schemas/spec
- Utilizar components

# Modelo de dados
```json
{
  "bikes": [{
      "id": 1,
      "ponto": null,
      "usuario": null
    }],
  "pontos": [{
    "id": 1,
    "nome": "ponto da felicidade",
    "capacidade": 10,
    "endereco": {
      "rua": "rua da felicidade",
      "numero": "012",
      "cep": "72000000",
      "complemento": ""
    }
  }],
  "usuarios": [{
    "id": 1,
    "login": "admin",
    "assinante": true,
    "key": "/&[3-.wff@qx'{aTX-2P>}XE_B6Jc+"
  }]
}
```
# Endpoints
| O que faz | path | método http | Interceptors |
| --- | --- | --- | --- |
| consultar all pontos | `/pontos` | `get` | |
| consultar um ponto | `/pontos/:id`| `:get`| |
| retirar uma bike | `/users/:id/bike/:id-bike`| `:post` | [1, 2] |
| devolver uma bike |   `/bikes/:id-bike/pontos/:id` | `post` | [1] |
| assinar |              `/users/:id/assinatura` | `post` | [1] |

# Interceptors
- 1 interceptor de autorizacao
    - verificar se a request vem com o header `key` e fazer o bind de `:user` na request caso exista, caso contrario erro 400
- 2 interceptor para usuario ativo
    - continua apenas se usuario estiver ativo, caso contrario erro 403
- 3 formatar saida para json, deve ter algum interceptor padrao

# Regras de negocio
consultar ponto
- trazer todas as bikes disponiveis naquele ponto
retirada da bike
- usuario soh pode ter uma bike vinculada, ativa, usando, sei la
- usuario nao pode retirar uma bika de outro usuario
devolucao da bike
- deve possuir vaga disponivel
assinatura
- usuario nao pode ser assinante




