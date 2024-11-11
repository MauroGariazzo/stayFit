package com.stayFit.enums;

public enum Gender {
    MASCHIO(0),
    FEMMINA(1);    
    
    private final int code;
	Gender(int code) {
        this.code = code;
    }
	
	public int getCode() {
        return code;
    }


    public static String getStringFromCode(int code)throws IllegalArgumentException {
        for (Gender gender : Gender.values()) {
            if (gender.getCode() == code) {
                return gender.name();
            }
        }
        throw new IllegalArgumentException("Codice non trovato");
    }
}

