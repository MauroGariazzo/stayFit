package com.stayFit.diet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.stayFit.models.Diet;
import com.stayFit.repository.DBConnector;

public class DietDAO implements ICreateDietDAO{
	private DBConnector dbConnector;
	
	public DietDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void insert(DietRequestCreateDTO dietRequestCreate)throws Exception{
		String query = "INSERT INTO stayfit.diet(mealType, calories, proteins, carbs, fats, "
				+ "fkuser) VALUES"
				+ "(?,?,?,?,?,?)";
		
		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, dietRequestCreate.mealType.ordinal());
			pstmt.setDouble(2, dietRequestCreate.calories);
			pstmt.setDouble(3, dietRequestCreate.proteins);
			pstmt.setDouble(4, dietRequestCreate.carbs);
			pstmt.setDouble(5, dietRequestCreate.fats);
			pstmt.setInt(6, dietRequestCreate.fk_user);
			pstmt.execute();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}		
	}
	
	public Diet get(int userFk) throws Exception{
		String query = "SELECT * FROM diet WHERE fkuser = ?";
		
		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, userFk);
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
