package org.example.string;

public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        sb.append(" ");
        sb.append("world");
        System.out.println(sb.toString());

        sb.insert(5, " my");
        System.out.println(sb.toString());
    }
}
