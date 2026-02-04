package br.com.luigi.service;

import br.com.luigi.model.AggregationAccumulator;
import br.com.luigi.model.AggregationKey;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

public class AggregatedCsvWriter {

    public void write(
            Map<AggregationKey, AggregationAccumulator> data,
            Path outputFile) {

        try {
            Files.createDirectories(outputFile.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

                writer.write("RazaoSocial;UF;TotalDespesas;MediaTrimestral;DesvioPadrao");
                writer.newLine();

                data.entrySet().stream()
                        .sorted(Comparator.comparingDouble(
                                (Map.Entry<AggregationKey, AggregationAccumulator> e)
                                        -> e.getValue().getTotal()
                        ).reversed())
                        .forEach(entry -> {
                            try {
                                AggregationKey key = entry.getKey();
                                AggregationAccumulator acc = entry.getValue();

                                writer.write(
                                        key.getRazaoSocial() + ";" +
                                                key.getUf() + ";" +
                                                String.format("%.2f", acc.getTotal()) + ";" +
                                                String.format("%.2f", acc.getMedia()) + ";" +
                                                String.format("%.2f", acc.getDesvioPadrao())
                                );
                                writer.newLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar CSV agregado", e);
        }
    }
}
