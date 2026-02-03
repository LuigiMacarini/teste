package br.com.luigi.service;

import br.com.luigi.model.ConsolidationKey;
import br.com.luigi.model.ExpenseRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseConsolidator {

    public ConsolidatedResult consolidate(List<ExpenseRecord> records) {

        Map<ConsolidationKey, BigDecimal> totals = new HashMap<>();

        for (ExpenseRecord record : records) {


            if (record.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            ConsolidationKey key = new ConsolidationKey(
                    record.getRegAns(),
                    record.getAno(),
                    record.getTrimestre()
            );

            totals.merge(key, record.getValor(), BigDecimal::add);
        }

        return new ConsolidatedResult(totals);
    }
}
