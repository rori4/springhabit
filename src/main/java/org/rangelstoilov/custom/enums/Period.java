package org.rangelstoilov.custom.enums;

public enum Period {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly");


    private String name;

    // constructor to set the string
    Period(String name){this.name = name;}

    // the toString just returns the given name
    @Override
    public String toString() {
        return name;
    }
}
