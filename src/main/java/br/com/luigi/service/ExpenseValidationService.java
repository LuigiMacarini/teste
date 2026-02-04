package br.com.luigi.service;

import br.com.luigi.validation.CnpjValidator;
import br.com.luigi.model.ValidatedExpenseRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExpenseValidationService {

    public void generateValidatedCsv(Path inputCsv, Path outputCsv) {

        try (
                BufferedReader reader = Files.newBufferedReader(inputCsv);
                BufferedWriter writer = Files.newBufferedWriter(outputCsv)
        ) {

            writer.write(
                    "CNPJ;RazaoSocial;Trimestre;Ano;ValorDespesas;" +
                            "CnpjValido;ValorValido;RazaoSocialValida"
            );
            writer.newLine();

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";");

                String cnpj = parts[0];
                String razaoSocial = parts[1];
                int trimestre = Integer.parseInt(parts[2]);
                int ano = Integer.parseInt(parts[3]);
                BigDecimal valor = new BigDecimal(parts[4]);
                String uf = "";
                boolean cnpjValido = CnpjValidator.isValid(cnpj);
                boolean valorValido = valor.compareTo(BigDecimal.ZERO) > 0;
                boolean razaoSocialValida =
                        razaoSocial != null && !razaoSocial.trim().isEmpty();

                ValidatedExpenseRecord record =
                        new ValidatedExpenseRecord(
                                cnpj,
                                razaoSocial,
                                uf,
                                trimestre,
                                ano,
                                valor,
                                cnpjValido,
                                valorValido,
                                razaoSocialValida
                        );


                writer.write(record.toCsvLine());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar CSV validado", e);
        }
    }
}
