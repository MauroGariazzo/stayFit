package com.stayFit.views;

import java.io.InputStream;

import com.stayFit.registration.*;
import com.stayFit.repository.DBConnector;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RegistrationStage {
    private Button togglePasswordVisibilityButton;

    public void show() {
        Stage registerStage = new Stage();
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.setTitle("Registrazione");

        VBox registerLayout = new VBox(15);
        registerLayout.setPadding(new Insets(20));
        registerLayout.setAlignment(Pos.CENTER);
        registerLayout.setPrefWidth(450);
        
        Label registerTitle = new Label("Registrazione");
        registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        registerTitle.setTextFill(Color.DARKSLATEGRAY);
        registerTitle.setMaxWidth(Double.MAX_VALUE);
        registerTitle.setTextAlignment(TextAlignment.CENTER);

        HBox titleBox = new HBox(registerTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPrefWidth(Double.MAX_VALUE);

        double textFieldWidth = 250;

        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("Username");
        newUsernameField.setMaxWidth(textFieldWidth - 10);

        TextField newEmailField = new TextField();
        newEmailField.setPromptText("Email");
        newEmailField.setMaxWidth(textFieldWidth - 10);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Password");
        newPasswordField.setPrefWidth(textFieldWidth);

        TextField newPasswordTextField = new TextField();
        newPasswordTextField.setPromptText("Password");
        newPasswordTextField.setVisible(false);
        newPasswordTextField.setPrefWidth(textFieldWidth);

        newPasswordTextField.textProperty().bindBidirectional(newPasswordField.textProperty());

        StackPane passwordFieldsStack = new StackPane();
        passwordFieldsStack.getChildren().addAll(newPasswordField, newPasswordTextField);

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

        ImageView eyeOffImageView = new ImageView(eyeOffImage);
        eyeOffImageView.setFitWidth(20);
        eyeOffImageView.setFitHeight(20);

        togglePasswordVisibilityButton.setGraphic(eyeOffImageView);

        togglePasswordVisibilityButton.setOnAction(event -> {
            if (newPasswordField.isVisible()) {
                newPasswordField.setVisible(false);
                newPasswordTextField.setVisible(true);
                togglePasswordVisibilityButton.setGraphic(eyeImageView);
            } else {
                newPasswordField.setVisible(true);
                newPasswordTextField.setVisible(false);
                togglePasswordVisibilityButton.setGraphic(eyeOffImageView);
            }
        });

        BorderPane passwordContainer = new BorderPane();
        passwordContainer.setPrefWidth(textFieldWidth + 30);
        passwordContainer.setCenter(passwordFieldsStack);
        passwordContainer.setRight(togglePasswordVisibilityButton);
        BorderPane.setAlignment(togglePasswordVisibilityButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(togglePasswordVisibilityButton, new Insets(0, 5, 0, 0));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(0, 0, 0, 35));

        gridPane.add(newUsernameField, 0, 0);
        gridPane.add(newEmailField, 0, 1);
        gridPane.add(passwordContainer, 0, 2);

        Button registerButton = new Button("Registrati");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        registerButton.setOnAction(event -> {
            try {
                ResponseUserCredentialsDTO response = createUserCredentials(
                    newUsernameField, newPasswordField, newEmailField);
                PersonalDataStage personalDataForm = new PersonalDataStage(response.id);
                registerStage.close();
                personalDataForm.show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                showAlert(ex.getMessage(), Alert.AlertType.WARNING);
            }
        });

        VBox.setMargin(gridPane, new Insets(0, 0, 20, 0));

        registerLayout.getChildren().addAll(titleBox, gridPane, registerButton);

        Scene registerScene = new Scene(registerLayout, 450, 350);
        registerStage.setScene(registerScene);
        registerStage.showAndWait();
    }


    private ResponseUserCredentialsDTO createUserCredentials(TextField newUsernameField, TextField newPasswordField,
            TextField newEmailField) throws Exception {

        RegistrationUserCredentialsController rc = new RegistrationUserCredentialsController(
                new RegistrationUserCredentialsCreateUseCase(new RegistrationUserCredentialsDAO(new DBConnector())));

        return rc.create(new RequestCreateUserCredentialsDTO(
                newUsernameField.getText(), newPasswordField.getText(), newEmailField.getText()));
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Attenzione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
