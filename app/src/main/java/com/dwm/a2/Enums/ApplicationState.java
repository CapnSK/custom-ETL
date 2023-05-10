package com.dwm.a2.Enums;

/**
 * @implNote These are the states the application can be present in at any given time
 */
public enum ApplicationState {
    INIT("INIT"), EXTRACT("EXTRACT"), TRANSFORM("TRANSFORM"), LOAD("LOAD"), QUEUE("QUEUE"), EXIT("EXIT");

    private final String label;

    private ApplicationState(String label){
        this.label= label;
    }

    public String getLabel(){
        return label;
    }
}