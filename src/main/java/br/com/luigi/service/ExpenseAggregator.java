package br.com.luigi.service;

import br.com.luigi.model.AggregationAccumulator;
import br.com.luigi.model.AggregationKey;
import br.com.luigi.model.ValidatedExpenseRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseAggregator {

    public Map<AggregationKey, AggregationAccumulator> aggregate(
            List<ValidatedExpenseRecord> records) {

        Map<AggregationKey, AggregationAccumulator> result = new HashMap<>();

        for (ValidatedExpenseRecord r : records) {

            if (!r.isValorValido()) continue;

            AggregationKey key = new AggregationKey(
                    r.getRazaoSocial(),
                    r.getUf()
            );

            result
                    .computeIfAbsent(key, k -> new AggregationAccumulator())
                    .add(r.getValorDespesas().doubleValue());
        }

        return result;
    }
}
