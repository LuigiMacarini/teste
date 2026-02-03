package br.com.luigi.service;

import br.com.luigi.model.ConsolidationKey;

import java.math.BigDecimal;
import java.util.Map;

public class ConsolidatedResult {

    private final Map<ConsolidationKey, BigDecimal> totals;

    public ConsolidatedResult(Map<ConsolidationKey, BigDecimal> totals) {
        this.totals = totals;
    }

    public Map<ConsolidationKey, BigDecimal> getTotals() {
        return totals;
    }
}
