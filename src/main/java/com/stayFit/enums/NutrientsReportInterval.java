package com.stayFit.enums;

public enum NutrientsReportInterval {
    GIORNALIERO(0),
	SETTIMANALE(1),
    MENSILE(2);
	
	private final int code;
	NutrientsReportInterval(int code) {
        this.code = code;
    }
	
	public int getCode() {
        return code;
    }
	
	public static String getStringFromCode(int code)throws IllegalArgumentException {
        for (NutrientsReportInterval reportInterval : NutrientsReportInterval.values()) {
            if (reportInterval.getCode() == code) {
                return reportInterval.name();
            }
        }
        throw new IllegalArgumentException("Codice non trovato");
    }
    
    public static String getStringForStageView(NutrientsReportInterval reportInterval) {
    	switch(reportInterval) {
    		case GIORNALIERO:
    			return "Giornaliero";
    		case SETTIMANALE:
    			return "Settimanale";
    		case MENSILE:
    			return "Mensile";    	
    		default:
    			return "";
    	}
    }
}
