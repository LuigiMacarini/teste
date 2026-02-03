package br.com.luigi.service;

import br.com.luigi.model.ConsolidationKey;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ConsolidatedCsvWriter {

    public Path write(
            ConsolidatedResult result,
            Path outputFile
    ) {

        try {
            Files.createDirectories(outputFile.getParent());

            try (BufferedWriter writer =
                         Files.newBufferedWriter(outputFile)) {

                writer.write("CNPJ;RazaoSocial;Trimestre;Ano;ValorDespesas");
                writer.newLine();

                for (Map.Entry<ConsolidationKey, BigDecimal> entry
                        : result.getTotals().entrySet()) {

                    ConsolidationKey key = entry.getKey();
                    BigDecimal total = entry.getValue();

                    writer.write(
                            key.getRegAns() + ";" +
                                    "OPERADORA_NAO_IDENTIFICADA" + ";" +
                                    key.getTrimestre() + ";" +
                                    key.getAno() + ";" +
                                    total
                    );
                    writer.newLine();
                }
            }

            System.out.println("CSV consolidado gerado: "
                    + outputFile.getFileName());

            return outputFile;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao gerar CSV consolidado", e
            );
        }
    }
}
