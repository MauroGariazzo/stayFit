package com.stayFit.user;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.User;
import com.stayFit.repository.DBConnector;

public class UserDAO implements IUserDAO{
	private DBConnector dbConnector;
	
	public UserDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	public User getUserInfo(int idUser) throws Exception {		
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
			throw new Exception(ex.getMessage());
		}
		finally {
			dbConnector.closeConnection();
		}
		return user;
	}
	
	public void update(RequestUpdateUserDTO request)throws Exception{
		String query = "UPDATE stayfit.stayfituser SET stayfitUser_name = ?, stayfitUser_surname = ?, height = ?, "
				+ "weight = ? , fitnessState = ?, birthday = ?, gender = ?, goal = ? WHERE ID = ?";		
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)){
			pstmt.setString(1, request.name);
			pstmt.setString(2, request.surname);
			pstmt.setInt(3, request.height);
			pstmt.setDouble(4, request.weight);
			pstmt.setInt(5, request.fitnessState.ordinal());
			pstmt.setDate(6, Date.valueOf(request.birthday));
			pstmt.setInt(7, request.gender.ordinal());
			pstmt.setInt(8, request.goal.ordinal());
			pstmt.setInt(9, request.id);
			
			pstmt.execute();
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		finally {
			dbConnector.closeConnection();
		}
	}
}
