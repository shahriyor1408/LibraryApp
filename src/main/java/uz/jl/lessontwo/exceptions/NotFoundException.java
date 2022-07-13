package uz.jl.lessontwo.exceptions;

import javax.servlet.ServletException;

public class NotFoundException extends ServletException {
    public NotFoundException(String message) {
        super(message);
    }


}
