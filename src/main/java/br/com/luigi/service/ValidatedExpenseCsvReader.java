package br.com.luigi.service;

import br.com.luigi.model.ValidatedExpenseRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ValidatedExpenseCsvReader {

    public List<ValidatedExpenseRecord> read(Path file) {

        List<ValidatedExpenseRecord> records = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {

            reader.readLine(); // pula header

            String line;
            while ((line = reader.readLine()) != null) {

                String[] v = line.split(";");

                records.add(new ValidatedExpenseRecord(
                        v[0],                                  // CNPJ
                        v[1],                                  // RazaoSocial
                        v[2],                                  // UF
                        Integer.parseInt(v[3]),                // Trimestre
                        Integer.parseInt(v[4]),                // Ano
                        new BigDecimal(v[5]),                  // ValorDespesas
                        Boolean.parseBoolean(v[6]),            // CnpjValido
                        Boolean.parseBoolean(v[7]),            // ValorValido
                        Boolean.parseBoolean(v[8])             // RazaoSocialValida
                ));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler CSV validado", e);
        }

        return records;
    }
}
