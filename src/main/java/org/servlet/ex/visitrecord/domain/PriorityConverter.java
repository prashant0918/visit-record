package org.servlet.ex.visitrecord.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PriorityConverter implements AttributeConverter<VisitPriority, Integer> {

    @Override
    public Integer convertToDatabaseColumn(VisitPriority attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public VisitPriority convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : VisitPriority.fromValue(dbData);
    }
}

