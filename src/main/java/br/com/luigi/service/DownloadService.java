package br.com.luigi.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadService {

    private final HttpClient client = HttpClient.newHttpClient();

    public void download(String url, Path destino) {

        try {
            Files.createDirectories(destino.getParent());

            if (Files.exists(destino)) {
                System.out.println("Arquivo já instalado: " + destino.getFileName());
                return;
            }

            System.out.println("Baixando arquivo: " + destino.getFileName());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<InputStream> response =
                    client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            Files.copy(response.body(), destino);

            System.out.println("Download concluído: " + destino.getFileName());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao baixar arquivo: " + url, e);
        }
    }
}
