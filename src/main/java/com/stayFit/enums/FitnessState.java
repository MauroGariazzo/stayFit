package com.stayFit.enums;

public enum FitnessState {
	SEDENTARIO(0), POCO_ATTIVO(1), MEDIAMENTE_ATTIVO(2), MOLTO_ATTIVO(3);

	private final int code;

	FitnessState(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static String getStringFromCode(int code) throws IllegalArgumentException {
		for (FitnessState fitness : FitnessState.values()) {
			if (fitness.getCode() == code) {
				return fitness.name();
			}
		}
		throw new IllegalArgumentException("Codice non trovato");
	}

	public static String getStringForStageView(FitnessState fitnessState) {
		switch (fitnessState) {
		case SEDENTARIO:
			return "Sedentario";
		case POCO_ATTIVO:
			return "Poco attivo";
		case MEDIAMENTE_ATTIVO:
			return "Mediamente attivo";
		case MOLTO_ATTIVO:
			return "Molto attivo";
		default:
			return "";
		}
	}
}
