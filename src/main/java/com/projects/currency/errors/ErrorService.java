package com.projects.currency.errors;

public class ErrorService extends Exception{
    public ErrorService(String msn) {
        super(msn);
    }
}
