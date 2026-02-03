package br.com.luigi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipService {

    public void zip(Path fileToZip, Path zipFile) {

        try {
            Files.createDirectories(zipFile.getParent());

            try (ZipOutputStream zos =
                         new ZipOutputStream(
                                 Files.newOutputStream(zipFile))) {

                ZipEntry entry =
                        new ZipEntry(fileToZip.getFileName().toString());

                zos.putNextEntry(entry);
                Files.copy(fileToZip, zos);
                zos.closeEntry();
            }

            System.out.println("ZIP gerado com sucesso: "
                    + zipFile.getFileName());

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao compactar arquivo", e
            );
        }
    }
}
