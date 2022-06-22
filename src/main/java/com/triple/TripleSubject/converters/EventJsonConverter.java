package com.triple.TripleSubject.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.exceptions.CheckedException;

import javax.persistence.AttributeConverter;

public class EventJsonConverter implements AttributeConverter<EventDto,String> {

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(EventDto eventDto){

        try {
            return objectMapper.writeValueAsString(eventDto);
        } catch (JsonProcessingException e) {
            throw new CheckedException(e.getMessage());
        }
    }

    @Override
    public EventDto convertToEntityAttribute(String jsonString){
        try{
            return objectMapper.readValue(jsonString,EventDto.class);
        }catch (JsonProcessingException e) {
            throw new CheckedException(e.getMessage());
        }
    }
}
