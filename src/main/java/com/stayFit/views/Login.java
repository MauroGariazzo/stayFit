package com.stayFit.views;

import com.stayFit.login.LoginController;
import com.stayFit.login.LoginDAO;
import com.stayFit.login.LoginGetUseCase;
import com.stayFit.login.LoginRequestDTO;
import com.stayFit.repository.DBConnector;
import com.stayFit.registration.ResponseUserDTO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label registerLabel;

    @Override
    public void start(Stage primaryStage) {        
        VBox loginLayout = new VBox(20);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKSLATEGRAY);

        usernameField = new TextField();
        usernameField.setPromptText("Username o Email");
        usernameField.setMaxWidth(250);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);

        loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        loginButton.setOnAction(event -> handleLogin(primaryStage));

        registerLabel = new Label("Non hai un account? Registrati qui.");
        registerLabel.setTextFill(Color.BLUE);
        registerLabel.setUnderline(true);
        registerLabel.setOnMouseClicked(event -> openRegisterForm());

        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, registerLabel);

        Scene loginScene = new Scene(loginLayout, 400, 400);
        primaryStage.setTitle("StayFit - Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void handleLogin(Stage primaryStage) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Inserisci sia username/email che password", Alert.AlertType.WARNING);
        } 
        else {
        	LoginController loginController = new LoginController(new LoginGetUseCase(new LoginDAO(new DBConnector())));
        	LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        	try {
        		ResponseUserDTO response = loginController.login(loginRequestDTO);
        		MainForm mainForm = new MainForm(response);
        		primaryStage.close();
        		mainForm.start(new Stage());
        	}
        	catch(Exception ex) {
        		showAlert(ex.getMessage(), Alert.AlertType.WARNING);
        	}
        }
    }

    private void openRegisterForm() {
        RegistrationForm registerForm = new RegistrationForm();
        registerForm.show();
    }


    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
