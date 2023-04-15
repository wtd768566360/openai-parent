package com.alone.openai.common.util;

import java.util.Random;

public class RandomGeneratorUtil {

    private static final String CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 6;
    private static final Random random = new Random();

    public static String generate() {
        char[] chars = CHARACTERS.toCharArray();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length() - 1);
            code.append(chars[index]);
        }
        return code.toString();
    }
}
