package br.com.luigi.service;

import br.com.luigi.model.ExpenseRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CsvExpenseProcessor {

    public List<ExpenseRecord> process(Path file, int year, int quarter) {

        System.out.println("Abrindo arquivo: " + file);

        List<ExpenseRecord> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String headerLine = reader.readLine();

            if (headerLine == null) return result;

            String[] headers = headerLine.replace("\"", "").split(";");
            Map<String, Integer> indexMap = buildIndexMap(headers);

            String line;
            while ((line = reader.readLine()) != null) {

                String[] values = line.replace("\"", "").split(";");

                String conta =
                        getValue(values, indexMap, "cd_conta_contabil");

                if (conta == null || !conta.startsWith("4")) {
                    continue;
                }

                String regAns =
                        getValue(values, indexMap, "reg_ans");

                String descricao =
                        getValue(values, indexMap, "descricao");

                String valorStr =
                        getValue(values, indexMap, "vl_saldo_final");

                if (valorStr == null || valorStr.isBlank()) continue;

                BigDecimal valor =
                        new BigDecimal(valorStr.replace(",", "."));

                result.add(new ExpenseRecord(
                        regAns,
                        descricao,
                        valor,
                        year,
                        quarter
                ));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo: " + file, e);
        }

        System.out.println("Registros de sinistro encontrados: " + result.size());
        return result;
    }


    private Map<String, Integer> buildIndexMap(String[] headers) {


        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i].toLowerCase(), i);
        }
        return map;
    }

    private String getValue(
            String[] values,
            Map<String, Integer> indexMap,
            String... possibleNames) {

        for (String name : possibleNames) {
            Integer index = indexMap.get(name);
            if (index != null && index < values.length) {
                return values[index];
            }
        }
        return null;
    }
}
