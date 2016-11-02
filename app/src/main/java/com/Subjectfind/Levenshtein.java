package com.Subjectfind;


import java.util.ArrayList;
import java.util.Collections;



public class Levenshtein {

    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {

            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }


    static ArrayList<ListMateriaField> listaDistanza(String args, String  materie) {


        String strMaterie[] = materie
                .replaceAll("]\"", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll("\"", "")
                .split(",");

        ArrayList<ListMateriaField> out = new ArrayList<>();

        for (String materia : strMaterie) {
            out.add(new ListMateriaField(
                    materia,
                    distance(args, materia)
            ));
        }

        Collections.sort(out);

        return out;
    }

}











