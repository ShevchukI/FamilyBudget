package com.peryite.familybudget.utils;

public enum RegEx {
    WITHOUT_SPACE("^\\S+$");

    private String fullName;

    RegEx(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
