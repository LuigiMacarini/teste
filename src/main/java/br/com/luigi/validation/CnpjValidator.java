package br.com.luigi.validation;

public class CnpjValidator {

    public static boolean isValid(String cnpj) {

        if (cnpj == null) return false;

        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) return false;
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] w1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] w2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

        int d1 = calc(cnpj.substring(0, 12), w1);
        int d2 = calc(cnpj.substring(0, 13), w2);

        return cnpj.endsWith("" + d1 + d2);
    }

    private static int calc(String s, int[] w) {
        int sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += (s.charAt(i) - '0') * w[i];
        }
        int mod = sum % 11;
        return mod < 2 ? 0 : 11 - mod;
    }
}
