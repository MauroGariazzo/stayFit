package com.stayFit.views;

import com.stayFit.registration.RegistrationUserCredentialsController;
import com.stayFit.enums.IsNewOrUpdate;
import com.stayFit.registration.RegistrationUserCredentialsCreateUseCase;
import com.stayFit.registration.RegistrationUserCredentialsDAO;
import com.stayFit.registration.RequestCreateUserCredentialsDTO;
import com.stayFit.registration.ResponseUserCredentialsDTO;
import com.stayFit.repository.DBConnector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationStage {

    public void show() {
        Stage registerStage = new Stage();
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.setTitle("Registrazione");
        
        VBox registerLayout = new VBox(15);
        registerLayout.setPadding(new Insets(20));
        registerLayout.setAlignment(Pos.CENTER);

        Label registerTitle = new Label("Registrazione");
        registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        registerTitle.setTextFill(Color.DARKSLATEGRAY);

        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("Username");

        TextField newEmailField = new TextField();
        newEmailField.setPromptText("Email");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Password");

        Button registerButton = new Button("Registrati");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        registerButton.setOnAction(event -> {
        	try{ 
        		ResponseUserCredentialsDTO response = createUserCredentials(newUsernameField, newPasswordField, 
        				newEmailField);
        		PersonalDataStage personalDataForm = new PersonalDataStage(response.id);
            	registerStage.close();
            	personalDataForm.show();
        	}
        	catch(Exception ex) {
        		showAlert(ex.getMessage(), Alert.AlertType.WARNING);
        	}
        });

        registerLayout.getChildren().addAll(registerTitle, newUsernameField, newEmailField, newPasswordField, registerButton);

        Scene registerScene = new Scene(registerLayout, 300, 300);
        registerStage.setScene(registerScene);
        registerStage.showAndWait();
    }
    
    private ResponseUserCredentialsDTO createUserCredentials(TextField newUsernameField, TextField newPasswordField, 
    		TextField newEmailField)throws Exception {
    	
    	RegistrationUserCredentialsController rc = new RegistrationUserCredentialsController(
    			new RegistrationUserCredentialsCreateUseCase(new RegistrationUserCredentialsDAO(new DBConnector())));

    	ResponseUserCredentialsDTO response = rc.create(new RequestCreateUserCredentialsDTO
    				(newUsernameField.getText(), newPasswordField.getText(), newEmailField.getText()));
    	
        return response;
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Attenzione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
