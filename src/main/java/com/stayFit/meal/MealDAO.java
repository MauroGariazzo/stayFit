package com.stayFit.meal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.stayFit.repository.DBConnector;

public class MealDAO {
	private DBConnector dbConnector;

	public MealDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public int insert(MealCreateRequestDTO mealCreateRequestDTO) throws Exception {
		String query = "INSERT INTO meal(mealUpdateDate, mealType, fk_user)VALUES(?,?,?)";
		int generatedId = -1;
		try (PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setDate(1, Date.valueOf(mealCreateRequestDTO.mealUpdateDate));
			pstmt.setInt(2, mealCreateRequestDTO.mealType.ordinal());
			pstmt.setInt(3, mealCreateRequestDTO.fk_user);

			pstmt.execute();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getInt(1);
					
				} 
				else {
					throw new Exception("Creazione utente fallita, nessun ID ottenuto.");
				}
			}
		} 
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return generatedId;
	}
}
