package com.indizen.cursoSpring.web.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ViewExpiredExceptionExceptionHandlerFactory extends ExceptionHandlerFactory {
 
    private ExceptionHandlerFactory parent;
 
    public ViewExpiredExceptionExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }
 
    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new ViewExpiredExceptionExceptionHandler(result);
 
        return result;
    }
 
 
}

