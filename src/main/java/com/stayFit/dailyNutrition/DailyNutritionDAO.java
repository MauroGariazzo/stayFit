package com.stayFit.dailyNutrition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.stayFit.models.DailyNutrition;
import com.stayFit.repository.DBConnector;

public class DailyNutritionDAO implements IDailyNutritionDAO {
	private DBConnector dbConnector;

	public DailyNutritionDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public DailyNutrition insert(DailyNutritionRequestCreateDTO dailyNutritionRequestCreateDTO) throws Exception {
		String query = "INSERT INTO stayfit.dailyNutrition(calories, proteins, carbs, fats, "
				+ "fkuser) VALUES (?,?,?,?,?)";

		int generatedId = -1;
		try (PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, dailyNutritionRequestCreateDTO.calories);
			pstmt.setInt(2, dailyNutritionRequestCreateDTO.proteins);
			pstmt.setInt(3, dailyNutritionRequestCreateDTO.carbs);
			pstmt.setInt(4, dailyNutritionRequestCreateDTO.fats);
			pstmt.setInt(5, dailyNutritionRequestCreateDTO.fk_user);
			pstmt.execute();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getInt(1);

					return new DailyNutrition(generatedId, dailyNutritionRequestCreateDTO.calories,
							dailyNutritionRequestCreateDTO.proteins, dailyNutritionRequestCreateDTO.carbs,
							dailyNutritionRequestCreateDTO.fats, dailyNutritionRequestCreateDTO.fk_user);
				} else {
					throw new Exception("Creazione dieta fallita, nessun ID ottenuto.");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}

	public DailyNutrition get(int userFk) throws Exception {
		System.out.println("user: " + userFk);
		String query = "SELECT * FROM stayfit.dailyNutrition WHERE fkuser = ?";
		DailyNutrition diet = new DailyNutrition();
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setInt(1, userFk);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					diet.setId(rs.getInt("id"));
					diet.setCalories(rs.getInt("calories"));
					diet.setProteins(rs.getInt("proteins"));
					diet.setCarbs(rs.getInt("carbs"));
					diet.setFats(rs.getInt("fats"));
				}
			}
		} 
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}	
		System.out.println(diet.getCalories());
		return diet;
	}

	public DailyNutrition update(DailyNutritionRequestUpdateDTO dietRequestUpdate) throws Exception {
		String query = "UPDATE stayfit.dailyNutrition SET calories = ?, proteins = ?, carbs = ?, fats = ?"
				+ " WHERE fkuser = ? ";
		
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setInt(1, dietRequestUpdate.calories);
			pstmt.setInt(2, dietRequestUpdate.proteins);
			pstmt.setInt(3, dietRequestUpdate.carbs);
			pstmt.setInt(4, dietRequestUpdate.fats);
			pstmt.setInt(5, dietRequestUpdate.fk_user);
			pstmt.execute();
		} 
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return new DailyNutrition(dietRequestUpdate.calories, dietRequestUpdate.proteins,
				dietRequestUpdate.carbs, dietRequestUpdate.fats, dietRequestUpdate.fk_user);
	}
}
