package com.stayFit.user;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.User;
import com.stayFit.repository.DBConnector;

public class UserDAO {
	private DBConnector dbConnector;
	
	public UserDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	public User getUserInfo(int idUser) {		
		String query = "SELECT * FROM stayfit.stayfituser WHERE ID = ?";
		User user = new User();
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setInt(1, idUser);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					String fitness = FitnessState.getStringFromCode(rs.getInt("fitnessState"));
					String gender = Gender.getStringFromCode(rs.getInt("gender"));
					String goal = Goal.getStringFromCode(rs.getInt("goal"));
					user.setBirthday(rs.getDate("birthday").toLocalDate());
					user.setFitnessState(FitnessState.valueOf(fitness));
					user.setGender(Gender.valueOf(gender));
					user.setGoal(Goal.valueOf(goal));
					user.setHeight(rs.getInt("height"));
					user.setWeight(rs.getDouble("weight"));
					user.setName(rs.getString("stayfitUser_name"));
					user.setSurname(rs.getString("stayfitUser_surname"));
					user.setUserCredentials(rs.getInt("userCredentials_fk"));
				}
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return user;
	}
	
	public void save(User user){
		String query = "UPDATE stayfit.stayfituser SET stayfitUser_name = ?, stayfitUser_surname = ?, height = ?, "
				+ "weight = ? , fitnessState = ?, birthday = ?, gender = ?, goal = ? WHERE ID = ?";		
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getSurname());
			pstmt.setInt(3, user.getHeight());
			pstmt.setDouble(4, user.getWeight());
			pstmt.setInt(5, user.getFitnessState().ordinal());
			pstmt.setDate(6, Date.valueOf(user.getBirthday()));
			pstmt.setInt(7, user.getGender().ordinal());
			pstmt.setInt(8, user.getGoal().ordinal());
			pstmt.setInt(9, user.getId());
			
			pstmt.execute();
		}
		catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
	}
}
