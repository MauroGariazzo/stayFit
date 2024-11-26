package com.stayFit.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.stayFit.models.Product;
import com.stayFit.repository.DBConnector;
import com.stayFit.utils.ImageUtil;

public class ProductDAO implements IProductDAO {
	private DBConnector dbConnector;

	public ProductDAO(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public void insert(ProductCreateRequestDTO product) throws Exception {
		String query = "INSERT INTO stayFit.product(product_name, product_brand, product_category, calories, proteins, "
				+ "fats, carbs, sugars, salt, image) VALUES(?,?,?,?,?,?,?,?,?,?) "
				+ "ON DUPLICATE KEY UPDATE product_name = product_name";

		try (PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query)) {			
			pstmt.setString(1, product.productName);
			pstmt.setString(2, product.brand);
			pstmt.setString(3, product.category);
			pstmt.setDouble(4, product.calories);
			pstmt.setDouble(5, product.proteins);
			pstmt.setDouble(6, product.fats);
			pstmt.setDouble(7, product.carbs);
			pstmt.setDouble(8, product.sugars);
			pstmt.setDouble(9, product.salt);
			byte[] imageBytes = ImageUtil.imageToByteArray(product.productImage);
			pstmt.setBytes(10, imageBytes);
			pstmt.execute();

		} 
		catch (SQLIntegrityConstraintViolationException ex) {
			throw new Exception("Prodotto già esistente");
		}
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		finally {
			dbConnector.closeConnection();
		}
	}
	
	public List<Product> get(ProductGetRequestDTO productDTO)throws Exception {
		String query = "SELECT * FROM stayFit.product " +
	               "WHERE product_name LIKE ? " +
	               "OR product_brand LIKE ? " +
	               "OR product_category LIKE ? " +
	               "ORDER BY " +
	               "CASE WHEN product_category = 'Generic' THEN 0 ELSE 1 END, " +
	               "product_name ASC " +
	               "LIMIT 50";

		List<Product> products = new ArrayList<>();	
		try(PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(query)){
			pstmt.setString(1, "%" + productDTO.productName + "%");
	        pstmt.setString(2, "%" + productDTO.brand + "%");
	        pstmt.setString(3, "%" + productDTO.category+ "%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setProductName(rs.getString("product_name"));
				product.setBrand(rs.getString("product_brand"));
				product.setCategory(rs.getString("product_category"));
				
				product.setCalories(rs.getDouble("calories"));
				product.setProteins(rs.getDouble("proteins"));
				product.setFats(rs.getDouble("fats"));
				product.setCarbs(rs.getDouble("carbs"));
				product.setSugars(rs.getDouble("sugars"));
				product.setSalt(rs.getDouble("salt"));				
				product.setProductImage(ImageUtil.byteArrayToImage(rs.getBytes("image")));
				products.add(product);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally {
			dbConnector.closeConnection();
		}		
		return products;
	}
}
