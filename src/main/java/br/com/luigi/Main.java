package br.com.luigi;

import br.com.luigi.model.*;
import br.com.luigi.service.*;

import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DataService service = new DataService();

        System.out.println("Últimos 3 períodos encontrados:");
        for (Data p : service.getLast3Quarters()) {
            System.out.println(p);
        }
        DownloadService downloader = new DownloadService();

        downloader.download(
                "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2024/4T2024.zip",
                Path.of("data/raw/2024_4.zip")
        );
        ExtractorService extractor = new ExtractorService();

        extractor.extract(
                Path.of("data/raw/2024_4.zip"),
                Path.of("data/extracted/2024_4"
                )

        );
        ExpenseFileDetector detector = new ExpenseFileDetector();
        Path extractedDir = Path.of("data/extracted/2024_4");
        CsvExpenseProcessor processor = new CsvExpenseProcessor();
        List<Path> files = detector.findExpenseFiles(extractedDir);

        List<ExpenseRecord> records = new ArrayList<>();

        for (Path file : files) {
            System.out.println("Processando: " + file.getFileName());

            List<ExpenseRecord> fileRecords =
                    processor.process(file, 2024, 4);

            if (fileRecords != null && !fileRecords.isEmpty()) {
                records.addAll(fileRecords);
            }
        }

        if (records.isEmpty()) {
            System.out.println("Nenhum registro de despesa encontrado.");
            return;
        }

        ExpenseConsolidator consolidator = new ExpenseConsolidator();
        ConsolidatedResult result = consolidator.consolidate(records);

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        System.out.println("Resultado da consolidação:");
        result.getTotals().forEach((key, value) -> {
            System.out.println(
                    "Operadora (REG_ANS): " + key.getRegAns() +
                            " | Ano: " + key.getAno() +
                            " | Trimestre: " + key.getTrimestre() +
                            " | Total Despesas: " + nf.format(value)
            );
        });

        ConsolidatedCsvWriter csvWriter =
                new ConsolidatedCsvWriter();

        Path csvPath = csvWriter.write(
                result,
                Path.of("data/output/consolidado_despesas.csv")
        );

        ZipService zipService = new ZipService();

        zipService.zip(
                csvPath,
                Path.of("data/output/consolidado_despesas.zip")
        );
        ExpenseValidationService validationService = new ExpenseValidationService();
        validationService.generateValidatedCsv(
                Path.of("data/output/consolidado_despesas.csv"),
                Path.of("data/output/despesas_validadas.csv")
        );

        ValidatedExpenseCsvReader reader = new ValidatedExpenseCsvReader();

        List<ValidatedExpenseRecord> validatedRecords =
                reader.read(Path.of("data/output/despesas_validadas.csv"));

        ExpenseAggregator aggregator = new ExpenseAggregator();

        Map<AggregationKey, AggregationAccumulator> aggregated =
                aggregator.aggregate(validatedRecords);

        AggregatedCsvWriter writer = new AggregatedCsvWriter();

        writer.write(
                aggregated,
                Path.of("data/output/despesas_agregadas.csv")
        );

        System.out.println("CSV agregado gerado com sucesso!");



    }



}
