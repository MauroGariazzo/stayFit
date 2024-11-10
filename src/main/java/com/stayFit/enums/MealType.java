package com.stayFit.enums;

public enum MealType {
	COLAZIONE(0),
	PRANZO(1),
	CENA(2),
	SPUNTINO(3);
	
	private final int code;
	MealType(int code) {
        this.code = code;
    }
	
	public int getCode() {
        return code;
    }


    public static String getStringFromCode(int code)throws IllegalArgumentException {
        for (MealType mealType : MealType.values()) {
            if (mealType.getCode() == code) {
                return mealType.name();
            }
        }
        throw new IllegalArgumentException("Codice non trovato");
    }
}
