package br.com.luigi.model;

import java.math.BigDecimal;

public class ValidatedExpenseRecord {

    private final String cnpj;
    private final String razaoSocial;
    private final int trimestre;
    private final int ano;
    private final BigDecimal valorDespesas;
    private final String uf;

    private final boolean cnpjValido;
    private final boolean valorValido;
    private final boolean razaoSocialValida;

    public ValidatedExpenseRecord(
            String cnpj,
            String razaoSocial,
            String uf,
            int trimestre,
            int ano,
            BigDecimal valorDespesas,
            boolean cnpjValido,
            boolean valorValido,
            boolean razaoSocialValida) {

        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.uf = uf;
        this.trimestre = trimestre;
        this.ano = ano;
        this.valorDespesas = valorDespesas;
        this.cnpjValido = cnpjValido;
        this.valorValido = valorValido;
        this.razaoSocialValida = razaoSocialValida;

    }

    public String toCsvLine() {
        return String.join(";",
                cnpj,
                razaoSocial == null ? "" : razaoSocial,
                uf == null ? "" : uf,
                String.valueOf(trimestre),
                String.valueOf(ano),
                valorDespesas.toPlainString(),
                String.valueOf(cnpjValido),
                String.valueOf(valorValido),
                String.valueOf(razaoSocialValida)
        );

    }
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getUf() {
        return uf;
    }

    public BigDecimal getValorDespesas() {
        return valorDespesas;
    }

    public boolean isValorValido() {
        return valorValido;
    }

    public boolean isCnpjValido() {
        return cnpjValido;
    }

    public boolean isRazaoSocialValida() {
        return razaoSocialValida;
    }



}
