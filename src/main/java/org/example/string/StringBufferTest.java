package org.example.string;

public class StringBufferTest {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("hello");
        sb.append(" ");
        sb.append("world");
        System.out.println(sb.toString());

        sb.insert(5, " my");
        System.out.println(sb.toString());
    }
}
