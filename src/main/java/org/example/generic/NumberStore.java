package org.example.generic;

import java.util.ArrayList;
import java.util.List;

class NumberStore {
    List<Number> nums = new ArrayList<>(); // 정수, 소수를 구분하지 않고 모두 넣는 리스트

    // 정수, 소수를 받아서 저장
    public void putNums(List<Number> list){
        nums.addAll(list);
    }

    public void putNumsUsingWildcard(List<? extends Number> list){
        nums.addAll(list);
    }

    // 현재 가지고 있는 모든 소수를 파라미터에 저장
    public void moveDoubles(List<Double> doubles) {
        doubles.addAll(getDoubles());
    }

    public void moveDoublesUsingWildcard(List<? super Double> doubles) {
        doubles.addAll(getDoubles());
    }

    public List<Double> getDoubles(){
        return nums.stream()
                .filter(n -> n instanceof Double)
                .map(Double.class::cast)
                .toList();
    }
}
