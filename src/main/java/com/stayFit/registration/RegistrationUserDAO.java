package com.stayFit.registration;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.User;
import com.stayFit.models.UserCredentials;
import com.stayFit.repository.DBConnector;

public class RegistrationUserDAO implements IRegistrationUserDAO{
	private DBConnector dbConnector;
	public RegistrationUserDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public User insert(RequestCreateUserDTO registrationRequestDTO)throws Exception{		
		String query = "INSERT INTO stayfit.stayfituser(stayFitUser_name, stayFitUser_surname, "
				+ "height, weight, fitnessState, bmi, birthday, subscriptionDate, gender, goal, userCredentials_fk) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		System.out.println(registrationRequestDTO.userCredentials_fk);
				
		try(PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			pstmt.setString(1, registrationRequestDTO.name);
			pstmt.setString(2, registrationRequestDTO.surname);
			pstmt.setInt(3, registrationRequestDTO.height);
			pstmt.setDouble(4, registrationRequestDTO.weight);
			pstmt.setInt(5, registrationRequestDTO.fitnessState.ordinal());			
			pstmt.setDouble(6, registrationRequestDTO.BMI);
			pstmt.setDate(7, Date.valueOf(registrationRequestDTO.birthday));
			pstmt.setDate(8, Date.valueOf(registrationRequestDTO.subscriptionDate));
			pstmt.setInt(9, registrationRequestDTO.gender.ordinal());
			pstmt.setInt(10, registrationRequestDTO.goal.ordinal());
			pstmt.setInt(11, registrationRequestDTO.userCredentials_fk);
			
			pstmt.execute();
						
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int generatedId = generatedKeys.getInt(1);	                
	                return new User(generatedId, registrationRequestDTO.name, registrationRequestDTO.surname, 
	                		registrationRequestDTO.height, registrationRequestDTO.weight, registrationRequestDTO.birthday,
	                		registrationRequestDTO.fitnessState, registrationRequestDTO.BMI, registrationRequestDTO.gender,
	                		registrationRequestDTO.goal, registrationRequestDTO.subscriptionDate,
	                		registrationRequestDTO.userCredentials_fk);
	            } 
	            else {
	                throw new Exception("Creazione utente fallita, nessun ID ottenuto.");
	            }
	        }	
		}
		catch(SQLIntegrityConstraintViolationException ex) {
			ex.printStackTrace();
			throw new Exception("Username o mail già presenti nel nostro sistema");
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());			
		}
		
		finally {
			dbConnector.closeConnection();
		}
	}	
}
