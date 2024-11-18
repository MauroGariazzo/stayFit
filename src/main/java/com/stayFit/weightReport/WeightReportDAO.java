package com.stayFit.weightReport;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.stayFit.models.WeightDiary;
import com.stayFit.repository.DBConnector;

public class WeightReportDAO implements IWeightReportDAO{
	private DBConnector dbConnector;
	
	public WeightReportDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void upsert(WeightReportCreateRequestDTO requestDTO) throws Exception {
	    String query = "INSERT INTO stayfit.weightDiary (weight, weightDateRegister, user_fk) " +
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
	    finally {
			dbConnector.closeConnection();
		}
	}
	
	
	public List<WeightDiary> getDailyWeightReport(int userId)throws Exception {
		String query = "SELECT * FROM stayfit.weightDiary WHERE user_fk = ? ORDER BY weightDateRegister;";
		List<WeightDiary>diary = new ArrayList<>();
		
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
	        pstmt.setDouble(1, userId);
	        //pstmt.execute();
	        try(ResultSet rs = pstmt.executeQuery()){
	        	while(rs.next()) {
	        		diary.add(new WeightDiary(rs.getInt("id"), rs.getDouble("weight"), 
		        			rs.getDate("weightDateRegister").toLocalDate(), rs.getInt("user_fk")));
	        	}	        	
	        }	        
	    } 
	    catch (Exception ex) {
	    	ex.printStackTrace();
	        throw new Exception(ex.getMessage());
	    }
		finally {
			dbConnector.closeConnection();
		}
		return diary;
	}
	
	
}
