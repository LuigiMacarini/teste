package br.com.luigi.model;

import java.math.BigDecimal;

public class ExpenseRecord {

    private final String regAns;
    private final String descricao;
    private final BigDecimal valor;
    private final int ano;
    private final int trimestre;

    public ExpenseRecord(String regAns, String descricao,
                         BigDecimal valor, int ano, int trimestre) {
        this.regAns = regAns;
        this.descricao = descricao;
        this.valor = valor;
        this.ano = ano;
        this.trimestre = trimestre;
    }

    public String getRegAns() {
        return regAns;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getAno() {
        return ano;
    }

    public int getTrimestre() {
        return trimestre;
    }

    @Override
    public String toString() {
        return regAns + " | " + descricao + " | " + valor +
                " | " + ano + "T" + trimestre;
    }
}
