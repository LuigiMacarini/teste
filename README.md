# 📊 Processamento e Consolidação de Despesas – ANS
📌 Descrição Geral

Este projeto realiza a integração com a API pública de Dados Abertos da ANS, com o objetivo de identificar, processar e consolidar os dados de Despesas com Eventos/Sinistros referentes aos 3 últimos trimestres disponíveis.

A aplicação foi desenvolvida em Java, priorizando robustez, clareza de código e decisões técnicas justificadas, conforme solicitado no teste.

---
# 🛠️ Tecnologias Utilizadas

Java 17+

java.net.http.HttpClient (API nativa)

Processamento de arquivos CSV/TXT

Regex para parsing manual

ZIP (java.util.zip)

Maven

---
## 📂 Estrutura do Projeto

```text
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── model
│   │   │   ├── service
│   │   │   └── Main.java
│   │   └── resources
├── data
│   ├── raw        (downloads)
│   ├── extracted  (arquivos extraídos)
│   └── output     (CSV e ZIP final)
└── README.md
```


### ⚠️ Os diretórios de dados (data/) são ignorados no versionamento via .gitignore.

---
# 🔄 Fluxo da Aplicação

Acessa o endpoint público da ANS

Identifica automaticamente os 3 últimos trimestres disponíveis

Baixa os arquivos ZIP correspondentes

Extrai os arquivos localmente

Identifica e processa apenas arquivos relacionados a Despesas com Eventos/Sinistros

Normaliza estruturas de colunas diferentes

Consolida os dados por operadora, ano e trimestre

Gera um CSV final consolidado

Compacta o CSV em consolidado_despesas.zip

---
# 📑 Processamento e Normalização
### 📌 Identificação de Arquivos Relevantes

Somente arquivos contendo colunas associadas a despesas/sinistros são processados. Arquivos irrelevantes são ignorados.

### 📌 Normalização de Colunas

Os arquivos podem conter variações de nomes de colunas, como:

VL_SINISTRO

VL_EVENTO

VL_DESPESA

VALOR_PAGO

O sistema identifica automaticamente a estrutura e extrai o valor correto, garantindo compatibilidade entre formatos distintos.

---
# ⚙️ Estratégia de Processamento (Trade-off Técnico)

Decisão: processamento incremental (linha a linha).

Justificativa:

Arquivos grandes (centenas de milhares de registros)

Redução do consumo de memória

Maior escalabilidade

Evita carregamento completo em memória

---
# 📊 Consolidação dos Dados
### 🔑 Chave de Consolidação
(REG_ANS, Ano, Trimestre)

### 📌 Regras Aplicadas

Valores zerados ou negativos → ignorados

Registros duplicados → somados corretamente

Dados inconsistentes → tratados conforme regras acima

---
# ⚠️ Tratamento de Inconsistências
Inconsistência	Tratamento	Justificativa
Valores ≤ 0	Ignorados	Não representam despesas reais
Estruturas diferentes	Normalização dinâmica	Robustez contra variações
Ausência de CNPJ	REG_ANS utilizado	Fonte não fornece CNPJ
Ausência de Razão Social	Valor padrão	Informação inexistente na origem

---
# 📄 Formato do CSV Final

Arquivo: consolidado_despesas.csv

Colunas:

CNPJ;RazaoSocial;Trimestre;Ano;ValorDespesas


Observação:
Como a fonte não disponibiliza CNPJ ou Razão Social, o campo CNPJ é preenchido com o identificador REG_ANS, devidamente documentado.

---
# 📦 Compactação

O CSV final é compactado no arquivo:

consolidado_despesas.zip

---
Como Executar
1. Clone o repositório

2. Abra o projeto no IntelliJ

3. Execute a classe Main

---
# 👤 Autor

**Luigi Niespodzinski Macarini**
