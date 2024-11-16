package com.stayFit.enums;

public enum Goal {
	PERDERE_PESO(0),
    MANTENERE_PESO(1),
    METTERE_MASSA_MUSCOLARE(2);
    
    private final int code;
    Goal(int code) {
        this.code = code;
    }
	
	public int getCode() {
        return code;
    }


    public static String getStringFromCode(int code)throws IllegalArgumentException {
        for (Goal goal : Goal.values()) {
            if (goal.getCode() == code) {
                return goal.name();
            }
        }
        throw new IllegalArgumentException("Codice non trovato");
    }
    
    public static String getStringForStageView(Goal goal) {
    	switch(goal) {
    		case PERDERE_PESO:
    			return "Perdere peso";
    		case MANTENERE_PESO:
    			return "Mantenere peso";
    		case METTERE_MASSA_MUSCOLARE:
    			return "Mettere massa muscolare";
    		default:
    			return "";
    	}
    }
}
