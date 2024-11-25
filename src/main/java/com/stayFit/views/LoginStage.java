package com.stayFit.views;

import com.stayFit.login.LoginController;
import com.stayFit.login.LoginDAO;
import com.stayFit.login.LoginGetUseCase;
import com.stayFit.login.LoginRequestDTO;
import com.stayFit.registration.ResponseUserDTO;
import com.stayFit.repository.DBConnector;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.InputStream;

public class LoginStage extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private TextField passwordTextField;
    private Button loginButton;
    private Label registerLabel;
    private Button togglePasswordVisibilityButton;

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
        usernameField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);

        passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setMaxWidth(250);
        passwordTextField.setVisible(false);

        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        togglePasswordVisibilityButton = new Button();
        togglePasswordVisibilityButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        InputStream eyeStream = getClass().getClassLoader().getResourceAsStream("icons/eye.png");
        InputStream eyeOffStream = getClass().getClassLoader().getResourceAsStream("icons/eye-off.png");

        if (eyeStream == null || eyeOffStream == null) {
            showAlert("Icone non trovate", Alert.AlertType.ERROR);
            return;
        }

        Image eyeImage = new Image(eyeStream);
        Image eyeOffImage = new Image(eyeOffStream);

        ImageView eyeImageView = new ImageView(eyeImage);
        eyeImageView.setFitWidth(20);
        eyeImageView.setFitHeight(20);
        eyeImageView.setPreserveRatio(true);

        ImageView eyeOffImageView = new ImageView(eyeOffImage);
        eyeOffImageView.setFitWidth(20);
        eyeOffImageView.setFitHeight(20);
        eyeOffImageView.setPreserveRatio(true);

        togglePasswordVisibilityButton.setGraphic(eyeOffImageView);

        togglePasswordVisibilityButton.setOnAction(event -> {
            if (passwordField.isVisible()) {
                passwordField.setVisible(false);
                passwordTextField.setVisible(true);
                togglePasswordVisibilityButton.setGraphic(eyeImageView);
            } 
            else {
                passwordField.setVisible(true);
                passwordTextField.setVisible(false);
                togglePasswordVisibilityButton.setGraphic(eyeOffImageView);
            }
        });


        StackPane passwordPane = new StackPane();
        passwordPane.getChildren().addAll(passwordField, passwordTextField);
        passwordPane.setMaxWidth(Double.MAX_VALUE);


        HBox passwordFieldContainer = new HBox();
        passwordFieldContainer.setAlignment(Pos.CENTER_LEFT);
        passwordFieldContainer.setSpacing(5);
        passwordFieldContainer.setMaxWidth(240);

        passwordFieldContainer.getChildren().addAll(passwordPane, togglePasswordVisibilityButton);

        HBox.setHgrow(passwordPane, Priority.ALWAYS);

        VBox.setMargin(passwordFieldContainer, new Insets(0, 0, 0, 40));

        loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        loginButton.setOnAction(event -> handleLogin(primaryStage));

        registerLabel = new Label("Non hai un account? Registrati qui.");
        registerLabel.setTextFill(Color.BLUE);
        registerLabel.setUnderline(true);
        registerLabel.setOnMouseClicked(event -> openRegisterForm(primaryStage));

        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordFieldContainer, loginButton, registerLabel);

        Scene loginScene = new Scene(loginLayout, 400, 400);
        primaryStage.setTitle("StayFit - Login");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleLogin(Stage primaryStage) {
        String username = usernameField.getText();
        String password = passwordField.isVisible() ? passwordField.getText() : passwordTextField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Inserisci sia username/email che password", Alert.AlertType.WARNING);
        } 
        else {
            LoginController loginController = new LoginController(new LoginGetUseCase(new LoginDAO(new DBConnector())));
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
            try {
                ResponseUserDTO response = loginController.login(loginRequestDTO);        		
                MainStage mainForm = new MainStage(response);
                primaryStage.close();
                mainForm.start(new Stage());
            }
            catch(Exception ex) {
            	ex.printStackTrace();
                showAlert(ex.getMessage(), Alert.AlertType.WARNING);
            }
        }
    }

    private void openRegisterForm(Stage primaryStage) {
        RegistrationStage registerForm = new RegistrationStage();
        primaryStage.close();
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
