package com.stayFit.views;

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
import java.time.LocalDate;

import com.stayFit.dailyNutrition.DailyNutritionCreateUseCase;
import com.stayFit.dailyNutrition.DailyNutritionResponseCreateDTO;
import com.stayFit.dailyNutrition.DailyNutritionController;
import com.stayFit.dailyNutrition.DailyNutritionDAO;
import com.stayFit.dailyNutrition.UserInfoDTO;
import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.enums.IsNewOrUpdate;
//import com.stayFit.mealNutrition.DailyNutritionDTO;
import com.stayFit.mealNutrition.MealNutritionController;
import com.stayFit.mealNutrition.MealNutritionCreateUseCase;
import com.stayFit.mealNutrition.MealNutritionDAO;
import com.stayFit.models.DailyNutrition;
import com.stayFit.registration.RegistrationUserController;
import com.stayFit.registration.RegistrationUserCreateUseCase;
import com.stayFit.registration.RegistrationUserDAO;
import com.stayFit.registration.RequestCreateUserDTO;
import com.stayFit.registration.ResponseUserDTO;
import com.stayFit.repository.DBConnector;
import com.stayFit.weightReport.WeightReportController;
import com.stayFit.weightReport.WeightReportCreateRequestDTO;
import com.stayFit.weightReport.WeightReportCreateUseCase;
import com.stayFit.weightReport.WeightReportDAO;

public class PersonalDataStage {
	private int idUserCredentials;

	public PersonalDataStage(int idUserCredentials) {
		this.idUserCredentials = idUserCredentials;
	}

	private TextField nameField;
	private TextField surnameField;
	private DatePicker birthDatePicker;
	private Spinner<Integer> heightSpinner;
	private Spinner<Double> weightSpinner;
	private ComboBox<String> fitnessStatusComboBox;
	private ComboBox<String> genderComboBox;
	private ComboBox<String> goalComboBox;

	public void show() {
		Stage personalDataStage = new Stage();
		personalDataStage.initModality(Modality.APPLICATION_MODAL);
		personalDataStage.setTitle("Dati Anagrafici");

		VBox dataLayout = new VBox(15);
		dataLayout.setPadding(new Insets(20));
		dataLayout.setAlignment(Pos.CENTER);

		Label dataTitle = new Label("Inserisci i Dati Anagrafici");
		dataTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		dataTitle.setTextFill(Color.DARKSLATEGRAY);

		nameField = new TextField("");
		nameField.setPromptText("Nome");
		nameField.setMaxWidth(200);
		
		surnameField = new TextField("");
		surnameField.setPromptText("Cognome");
		surnameField.setMaxWidth(200);
		
		birthDatePicker = new DatePicker();
		birthDatePicker.setPromptText("Data di Nascita");
		birthDatePicker.setMaxWidth(200);

		heightSpinner = new Spinner<>();
		heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 250, 170));
		heightSpinner.setEditable(false);
		heightSpinner.setMaxWidth(200);

		weightSpinner = new Spinner<>();
		weightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(30.0, 300.0, 70.0, 1));
		weightSpinner.setEditable(false);
		weightSpinner.setMaxWidth(200);

		fitnessStatusComboBox = new ComboBox<>();
		fitnessStatusComboBox.getItems().addAll("SEDENTARIO", "POCO_ATTIVO", "MEDIAMENTE_ATTIVO", "MOLTO_ATTIVO");
		fitnessStatusComboBox.setPromptText("Stato Fitness");
		fitnessStatusComboBox.setValue("SEDENTARIO");
		fitnessStatusComboBox.setMaxWidth(200);

		genderComboBox = new ComboBox<>();
		genderComboBox.getItems().addAll("Maschio", "Femmina");
		genderComboBox.setValue("Maschio");
		genderComboBox.setMaxWidth(200);

		goalComboBox = new ComboBox<>();
		goalComboBox.getItems().addAll("Perdere Peso", "Mantenere Peso", "Mettere Massa Muscolare");
		goalComboBox.setValue("Mantenere Peso");
		goalComboBox.setMaxWidth(200);
		
		Button saveButton = new Button("Salva");
		saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
		saveButton.setOnAction(event -> {
			try {
				create(personalDataStage);
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
				showAlert(ex.getMessage(), Alert.AlertType.WARNING);
			}
		});

		dataLayout.getChildren().addAll(dataTitle, nameField, surnameField, birthDatePicker, heightSpinner,
				weightSpinner, fitnessStatusComboBox, genderComboBox, goalComboBox, saveButton);

		Scene dataScene = new Scene(dataLayout, 350, 550);
		personalDataStage.setScene(dataScene);
		personalDataStage.show();
	}

	private void create(Stage personalDataStage) throws Exception {
		RegistrationUserController registrationUserController = new RegistrationUserController(
				new RegistrationUserCreateUseCase(new RegistrationUserDAO(new DBConnector())));

		try {
			// Creo la richiesta per la creazione dello user con i suoi dati anagrafici
			RequestCreateUserDTO userDTO = new RequestCreateUserDTO(nameField.getText(), surnameField.getText(),
					heightSpinner.getValue(), weightSpinner.getValue(), birthDatePicker.getValue(),
					FitnessState.valueOf(fitnessStatusComboBox.getValue()),
					Gender.valueOf(genderComboBox.getValue().toUpperCase()),
					Goal.valueOf(goalComboBox.getValue().toUpperCase().replace(" ", "_")), LocalDate.now(), idUserCredentials);
						
			// Calcolo del suo bmi
			userDTO.BMI = registrationUserController.getBMI(userDTO.height, userDTO.weight);
			// Registro le info del cliente
			ResponseUserDTO response = registrationUserController.insert(userDTO);					
			
			// UserInfo per il calcolo della dieta
			UserInfoDTO userInfo = new UserInfoDTO(response.id, userDTO.gender, userDTO.goal, userDTO.fitnessState, userDTO.birthday,
					userDTO.height, userDTO.weight, userDTO.userCredentials_fk);
			DailyNutritionController dailyNutritionController = new DailyNutritionController(
					new DailyNutritionCreateUseCase(new DailyNutritionDAO(new DBConnector())));

			DailyNutritionResponseCreateDTO dailyNutrition = dailyNutritionController.create(userInfo);
			MealNutritionController mealController = new MealNutritionController(
					new MealNutritionCreateUseCase(new MealNutritionDAO(new DBConnector())));
			mealController.create(dailyNutrition);
			
			// Registro del peso (double weight, LocalDate weightDateRegistration, int user_fk)
			WeightReportCreateRequestDTO requestRegisterWeight = new WeightReportCreateRequestDTO(response.weight, LocalDate.now(),
					response.id);
			WeightReportController weightReport = new WeightReportController(new WeightReportCreateUseCase(
					new WeightReportDAO(new DBConnector())));
			weightReport.create(requestRegisterWeight);

			MainStage mainForm = new MainStage(response);
			personalDataStage.close();
			Stage mainStage = new Stage();
			mainForm.start(mainStage);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
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
