package com.stayFit.registration;

import java.sql.PreparedStatement;
import com.stayFit.models.UserCredentials;
import com.stayFit.repository.DBConnector;
import java.sql.*;

public class RegistrationUserCredentialsDAO implements IRegistrationUserCredentialsDAO {
	private DBConnector dbConnector;
	
	public RegistrationUserCredentialsDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public UserCredentials register(RequestCreateUserCredentialsDTO registrationRequestDTO)throws Exception{
		String query = "INSERT INTO stayfit.userCredentials(stayFitUser_email, stayFitUser_Username, "
				+ "stayFitUser_Password) VALUES(?,?,?)";
				
		try(PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			pstmt.setString(1, registrationRequestDTO.email);
			pstmt.setString(2, registrationRequestDTO.username);
			pstmt.setString(3, registrationRequestDTO.password);			
			pstmt.execute();
			
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int generatedId = generatedKeys.getInt(1);	                
	                return new UserCredentials(generatedId, registrationRequestDTO.username, 
	                		registrationRequestDTO.password, registrationRequestDTO.email);
	            } 
	            else {
	                throw new Exception("Creazione utente fallita, nessun ID ottenuto.");
	            }
	        }			
		}
		catch(SQLIntegrityConstraintViolationException ex) {
			throw new Exception("Username o mail già presenti nel nostro sistema");
		}
		
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		finally {
			dbConnector.closeConnection();
		}			
	}	
}
