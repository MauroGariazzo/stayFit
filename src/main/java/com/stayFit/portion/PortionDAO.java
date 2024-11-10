package com.stayFit.portion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.stayFit.models.Portion;
import com.stayFit.repository.DBConnector;

public class PortionDAO implements IPortionDAO {
	private DBConnector dbConnector;

	public PortionDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public void upsert(PortionCreateRequestDTO pcrDTO) throws Exception {
	    String query = "INSERT INTO stayfit.portion(product_fk, meal_fk, grams, calories, proteins, fats, carbs, sugars, salt) "
	                 + "VALUES(?,?,?,?,?,?,?,?,?) "
	                 + "ON DUPLICATE KEY UPDATE "
	                 + "grams = grams + VALUES(grams), "
	                 + "calories = calories + VALUES(calories), "
	                 + "proteins = proteins + VALUES(proteins), "
	                 + "fats = fats + VALUES(fats), "
	                 + "carbs = carbs + VALUES(carbs), "
	                 + "sugars = sugars + VALUES(sugars), "
	                 + "salt = salt + VALUES(salt)";

	    try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
	        pstmt.setInt(1, pcrDTO.product_fk);
	        pstmt.setInt(2, pcrDTO.meal_fk);
	        pstmt.setDouble(3, pcrDTO.grams);
	        pstmt.setDouble(4, pcrDTO.calories);
	        pstmt.setDouble(5, pcrDTO.proteins);
	        pstmt.setDouble(6, pcrDTO.fats);
	        pstmt.setDouble(7, pcrDTO.carbs);
	        pstmt.setDouble(8, pcrDTO.sugars);
	        pstmt.setDouble(9, pcrDTO.salt);
	        pstmt.execute();
	    } 
	    catch (Exception ex) {
	        throw new Exception(ex.getMessage());
	    }
	}


	public List<Portion> getMealPortions(PortionGetRequestDTO pgrDTO) throws Exception {		
		String query = "SELECT p.id AS portion_id, p.grams, p.calories, p.carbs, p.proteins, p.fats, p.sugars, "
				+ "p.salt, m.mealUpdateDate, m.mealType, pr.id AS product_id, pr.product_name, m.id AS meal_fk "
				+ "FROM stayfit.portion p JOIN stayfit.meal m ON p.meal_fk = m.id JOIN stayfit.product pr ON p.product_fk = pr.id"
				+ " WHERE m.mealType = ? AND m.mealupdatedate = ? AND fk_user = ?;";
		
		List<Portion> portions = new ArrayList<>();
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setInt(1, pgrDTO.mealType.ordinal());
			pstmt.setDate(2, Date.valueOf(pgrDTO.mealUpdateDate));
			pstmt.setInt(3, pgrDTO.fk_user);
			pstmt.execute();
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {					

					Portion portion = new Portion(rs.getInt("portion_id"), rs.getString("product_name"),
							rs.getInt("product_id"), rs.getInt("meal_fk"), rs.getDouble("grams"), 
							rs.getDouble("calories"), rs.getDouble("proteins"), rs.getDouble("fats"), 
							rs.getDouble("carbs"), rs.getDouble("sugars"), rs.getDouble("salt"), rs.getInt("mealType"));
										
					portions.add(portion);
				}
			}
		} 
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		return portions;
	}
	
	public void delete(int id)throws Exception {
		String query = "DELETE FROM stayfit.portion WHERE id = ?";
		
		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, id);
			pstmt.execute();
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
}
