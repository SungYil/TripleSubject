package com.triple.TripleSubject.util;

import com.triple.TripleSubject.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    public void validateUuid(String uuid,String message){
        Pattern idPattern=Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
        if(!idPattern.matcher(uuid).matches())throw new ValidationException(message);
    }
}
