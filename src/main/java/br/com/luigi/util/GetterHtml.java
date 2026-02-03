package br.com.luigi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetterHtml {
    public static List<String> extractByRegex(String html,String regex){
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()){
            results.add((matcher.group(1)));
        }
        return results;
    }

}
