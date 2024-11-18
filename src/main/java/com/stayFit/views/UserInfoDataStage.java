package com.stayFit.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

import com.stayFit.dailyNutrition.DailyNutritionController;
import com.stayFit.dailyNutrition.DailyNutritionUpdateUseCase;
import com.stayFit.dailyNutrition.DailyNutritionGetUseCase;
import com.stayFit.mealNutrition.MealNutritionUpdateUseCase;
import com.stayFit.dailyNutrition.DailyNutritionDAO;
import com.stayFit.dailyNutrition.DailyNutritionResponseGetDTO;
import com.stayFit.dailyNutrition.DailyNutritionResponseUpdateDTO;
import com.stayFit.dailyNutrition.UserInfoDTO;
import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.mealNutrition.MealNutritionController;
import com.stayFit.mealNutrition.MealNutritionDAO;
import com.stayFit.repository.DBConnector;
import com.stayFit.user.RequestUpdateUserDTO;
import com.stayFit.user.ResponseGetUserDTO;
import com.stayFit.user.UserController;
import com.stayFit.user.UserDAO;
import com.stayFit.user.UserGetUseCase;
import com.stayFit.weightReport.WeightReportController;
import com.stayFit.weightReport.WeightReportCreateRequestDTO;
import com.stayFit.weightReport.WeightReportCreateUseCase;
import com.stayFit.weightReport.WeightReportDAO;
import com.stayFit.user.UserUpdateUseCase;

public class UserInfoDataStage {
	private int idUser;

	private TextField nameField;
	private TextField surnameField;
	private DatePicker birthDatePicker;
	private Spinner<Integer> heightSpinner;
	private Spinner<Double> weightSpinner;
	private ComboBox<String> fitnessStatusComboBox;
	private ComboBox<String> genderComboBox;
	private ComboBox<String> goalComboBox;

	public UserInfoDataStage(int idUser) {
		this.idUser = idUser;

		this.nameField = new TextField();
		this.surnameField = new TextField();
		this.birthDatePicker = new DatePicker();
		this.heightSpinner = new Spinner<Integer>();
		this.weightSpinner = new Spinner<Double>();
		this.fitnessStatusComboBox = new ComboBox<String>();
		this.genderComboBox = new ComboBox<String>();
		this.goalComboBox = new ComboBox<String>();
		loadUserData();
	}

	public StackPane createPersonalDataPane() {
		// Creazione del layout principale VBox
		VBox dataLayout = new VBox(15);
		dataLayout.setPadding(new Insets(20));
		dataLayout.setAlignment(Pos.CENTER);

		// Titolo
		Label dataTitle = new Label("I tuoi dati:");
		dataTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		dataTitle.setTextFill(Color.DARKSLATEGRAY);

		nameField.setMaxWidth(200);
		nameField.setPromptText("Nome");
		surnameField.setMaxWidth(200);
		surnameField.setPromptText("Cognome");

		birthDatePicker.setMaxWidth(200);
		birthDatePicker.setPromptText("Data di Nascita");

		heightSpinner.setMaxWidth(200);
		heightSpinner.setEditable(false);

		weightSpinner.setMaxWidth(200);
		weightSpinner.setEditable(false);

		fitnessStatusComboBox.setMaxWidth(200);
		fitnessStatusComboBox.getItems().addAll("Sedentario", "Poco attivo", "Mediamente attivo", "Molto attivo");

		genderComboBox.setMaxWidth(200);
		genderComboBox.getItems().addAll("Maschio", "Femmina");

		goalComboBox.setMaxWidth(200);
		goalComboBox.getItems().addAll("Perdere Peso", "Mantenere Peso", "Mettere Massa Muscolare");

		// Pulsante Salva
		Button saveButton = new Button("Salva");
		saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
		saveButton.setOnAction(event -> {
			try {
				RequestUpdateUserDTO request = updateUserInfo(); // Aggiorna i dati dell'utente
				updateNutrition(request); // Aggiorna la dieta
			} 
			catch (Exception ex) {
				showAlert(ex.getMessage(), Alert.AlertType.ERROR);
			}

		});

		// Aggiunta di tutti i nodi al VBox
		dataLayout.getChildren().addAll(dataTitle, nameField, surnameField, birthDatePicker, heightSpinner,
				weightSpinner, fitnessStatusComboBox, genderComboBox, goalComboBox, saveButton);

		// Creazione dello StackPane e aggiunta del VBox
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(dataLayout);

		return stackPane;
	}

