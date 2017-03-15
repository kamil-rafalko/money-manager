package com.corriel.data.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;
import java.sql.Date;

@Converter(autoApply = true)
public class YearMonthConverter implements AttributeConverter<YearMonth, Date> {

    private static final int FIRST_DAY_OF_MONTH = 1;

    @Override
    public Date convertToDatabaseColumn(YearMonth yearMonth) {
        return Date.valueOf(yearMonth.atDay(FIRST_DAY_OF_MONTH));
    }

    @Override
    public YearMonth convertToEntityAttribute(Date date) {
        return YearMonth.from(date.toLocalDate());
    }
}
