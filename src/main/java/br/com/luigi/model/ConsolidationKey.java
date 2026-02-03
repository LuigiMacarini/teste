package br.com.luigi.model;

import java.util.Objects;

public class ConsolidationKey {

    private final String regAns;
    private final int ano;
    private final int trimestre;

    public ConsolidationKey(String regAns, int ano, int trimestre) {
        this.regAns = regAns;
        this.ano = ano;
        this.trimestre = trimestre;
    }

    public String getRegAns() {
        return regAns;
    }

    public int getAno() {
        return ano;
    }

    public int getTrimestre() {
        return trimestre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsolidationKey)) return false;
        ConsolidationKey that = (ConsolidationKey) o;
        return ano == that.ano &&
                trimestre == that.trimestre &&
                Objects.equals(regAns, that.regAns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regAns, ano, trimestre);
    }
}
