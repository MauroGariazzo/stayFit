package com.stayFit.repository;

import java.util.Map;
import java.util.HashMap;
import com.stayFit.models.Product;
import java.sql.*;

public class DAOProduct {	
	private DBConnector dbConnector;
	
	public DAOProduct() {
		
		this.dbConnector = new DBConnector("jdbc:mysql://localhost:3306/stayfit", "root", "Bitcamp_0");
	}
	
	public Map<Integer, Product> getAll(){
		String query = "SELECT * FROM stayfit.product";
		
		
		
		return new HashMap<Integer, Product>();
	}
}
