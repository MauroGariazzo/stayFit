package com.stayFit.nutrientsReport;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.stayFit.repository.DBConnector;

public class NutrientsReportDAO implements INutrientsReportDAO{
	private DBConnector dbConnector;
	
	public NutrientsReportDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	/*Giornaliero*/
	public List<NutrientsReportGetResponseDTO> getDailyNutrients(int user_fk) throws Exception{
		String query = "SELECT DATE(m.mealUpdateDate) AS day, SUM(p.calories) AS total_calories, "
				+ "SUM(p.proteins) AS total_proteins, "
				+ "SUM(p.fats) AS total_fats, "
				+ "SUM(p.sugars) AS total_sugars, "
				+ "SUM(p.salt) AS total_salt, "
				+ "SUM(p.carbs) AS total_carbs "
				+ "FROM portion p "
				+ "JOIN meal m "
				+ "ON p.meal_fk = m.id "
				+ "WHERE fk_user = ? "
				+ "GROUP BY DATE(m.mealUpdateDate) "
				+ "ORDER BY day;";
		
		List<NutrientsReportGetResponseDTO> dailyReport = new ArrayList<>();

		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, user_fk);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					Date date = rs.getDate("day");	
					dailyReport.add(new NutrientsDailyReportGetResponseDTO(date.getYear()+1900, date.getMonth()+1,
							date.getDate(), rs.getDouble("total_calories"), rs.getDouble("total_proteins"),
							rs.getDouble("total_fats"), rs.getDouble("total_carbs")));
				}
			}
			catch(Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		finally {
			dbConnector.closeConnection();
		}
		return dailyReport;
	}
	
	/*Settimanale*/
	public List<NutrientsReportGetResponseDTO> getWeeklyNutrients(int user_fk) throws Exception{
		String query = "SELECT YEAR(m.mealUpdateDate) AS year, WEEK(m.mealUpdateDate) AS week, SUM(p.calories) "
				+ "AS total_calories, SUM(p.proteins) AS total_proteins, SUM(p.fats) AS total_fats, SUM(p.sugars) "
				+ "AS total_sugars, SUM(p.salt) AS total_salt, SUM(p.carbs) AS total_carbs "
				+ "FROM portion p "
				+ "JOIN meal m "
				+ "ON p.meal_fk = m.id "
				+ "WHERE fk_user = ? "
				+ "GROUP BY YEAR(m.mealUpdateDate), "
				+ "WEEK(m.mealUpdateDate) "
				+ "ORDER BY year, week;";				
		
		List<NutrientsReportGetResponseDTO> weeklyReport = new ArrayList<>();

		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, user_fk);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					
					weeklyReport.add(new NutrientsWeeklyReportGetResponseDTO(rs.getInt("year"), rs.getInt("week"),
							rs.getDouble("total_calories"), rs.getDouble("total_proteins"), rs.getDouble("total_fats"), 
							rs.getDouble("total_carbs")));
				}
			}
			catch(Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		finally {
			dbConnector.closeConnection();
		}
		return weeklyReport;
	}
	
	/*Mensile*/
	public List<NutrientsReportGetResponseDTO> getMonthlyNutrients(int user_fk) throws Exception{
		String query = "SELECT YEAR(m.mealUpdateDate) AS year, MONTH(m.mealUpdateDate) AS month, SUM(p.calories) "
				+ "AS total_calories, SUM(p.proteins) AS total_proteins, SUM(p.fats) AS total_fats, "
				+ "SUM(p.sugars) AS total_sugars, SUM(p.salt) AS total_salt, SUM(p.carbs) AS total_carbs "
				+ "FROM portion p "
				+ "JOIN meal m "
				+ "ON p.meal_fk = m.id "
				+ "WHERE fk_user = ? "
				+ "GROUP BY YEAR(m.mealUpdateDate), "
				+ "MONTH(m.mealUpdateDate) "
				+ "ORDER BY year, month;";				
		
		List<NutrientsReportGetResponseDTO> monthlyReport = new ArrayList<>();

		try(PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, user_fk);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					
					monthlyReport.add(new NutrientsMonthlyReportGetResponseDTO(rs.getInt("year"), rs.getInt("month"),
							rs.getDouble("total_calories"), rs.getDouble("total_proteins"), rs.getDouble("total_fats"), 
							rs.getDouble("total_carbs")));
				}
			}
			catch(Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		finally {
			dbConnector.closeConnection();
		}
		return monthlyReport;
	}
}


/*int year, int week, double calories, double proteins,
			double fats, double carbs*/