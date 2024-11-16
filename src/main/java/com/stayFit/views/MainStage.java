package com.stayFit.views;

import com.stayFit.dailyNutrition.DailyNutritionResponseGetDTO;
import com.stayFit.registration.ResponseUserDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainStage {

	private ResponseUserDTO userDTO;
	private BorderPane root;
	private ScrollPane scrollPane;
	private DailyNutritionResponseGetDTO response;
	
	public MainStage(ResponseUserDTO userDTO) {
		this.userDTO = userDTO;		
	}

	// @Override
	public void start(Stage primaryStage) {		
		primaryStage.setTitle("Stay Fit");
				
		VBox sidebar = new VBox(20);
		sidebar.setPadding(new Insets(30));
		sidebar.setStyle("-fx-background-color: #b22222;");

		Label appTitle = new Label("Stay Fit");
		appTitle.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
		
		ImageView titleImageView = new ImageView(new Image("/icons/apple.png"));
	    titleImageView.setFitHeight(30);
	    titleImageView.setPreserveRatio(true);
	    
	    HBox titleBox = new HBox(10, titleImageView, appTitle);
	    titleBox.setAlignment(Pos.CENTER_LEFT);

		Label exitButton = createSidebarButtonWithImage("Esci", "/icons/exit.png");
		exitButton.setOnMouseClicked(e -> primaryStage.close());

		Label foodsButton = createSidebarButtonWithImage("La mia dieta", "/icons/food.png");
		foodsButton.setOnMouseClicked(e -> {
			/*StackPane content = new DailyReportStage(userDTO, response.dailyCalories, response.dailyProteins, 
					response.dailyFats, response.dailyCarbs,  userDTO.subscriptionDate, response)
					.getDailyReportView();*/
			
			StackPane content = new DailyReportStage(userDTO, userDTO.subscriptionDate)
					.getDailyReportView();
			
			/*StackPane TEST = new DailyReportStage(userDTO, response.dailyCalories, response.dailyProteins, 
					response.dailyFats, response.dailyCarbs, LocalDate.of(2024, 10, 30), response)
					.getDailyReportView();*/
			scrollPane.setContent(content);
		});

		Label reportButton = createSidebarButtonWithImage("Report", "/icons/report.png");
		reportButton.setOnMouseClicked(e -> {
			StackPane content = new ReportStage().createReportContent();
			scrollPane.setContent(content);
			});

		Label dataButton = createSidebarButtonWithImage("I miei dati", "/icons/myData.png");
		dataButton.setOnMouseClicked(e ->{
			StackPane content = new UserInfoDataStage(userDTO.id).createPersonalDataPane();
			scrollPane.setContent(content);
		});
		

		sidebar.getChildren().addAll(titleBox, foodsButton, reportButton, dataButton, exitButton);

		// Layout principale con area centrale modificabile
		root = new BorderPane();
		root.setLeft(sidebar);

		// ScrollPane nell'area centrale
		scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		root.setCenter(scrollPane);

		showWelcomeContent();

		Scene scene = new Scene(root); // root come nodo principale della scena
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Metodo per creare un pulsante personalizzato nella sidebar con immagine
	private Label createSidebarButtonWithImage(String text, String imagePath) {
		Image image;
		try {
			// Carica l'immagine usando getResource per il percorso della risorsa
			image = new Image(getClass().getResource(imagePath).toExternalForm());
		} catch (NullPointerException e) {
			System.err.println("Immagine non trovata: " + imagePath);
			image = new Image("file:resources/default.png");
		}
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);

		Label button = new Label(text, imageView);
		button.setStyle(
				"-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: transparent;");
		button.setAlignment(Pos.CENTER_LEFT);

		button.setOnMouseEntered(e -> button.setStyle(
				"-fx-background-color: #cc3333; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;"));
		button.setOnMouseExited(e -> button.setStyle(
				"-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;"));

		return button;
	}

	private void showReportContent() {
		
	}

	
	private void showWelcomeContent() {
		VBox content = new VBox(10);
		content.setAlignment(Pos.CENTER);
		content.setPadding(new Insets(20));

		Label title = new Label("Benvenuto su Stay Fit");
		Label message = new Label("Seleziona una categoria dallo sidebar");

		content.getChildren().addAll(title, message);
		scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
	}
	
	private void showAlert(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Errore");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
