package com.Controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUtil
{
    static Map<String, Object> getErrors(BindingResult bindingResult) //static так как исп только в Controllers
    {
        Map<String,Object> errorMap= bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField()+"Error",
                FieldError::getDefaultMessage
        ));
        return errorMap;
    }
}
