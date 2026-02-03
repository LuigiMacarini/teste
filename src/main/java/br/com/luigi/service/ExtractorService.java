package br.com.luigi.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractorService {

    public void extract(Path zipFile, Path outputDir) {

        try (InputStream fis = Files.newInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis)) {

            Files.createDirectories(outputDir);

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {

                Path filePath = outputDir.resolve(entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                zis.closeEntry();
            }

            System.out.println("ZIP extraído: " + zipFile.getFileName());

        } catch (IOException e) {
            throw new RuntimeException("Erro ao extrair ZIP: " + zipFile, e);
        }
    }
}
