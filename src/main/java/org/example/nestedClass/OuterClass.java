package org.example.nestedClass;

public class OuterClass {
    private int x = 10;

    private InnerClass inner = new InnerClass();

    public class InnerClass {
        public void display() {
            System.out.println("Value of x is: " + x);
        }
    }

    public static class StaticInnerClass {
        public void display() {
            System.out.println("Static Inner Class");
        }
    }
}
