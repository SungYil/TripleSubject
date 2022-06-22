package com.triple.TripleSubject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValidationException extends CheckedException{
    private String message = "유효한 값이 아닙니다.";
}
