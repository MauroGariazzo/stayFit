package com.stayFit.weightReport;

import java.sql.Date;
import java.sql.PreparedStatement;

import com.stayFit.repository.DBConnector;

public class WeightReportDAO implements IWeightReportDAO{
	private DBConnector dbConnector;
	
	public WeightReportDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void upsert(WeightReportCreateRequestDTO requestDTO) throws Exception {
	    String query = "INSERT INTO stayfit.weightDiary (weight, date, user_fk) " +
	                   "VALUES (?, ?, ?) " +
	                   "ON DUPLICATE KEY UPDATE " +
	                   "weight = VALUES(weight), " +
	                   "user_fk = VALUES(user_fk)";

	    try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
	        pstmt.setDouble(1, requestDTO.weight);
	        pstmt.setDate(2, Date.valueOf(requestDTO.weightDateRegistration));
	        pstmt.setInt(3, requestDTO.user_fk);
	        pstmt.execute();
	    } 
	    catch (Exception ex) {
	        throw new Exception(ex.getMessage());
	    }
	}
	
}
