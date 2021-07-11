package com.aquilesd.coursemc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private static final long serialVersionUID = 1L ;

    private List<FieldMessage> error = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getError() {
        return error;
    }

    public void addError(String name, String messagem) {
       error.add(new FieldMessage(name, messagem));
    }
}
