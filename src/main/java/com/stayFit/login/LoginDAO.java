package com.stayFit.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.User;
import com.stayFit.repository.DBConnector;

public class LoginDAO implements ILoginDAO {
	private DBConnector dbConnector;

	public LoginDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public User login(LoginRequestDTO loginRequestDTO) throws Exception {		
		String query = "SELECT u.id as user_id, u.stayfituser_name, u.stayfituser_surname, u.height, u.weight, "
				+ "u.fitnessState, u.bmi, u.birthday, u.subscriptionDate, u.gender, u.goal, uc.id as userCredentialsId "
				+ "FROM stayfit.stayfitUser u " + "JOIN stayfit.UserCredentials uc ON u.userCredentials_fk = uc.id "
				+ "WHERE uc.stayfituser_username = ? AND uc.stayFitUser_password = ?;";

		User user = new User();
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setString(1, loginRequestDTO.username);
			pstmt.setString(2, loginRequestDTO.password);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Gender gender = Gender.valueOf(Gender.getStringFromCode(rs.getInt("gender")));
					Goal goal = Goal.valueOf(Goal.getStringFromCode(rs.getInt("goal")));
					FitnessState fitness = FitnessState
							.valueOf(FitnessState.getStringFromCode(rs.getInt("fitnessState")));

					user = new User(rs.getInt("user_id"), rs.getString("stayfituser_name"),
							rs.getString("stayfituser_surname"), rs.getInt("height"), rs.getDouble("weight"),
							rs.getDate("birthday").toLocalDate(), fitness, rs.getDouble("BMI"), gender, goal,
							rs.getDate("subscriptionDate").toLocalDate(), rs.getInt("userCredentialsId"));
				}
			}
			return user;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
		finally {
			dbConnector.closeConnection();
		}
	}

	public String getCryptedPassword(LoginRequestDTO loginRequestDTO) throws Exception {
		String query = "SELECT stayfituser_password FROM stayfit.UserCredentials WHERE " + "stayfituser_username = ?";

		String password = "";
		try (PreparedStatement pstmt = dbConnector.getPreparedStatementObj(query)) {
			pstmt.setString(1, loginRequestDTO.username);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					password = rs.getString("stayfituser_password");
				}
				return password;
			} catch (Exception ex) {
				throw new Exception("Username o password errati");
			}
		}
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		finally {
			dbConnector.closeConnection();
		}
	}
}
