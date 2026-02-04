package br.com.luigi.model;

import java.util.ArrayList;
import java.util.List;

public class AggregationAccumulator {

    private final List<Double> valoresPorTrimestre = new ArrayList<>();

    public void add(double valor) {
        valoresPorTrimestre.add(valor);
    }

    public double getTotal() {
        return valoresPorTrimestre.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getMedia() {
        if (valoresPorTrimestre.isEmpty()) return 0.0;
        return getTotal() / valoresPorTrimestre.size();
    }

    public double getDesvioPadrao() {
        if (valoresPorTrimestre.size() <= 1) return 0.0;

        double media = getMedia();

        double variancia = valoresPorTrimestre.stream()
                .mapToDouble(v -> Math.pow(v - media, 2))
                .average()
                .orElse(0.0);

        return Math.sqrt(variancia);
    }

    public int getQuantidadeTrimestres() {
        return valoresPorTrimestre.size();
    }
}
