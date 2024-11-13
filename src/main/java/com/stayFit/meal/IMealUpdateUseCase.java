package com.stayFit.meal;

import java.time.LocalDate;

public interface IMealUpdateUseCase {
	public void terminateDay(LocalDate date)throws Exception;
}
