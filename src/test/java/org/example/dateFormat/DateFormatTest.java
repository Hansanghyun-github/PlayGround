package org.example.dateFormat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DateFormatTest {
    DateFormat dateFormat = new DateFormat();
    @Test
    void hyphen_date_formatting_test() throws Exception {
        // given
        String dateHyphen = dateFormat.dateHyphen;
        
        // when
        LocalDate parse = LocalDate.parse(dateHyphen);

        // then
        System.out.println(parse);
    }

    @Test
    void slash_date_formatting_test() throws Exception {
        // given
        String dateSlash = dateFormat.dateSlash;

        // when
        LocalDate parse = LocalDate.parse(dateSlash, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        // then
        System.out.println(parse);
    }
    
    @Test
    void dot_date_formatting_test() throws Exception {
        // given
        String dateDot = dateFormat.dateDot;
        
        // when
        LocalDate parse = LocalDate.parse(dateDot, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        
        // then
        System.out.println(parse);
    }

    @Test
    void no_delimiter_date_formatting_test() throws Exception {
        // given
        String dateWithoutDelimiter = dateFormat.dateWithoutDelimiter;

        // when
        LocalDate parse = LocalDate.parse(dateWithoutDelimiter, DateTimeFormatter.BASIC_ISO_DATE);

        // then
        System.out.println(parse);
    }

    @Test
    void LocalDate_to_string() throws Exception {
        // given
        LocalDate localDate1 = LocalDate.of(2021, 7, 1);

        // when
        String format = localDate1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // then
        assertThat(format).isEqualTo("2021-07-01");
    }
}