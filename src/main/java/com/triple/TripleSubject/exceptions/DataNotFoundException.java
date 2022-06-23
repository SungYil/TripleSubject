package com.triple.TripleSubject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataNotFoundException extends CheckedException{
    private String message = "값을 찾을 수 없습니다.";
}
