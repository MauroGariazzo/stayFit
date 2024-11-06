package com.stayFit.registration;

import java.sql.PreparedStatement;
import java.time.LocalDate;

import com.stayFit.enums.FitnessState;
import com.stayFit.models.User;
import com.stayFit.repository.DBConnector;

import java.sql.*;

public class RegistrationDAO implements IRegistrationDAO {
	private DBConnector dbConnector;
	
	public RegistrationDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public User register(RegistrationRequestDTO registrationRequestDTO)throws Exception{
		String query = "INSERT INTO(stayFitUser_name, stayFitUser_surname, height, weight, fitnessState,"
				+ "stayFitUserUsername, stayFitUserPassword, bmi) VALUES(?,?,?,?,?,?,?,?)";
		
		
		try(PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			pstmt.setString(1, registrationRequestDTO.name);
			pstmt.setString(2, registrationRequestDTO.surname);
			pstmt.setInt(3, registrationRequestDTO.height);
			pstmt.setDouble(4, registrationRequestDTO.weight);
			pstmt.setInt(5, registrationRequestDTO.fitnessState.ordinal());
			pstmt.setString(6, registrationRequestDTO.username);
			pstmt.setString(7, registrationRequestDTO.password);
			pstmt.setDouble(8, registrationRequestDTO.BMI);
			
			pstmt.execute();
			
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int generatedId = generatedKeys.getInt(1);
	
	                return new User(generatedId, registrationRequestDTO.name, registrationRequestDTO.surname, 
	                		registrationRequestDTO.height, registrationRequestDTO.weight, registrationRequestDTO.birthday,
	                		registrationRequestDTO.fitnessState, registrationRequestDTO.username, registrationRequestDTO.password, registrationRequestDTO.BMI);
	            } 
	            else {
	                throw new Exception("Creazione utente fallita, nessun ID ottenuto.");
	            }
	        }			
		}
		
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
				
	}
}
