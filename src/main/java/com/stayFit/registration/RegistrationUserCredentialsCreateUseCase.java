package com.stayFit.registration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.stayFit.models.UserCredentials;

public class RegistrationUserCredentialsCreateUseCase implements IRegistrationUserCredentialsCreateUseCase{
	private IRegistrationUserCredentialsDAO registrationDAO;
	private BCryptPasswordEncoder passwordEncoder;
	 
	public RegistrationUserCredentialsCreateUseCase(IRegistrationUserCredentialsDAO registrationDAO) {
		this.registrationDAO = registrationDAO;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public UserCredentials create(RequestCreateUserCredentialsDTO registrationRequestDTO) throws Exception{
		registrationRequestDTO.password = CryptPassword(registrationRequestDTO);
		System.out.println(registrationRequestDTO.password);
		return registrationDAO.register(registrationRequestDTO);
	}
	
	public void update(RequestCreateUserDTO regitrationRequestDTO) throws Exception {
		
	}
	
	public String CryptPassword(RequestCreateUserCredentialsDTO registrationRequestDTO) {
		 return passwordEncoder.encode(registrationRequestDTO.password);
	}
}
