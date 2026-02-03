package br.com.luigi.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public class ExpenseFileDetector {

    public List<Path> findExpenseFiles(Path dir) {

        try (Stream<Path> files = Files.walk(dir)) {

            return files
                    .filter(Files::isRegularFile)
                    .filter(p -> {
                        String name = p.getFileName().toString().toLowerCase();
                        return name.endsWith(".csv") || name.endsWith(".txt");
                    })
                    .filter(this::hasExpenseHeader)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar arquivos de despesa", e);
        }
    }

    private boolean hasExpenseHeader(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String header = reader.readLine();
            if (header == null) return false;

            header = header.toUpperCase();

            return header.contains("REG_ANS")
                    && header.contains("DESCRICAO")
                    && header.contains("VL_SALDO_FINAL");

        } catch (IOException e) {
            return false;
        }
    }
}