	private void loadUserData() {
		UserController userGetController = new UserController(new UserGetUseCase(new UserDAO(new DBConnector())));
		try {
			ResponseGetUserDTO response = userGetController.get(idUser);

			nameField.setText(response.name);
			surnameField.setText(response.surname);
			heightSpinner
					.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 250, response.height));
			weightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10, 300, response.weight));
			fitnessStatusComboBox.setValue(FitnessState.getStringForStageView(response.fitnessState));
			goalComboBox.setValue(Goal.getStringForStageView(response.goal));
			genderComboBox.setValue(Gender.getStringForStageView(response.gender));
			birthDatePicker.setValue(response.birthday);
		} 
		catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.ERROR);
		}
	}

	private RequestUpdateUserDTO updateUserInfo() throws Exception {
		UserController userUpdateController = new UserController(new UserUpdateUseCase(new UserDAO(new DBConnector())));

		RequestUpdateUserDTO request = new RequestUpdateUserDTO(idUser, nameField.getText(), surnameField.getText(),
				birthDatePicker.getValue(),
				FitnessState.valueOf(fitnessStatusComboBox.getValue().toUpperCase().replace(" ", "_")),
				Gender.valueOf(genderComboBox.getValue().toUpperCase()),
				Goal.valueOf(goalComboBox.getValue().toUpperCase().replace(" ", "_")), heightSpinner.getValue(),
				weightSpinner.getValue());

		try {
			userUpdateController.update(request);
		} 
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return request;
	}

	private void updateNutrition(RequestUpdateUserDTO request) {
		try {
			UserInfoDTO userInfo = new UserInfoDTO(request.id, request.gender, request.goal, request.fitnessState,
					request.birthday, request.height, request.weight, request.userCredentials_fk);

			DailyNutritionController dailyNutritionControllerForGet = new DailyNutritionController(
					new DailyNutritionGetUseCase(new DailyNutritionDAO(new DBConnector())));

			// Ottengo la dieta dell'utente
			DailyNutritionResponseGetDTO dailyNutritionResponse = dailyNutritionControllerForGet.get(idUser);

			DailyNutritionController dailyNutritionController = new DailyNutritionController(
					new DailyNutritionUpdateUseCase(new DailyNutritionDAO(new DBConnector())));

			// Aggiorno la dieta quotidiana
			DailyNutritionResponseUpdateDTO dailyNutrition = dailyNutritionController.update(userInfo);
			dailyNutrition.id = dailyNutritionResponse.id;

			// Aggiorno i macronutrienti per ogni giorno
			MealNutritionController mealController = new MealNutritionController(
					new MealNutritionUpdateUseCase(new MealNutritionDAO(new DBConnector())));

			mealController.update(dailyNutrition);

			// Aggiorno il peso
			WeightReportCreateRequestDTO requestRegisterWeight = new WeightReportCreateRequestDTO(userInfo.weight,
					LocalDate.now(), userInfo.id);
			WeightReportController weightReport = new WeightReportController(
					new WeightReportCreateUseCase(new WeightReportDAO(new DBConnector())));
			weightReport.create(requestRegisterWeight);

			showAlert("Informazioni aggiornate con successo", Alert.AlertType.INFORMATION);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			showAlert("Qualcosa è andato storto", Alert.AlertType.ERROR);
		}
	}

	// Metodo per mostrare un avviso
	private void showAlert(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Informazione");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
