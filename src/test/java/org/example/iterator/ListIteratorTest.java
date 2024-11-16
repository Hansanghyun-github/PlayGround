package org.example.iterator;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static org.assertj.core.api.Assertions.*;

class ListIteratorTest {
    @Test
    void set_메서드는_바로_뒤의_원소를_변경한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();

        // when // then
        assertThatThrownBy(() -> li.set(0))
                .isInstanceOf(IllegalStateException.class);

        li.next();
        li.set(0);
        assertThat(list).containsExactly(0, 2, 3, 4, 5, 6);
    }

    @Test
    void nextIndex_메서드는_현재_가리키는_원소의_인덱스만_반환하고_이동하지는_않는다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();

        // when // then
        assertThat(li.nextIndex()).isEqualTo(0);
        assertThat(li.nextIndex()).isEqualTo(0);
    }

    @Test
    void next_메서드는_다음_원소를_반환하면서_한칸_이동한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();
        assertThat(li.nextIndex()).isEqualTo(0);

        // when // then
        assertThat(li.next()).isEqualTo(1);
        assertThat(li.nextIndex()).isEqualTo(1);
    }

    @Test
    void add_메서드는_다음_위치_앞에_새로운_원소를_추가한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();
        li.next();

        // when
        li.add(0);

        // then
        assertThat(list).containsExactly(1, 0, 2, 3, 4, 5, 6);
    }

    @Test
    void add_메서드는_새로운_원소를_추가한_다음_한칸_이동한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();
        li.next();

        // when
        li.add(0);

        // then
        assertThat(li.nextIndex()).isEqualTo(2);
        assertThat(li.next()).isEqualTo(2);
    }

    @Test
    void remove_메서드는_이전_원소를_삭제한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();
        li.next();

        // when
        li.remove();

        // then
        assertThat(list).containsExactly(2, 3, 4, 5, 6);
    }

    @Test
    void remove_메서드는_원소를_삭제한_다음_이전_위치로_이동한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();
        li.next();
        assertThat(li.nextIndex()).isEqualTo(1);

        // when
        li.remove();

        // then
        assertThat(li.nextIndex()).isEqualTo(0);
    }

    @Test
    void 첫_위치에서_remove() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();

        // when // then
        assertThatThrownBy(li::remove)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 마지막_위치에서_remove() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();
        while (li.hasNext()) {
            li.next();
        }

        // when // then
        li.remove();
        System.out.println(list);
    }

    @Test
    void remove_메서드는_최근에_이동했던_위치의_원소를_제거한다() throws Exception {
        // given
        List<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6));
        ListIterator<Integer> li = list.listIterator();

        // when
        li.next();
        li.previous();

        // then
        li.remove();
        System.out.println(list);
    }
}