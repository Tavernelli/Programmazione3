package com.Subjectfind;

class ListMateriaField implements Comparable<ListMateriaField> {

    int distance = Integer.MAX_VALUE;
    String materia = "";


    ListMateriaField(String m,int d){
        materia = m;
        distance = d;
    }

    @Override
    public int compareTo(ListMateriaField obj) {
        if(distance < obj.distance){
            return -1; // restituisce un negativo se distance è < del nuovo oggetto
        }
        else if (distance == obj.distance){
            return 0;
        }
        return 1; // restituisce un negativo se distance è > del nuovo oggetto
    }
}
