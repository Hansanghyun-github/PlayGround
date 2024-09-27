package org.example.dateFormat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumFormatTest {
    @Test
    void enum_error_test() throws Exception {
        // given
        String example = "EXAMPLE12";
        
        // when // then
        Assertions.assertThatThrownBy(() -> ExampleEnum.valueOf(example))
                .isInstanceOf(IllegalArgumentException.class);
    }

    enum ExampleEnum {
        EXAMPLE1,
        EXAMPLE2,
        EXAMPLE3
    }
}
