package com.stayFit.portion;

import java.util.List;

public interface IPortionCreateUseCase {
	public void execute(List<PortionCreateRequestDTO> portions, int id)throws Exception;
}
