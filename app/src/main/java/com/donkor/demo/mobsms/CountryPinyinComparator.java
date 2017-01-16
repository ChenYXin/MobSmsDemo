package com.donkor.demo.mobsms;

import java.util.Comparator;

public class CountryPinyinComparator implements Comparator<Country> {
    public int compare(Country o1, Country o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
