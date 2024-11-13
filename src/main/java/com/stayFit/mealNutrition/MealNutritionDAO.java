package com.stayFit.mealNutrition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stayFit.enums.MealType;
import com.stayFit.models.MealNutrition;
import com.stayFit.repository.DBConnector;

public class MealNutritionDAO implements IMealNutritionDAO {
	private DBConnector dbConnector;

	public MealNutritionDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public void insert(MealNutritionCreateRequestDTO request) throws Exception {
		String query = "INSERT INTO stayfit.mealNutrition(daily_nutrition_fk, meal_type, calories,"
				+ "proteins, carbs, fats) VALUES(?,?,?,?,?,?)";

		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setInt(1, request.dailyNutritionFk);
			pstmt.setInt(2, request.mealType.ordinal());
			pstmt.setInt(3, request.calories);
			pstmt.setInt(4, request.proteins);
			pstmt.setInt(5, request.carbs);
			pstmt.setInt(6, request.fats);

			pstmt.execute();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public Map<MealType, MealNutrition> get(int daily_nutrition_fk) throws Exception {
		System.out.println("dailyNutrition: " + daily_nutrition_fk);
		String query = "SELECT * FROM stayfit.mealNutrition WHERE daily_nutrition_fk = ?";
		Map<MealType, MealNutrition> mealNutritions = new HashMap<>();

		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setInt(1, daily_nutrition_fk);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					
					String mealType = MealType.getStringFromCode(rs.getInt("meal_type"));					
					MealNutrition mealNutrition = new MealNutrition(rs.getInt("id"), MealType.valueOf(mealType),
							rs.getInt("calories"), rs.getInt("proteins"), rs.getInt("carbs"), rs.getInt("fats"),
							rs.getInt("daily_nutrition_fk"));
					mealNutritions.put(MealType.valueOf(mealType), mealNutrition);
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return mealNutritions;
	}
}
