package br.com.luigi.model;

import java.util.Objects;

public class AggregationKey {

    private final String razaoSocial;
    private final String uf;

    public AggregationKey(String razaoSocial, String uf) {
        this.razaoSocial = razaoSocial;
        this.uf = uf;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggregationKey)) return false;
        AggregationKey that = (AggregationKey) o;
        return Objects.equals(razaoSocial, that.razaoSocial) &&
                Objects.equals(uf, that.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(razaoSocial, uf);
    }
}
