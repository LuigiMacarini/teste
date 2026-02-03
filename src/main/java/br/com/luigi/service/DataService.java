package br.com.luigi.service;
import br.com.luigi.AnsReader;
import br.com.luigi.model.Data;
import br.com.luigi.util.GetterHtml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataService {

    private static final String URL_BASE =
            "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/";


    private final AnsReader client = new AnsReader();

    public List<Data> getLast3Quarters() {

        String baseHtml = client.getHtml(URL_BASE);

        List<String> years = GetterHtml.extractByRegex(
                baseHtml,
                "href=\"(\\d{4})/\""
        )       .stream()
                .map(Integer::parseInt)
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .toList();


        List<Data> data = new ArrayList<>();

        for (String yearString : years) {

            String yearUrl = URL_BASE + yearString + "/";
            String yearHtml = client.getHtml(yearUrl);


            List<String> quarters = GetterHtml.extractByRegex(
                    yearHtml,
                    "href=\"([1-4])T\\d{4}\\.zip\""
            ).stream().distinct().toList();

            for (String quarter : quarters) {
                data.add(new Data(
                        Integer.parseInt(yearString),
                        Integer.parseInt(quarter)
                ));
            }
        }

        data.sort(
                Comparator
                        .comparingInt(Data::getYear)
                        .thenComparingInt(Data::getQuarter)
                        .reversed()
        );

        return data.stream().limit(3).toList();
    }




}
