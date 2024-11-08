package com.stayFit.portion;

import java.sql.PreparedStatement;

import com.stayFit.repository.DBConnector;

public class PortionDAO implements IPortionDAO {
	private DBConnector dbConnector;
	
	public PortionDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void insert(PortionCreateRequestDTO pcrDTO) throws Exception{
		System.out.println("meal_fk: " + pcrDTO.meal_fk);
		System.out.println("product_fk: " + pcrDTO.product_fk);
		String query = "INSERT INTO portion(product_fk, meal_fk, grams) VALUES(?,?,?)";
		
		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, pcrDTO.product_fk);
			pstmt.setInt(2, pcrDTO.meal_fk);
			pstmt.setDouble(3, pcrDTO.grams);
			
			pstmt.execute();
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
