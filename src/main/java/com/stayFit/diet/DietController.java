package com.stayFit.diet;

public class DietController {
	private ICreateDietUseCase createDietUseCase;

	public DietController(ICreateDietUseCase createDietUseCase) {
		this.createDietUseCase = createDietUseCase;
	}

	public void create(UserInfoDTO userInfoDTO) throws Exception {
		createDietUseCase.create(userInfoDTO);
	}
}
