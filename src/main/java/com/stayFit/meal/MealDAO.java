package com.stayFit.meal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.stayFit.enums.MealType;
import com.stayFit.models.Meal;
import com.stayFit.repository.DBConnector;

public class MealDAO implements IMealDAO {
	private DBConnector dbConnector;

	public MealDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public int insert(MealCreateRequestDTO mealCreateRequestDTO) throws Exception {
		System.out.println(mealCreateRequestDTO.mealUpdateDate);
		String query = "INSERT INTO stayFit.meal(mealUpdateDate, mealType, fk_user)VALUES(?,?,?)";
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

				} else {
					throw new Exception("Creazione utente fallita, nessun ID ottenuto.");
				}
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return generatedId;
	}

	public List<Meal> getAll(MealGetRequestDTO mealGetRequestDTO) throws Exception{
		String query = "SELECT * FROM stayFit.meal WHERE mealType = ? AND mealUpdateDate = ? AND fk_user = ?";
		List<Meal>meals = new ArrayList<>();
		try (PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query)) {
			pstmt.setInt(1, mealGetRequestDTO.mealType.ordinal());
			pstmt.setDate(2, Date.valueOf(mealGetRequestDTO.mealUpdateDate));
			pstmt.setInt(3, mealGetRequestDTO.fk_user);
			try(ResultSet rs = pstmt.executeQuery()){
				
				while(rs.next()) {	
					//int id, LocalDate date, MealType mealType, int fkUser
					Meal meal = new Meal(rs.getInt("id"), mealGetRequestDTO.mealUpdateDate, mealGetRequestDTO.mealType, mealGetRequestDTO.fk_user);
					meals.add(meal);
				}
			}
		}
		
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return meals;
	}
	
	/*public void update(MealCreateRequestDTO mealCreateRequestDTO) throws Exception {
		String query = "UPDATE stayFit.meal SET mealUpdateDate = ?, mealType = ?, fk_user = ?";

		try (PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setDate(1, Date.valueOf(mealCreateRequestDTO.mealUpdateDate));
			pstmt.setInt(2, mealCreateRequestDTO.mealType.ordinal());
			pstmt.setInt(3, mealCreateRequestDTO.fk_user);

			pstmt.execute();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getInt(1);

				} else {
					throw new Exception("Creazione utente fallita, nessun ID ottenuto.");
				}
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return generatedId;
	}*/
}
