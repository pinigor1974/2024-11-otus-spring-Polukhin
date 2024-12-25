package ru.otus.test.hw;

import java.util.*;

public class SymbolIntersection {
    static List<String> result(String[] words){
        int[][] index = new int[128][100];
        for(int wordIndex =0; wordIndex < words.length; ++ wordIndex){
            String word = words[wordIndex];
            char[] data = word.toCharArray();
            for(int i =0; i < data.length; ++i){
                index[data[i]][wordIndex] +=1;
            }
        }
        List<String> s = new ArrayList<>();
        for(int i = 0; i < index.length; ++i){
            var data = index[i];

            var count = 0;
            for(int j = 0; j < data.length; ++j ){
                if(data[j] > 0){
                    ++count;
                }
            }
            if(count >= words.length){
                s.add(String.valueOf((char) i));
            }
        }

        return s;
    }

    public static void main(String[] args) {

        System.out.println(result(new String[]{"abccccdffff", "cde"}));

    }
}
