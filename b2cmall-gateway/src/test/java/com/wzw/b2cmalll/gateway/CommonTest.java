package com.wzw.b2cmalll.gateway;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTest {

    public static void main(String[] args) {
        String text = "Hello, this is a sample text. It starts with 'Hello'.";

        // 匹配以'H'开头的一段文本
        Pattern pattern = Pattern.compile("^(H.*)");
        Matcher matcher = pattern.matcher(text);

        // 查找匹配项
        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println("Match found: " + matcher.group(1));
        } else {
            System.out.println("No match found.");
        }

        // 匹配中间以' is'开头的一段文本
        pattern = Pattern.compile("(?<= is)(.*)");
        matcher = pattern.matcher(text);

        // 查找匹配项
        if (matcher.find()) {
            System.out.println("Match found: " + matcher.group(1));
        } else {
            System.out.println("No match found.");
        }
    }

}
