package com.example.nick.countrypedia.view;

public class Formatter {
    public static String seperate(String str, int step, char separator) {
        StringBuilder builder = new StringBuilder(str);

        if (step >= builder.length()) {
            return str;
        }

        for(int z = builder.length()-3; z>0; z -= step) {
            builder.insert(z, separator);
        }

        return builder.toString();
    }

    public static String seperate(long number, int step, char separator) {
        return seperate(String.valueOf(number), step, separator);
    }
}
