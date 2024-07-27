package org.example.nestedClass;

public class Main {
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();

        OuterClass.StaticInnerClass staticInner = new OuterClass.StaticInnerClass();
        staticInner.display();

        OuterClass.StaticInnerClass staticInner2 = new OuterClass.StaticInnerClass();
    }
}
