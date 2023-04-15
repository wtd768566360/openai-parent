package com.alone.openai.web.test;

public class NewlineCharacterTest {
    public static void main(String[] args) {
        String content = "\n";
        content = content.replaceAll("\n", "\\\\n");
        System.out.println(content);
    }
}
