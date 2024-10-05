package org.example.generic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NumberStoreTest {
    
    @Test
    void generic_is_invariance() throws Exception {
        // given
        NumberStore numberStore = new NumberStore();

        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);

        // (List<Number> != List<Integer>) - 공변 필요
        //numberStore.putNums(nums);

        List<Number> newStore = new ArrayList<>();
        // (List<Double> != List<Number>) - 반공변 필요
        //numberStore.moveDoubles(newStore);
    }
    
    @Test
    void using_wildcard() throws Exception {
        // given
        NumberStore numberStore = new NumberStore();

        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);

        // (List<Number> != List<Integer>) - 공변 필요
        numberStore.putNumsUsingWildcard(nums);

        List<Number> newStore = new ArrayList<>();
        // (List<Double> != List<Number>) - 반공변 필요
        numberStore.moveDoublesUsingWildcard(newStore);
    }

}