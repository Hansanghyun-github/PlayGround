package org.example.compare;

import java.util.ArrayList;
import java.util.List;

public class CompareExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(5);
        list.add(20);
        list.add(15);
        list.add(25);
        System.out.println("Original List: ");
        System.out.println(list);

        list.sort((a, b) -> b - a);
        System.out.println("After / (a, b) -> b - a");
        System.out.println(list); // [25, 20, 15, 10, 5]

        list.sort((a, b) -> a - b);
        System.out.println("After / (a, b) -> a - b");
        System.out.println(list); // [5, 10, 15, 20, 25]

        //System.out.println(Integer.valueOf(10).compareTo(5));
        System.out.println(Integer.compare(10, 5)); // 1, 앞쪽이 크면 1
        System.out.println(Integer.compare(5, 10)); // -1, 뒷쪽이 크면 -1
        System.out.println(Integer.compare(10, 10)); // 0, 같으면 0

        /**
         * sort시 사용하는 Comparator 규칙은 이렇게 생각하자.
         * 오름 차순 -> 뒤로 갈수록 커진다. -> a - b (큰게 뒤에 있다)
         * 내림 차순 -> 앞으로 갈수록 커진다. -> b - a (큰게 앞에 있다)
         * */

    }

}
