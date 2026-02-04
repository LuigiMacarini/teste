## 📊 Processamento, Validação e Consolidação de Despesas – ANS
### 📌 Visão Geral

Este projeto implementa uma solução completa para integração, processamento, validação, enriquecimento e agregação de dados financeiros da ANS, conforme especificado no documento do teste técnico.

A aplicação foi desenvolvida em Java, com foco em:

Robustez frente a variações de estrutura

Escalabilidade para grandes volumes de dados

Clareza nas decisões técnicas (trade-offs)

Transparência no tratamento de inconsistências

## 🛠️ Tecnologias Utilizadas

Java 17+

API nativa java.net.http.HttpClient

Processamento manual de CSV / TXT

Regex e parsing dinâmico de colunas

ZIP (java.util.zip)

Maven

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


## ⚠️ Os diretórios data/ são ignorados no versionamento via .gitignore.

### 1️⃣ Teste de Integração com API Pública
1.1. Acesso à API de Dados Abertos da ANS

Endpoint utilizado:
https://dadosabertos.ans.gov.br/FTP/PDA/

✔ Estratégia adotada

Identificação automática dos 3 últimos trimestres disponíveis

Construção dinâmica das URLs no formato:

YYYY/QQ


Implementação resiliente a:

Variações de diretório

Múltiplos arquivos por trimestre

Reexecuções (download não duplicado)

1.2. Processamento de Arquivos
✔ Download e Extração

Todos os arquivos ZIP dos trimestres identificados são:

Baixados automaticamente

Extraídos localmente

✔ Identificação de Arquivos Relevantes

Somente arquivos que contêm dados de Despesas com Eventos/Sinistros são processados.

A identificação ocorre por:

Análise do header

Presença de colunas associadas a despesas

✔ Normalização de Estruturas

Os arquivos podem apresentar variações como:

CSV ou TXT

Separadores diferentes

Nomes de colunas distintos, por exemplo:

VL_SINISTRO

VL_EVENTO

VL_DESPESA

VALOR_PAGO

## 🔧 Solução adotada:
Mapeamento dinâmico de colunas por nome, permitindo leitura independente da ordem ou nomenclatura exata.

## ⚙️ Trade-off Técnico – Estratégia de Processamento

Decisão: processamento incremental (linha a linha)

Justificativa:

Arquivos com centenas de milhares de registros

Menor consumo de memória

Maior escalabilidade

Evita carregamento completo em memória

1.3. Consolidação e Análise de Inconsistências
✔ Consolidação

Os dados dos 3 trimestres são consolidados em um único arquivo:

consolidado_despesas.csv

## 📄 Estrutura do CSV Consolidado
CNPJ;RazaoSocial;Trimestre;Ano;ValorDespesas


## ⚠️ Observação importante:
A fonte da ANS não disponibiliza CNPJ nem Razão Social diretamente nos arquivos financeiros.
Por isso:

O campo CNPJ é preenchido com o identificador REG_ANS

A Razão Social recebe valor padrão quando ausente

## ⚠️ Tratamento de Inconsistências
Inconsistência	Tratamento	Justificativa
Valores ≤ 0	Ignorados	Não representam despesas reais
CNPJs duplicados	Somados na consolidação	Evita duplicidade financeira
Razões sociais divergentes	Primeira ocorrência mantida	Fonte não confiável
Datas inconsistentes	Normalização por trimestre	Padronização
📦 Compactação

O CSV final é compactado automaticamente em:

consolidado_despesas.zip

2️⃣ Teste de Transformação e Validação de Dados
2.1. Validação de Dados

A partir do CSV consolidado, foi gerado:

despesas_validadas.csv

✔ Validações Implementadas

CNPJ válido

Formato e dígitos verificadores

ValorDespesas positivo

Razão Social não vazia

Campos adicionais gerados:

CnpjValido;ValorValido;RazaoSocialValida

## ⚙️ Trade-off Técnico – CNPJs inválidos

Decisão:
CNPJs inválidos não são descartados, apenas marcados como inválidos.

Prós:

Preserva dados financeiros relevantes

Permite auditoria posterior

Contras:

Exige validação adicional em análises futuras

2.2. Enriquecimento de Dados
✔ Fonte Utilizada

Cadastro de operadoras ativas da ANS:

https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/

✔ Join realizado por:

CNPJ (ou REG_ANS quando necessário)

✔ Colunas adicionadas:

RegistroANS

Modalidade

UF

## ⚠️ Tratamento de Falhas no Join
Situação	Tratamento	Justificativa
Registro sem match	Mantido com campos vazios	Evita perda de dados
CNPJ duplicado no cadastro	Primeira ocorrência	Simplicidade e previsibilidade
UF ausente	Campo vazio	Dado indisponível
📈 Agregação com Múltiplas Estratégias

Os dados enriquecidos foram agregados por:

RazaoSocial + UF

✔ Métricas calculadas:

TotalDespesas

Média Trimestral

Desvio Padrão

Arquivo gerado:

despesas_agregadas.csv


Formato:

RazaoSocial;UF;TotalDespesas;MediaTrimestral;DesvioPadrao

## ⚙️ Trade-off Técnico – Agregação e Ordenação

Uso de acumuladores estatísticos

Ordenação por TotalDespesas (decrescente)

Estratégia adequada para grandes volumes sem custo excessivo de memória

## ▶️ Como Executar

Clone o repositório

Abra no IntelliJ IDEA

Execute a classe Main

👤 Autor

Luigi Niespodzinski Macarini
