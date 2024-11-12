package com.stayFit.views;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;

import com.stayFit.utils.PortionListener;
import com.stayFit.enums.MealType;
import com.stayFit.meal.*;
import com.stayFit.portion.*;
import com.stayFit.registration.ResponseUserDTO;
import com.stayFit.repository.DBConnector;

public class DailyReportStage implements PortionListener {

	private final int MAX_CALORIES;
	private final int MAX_PROTEINS;
	private final int MAX_FATS;
	private final int MAX_CARBS;

	private ProgressBar caloriesProgressBar;
	private Label caloriesLabel;
	private ProgressBar proteinProgressBar;
	private Label proteinLabel;
	private ProgressBar fatProgressBar;
	private Label fatLabel;
	private ProgressBar carbProgressBar;
	private Label carbLabel;
	private Label sugarsLabel;
	private Label saltLabel;
	private Button selectedDayButton;
	private VBox calendarBox;
	private Label monthLabel;
	private YearMonth currentYearMonth;
	private LocalDate startingDate;
	private Button previousButton;
	private Button nextButton;
	private LocalDate selectedDate;

	private MealDAO mealDAO;
	private MealCreateUseCase mcuc;
	private MealController mc;
	private MealCreateRequestDTO meal;
	private MealGetUseCase mguc;

	private PortionDAO portionDAO;
	private PortionCreateUseCase pcuc;
	private PortionGetUseCase pguc;
	private PortionController portionController;
	private PortionCreateRequestDTO pcrDTO;

	private StackPane mainContent;
	private ListView<PortionGetResponseDTO> listViewPortions;
	private ResponseUserDTO userDTO;
	
	public DailyReportStage(final ResponseUserDTO userDTO, final int MAX_CALORIES, final int MAX_PROTEINS, final int MAX_FATS, 
			final int MAX_CARBS, LocalDate startingDate) {
		this.userDTO = userDTO;
		this.MAX_CALORIES = MAX_CALORIES;
		this.MAX_PROTEINS = MAX_PROTEINS;
		this.MAX_FATS = MAX_FATS;
		this.MAX_CARBS = MAX_CARBS;
		this.startingDate = startingDate;
		this.currentYearMonth = YearMonth.now();
		// this.portions = new ArrayList<>();
		listViewPortions = new ListView<>();
		/*---------------------------------------------------------------*/
		this.mealDAO = new MealDAO(new DBConnector());
		this.mcuc = new MealCreateUseCase(mealDAO);
		this.mguc = new MealGetUseCase(mealDAO);
		this.mc = new MealController(mguc, mcuc);
		this.meal = new MealCreateRequestDTO();

		this.portionDAO = new PortionDAO(new DBConnector());
		this.pguc = new PortionGetUseCase(portionDAO);
		this.pcuc = new PortionCreateUseCase(portionDAO);
		this.portionController = new PortionController(pcuc, pguc);
	
		caloriesLabel = new Label();
		proteinLabel = new Label();
		fatLabel = new Label();
		carbLabel = new Label();
		sugarsLabel = new Label();
		saltLabel = new Label();
		caloriesProgressBar = new ProgressBar();		
		proteinProgressBar = new ProgressBar();		
		fatProgressBar = new ProgressBar();		
		carbProgressBar = new ProgressBar();
		
		
	}

	@Override
	public void onPortionsAdded(String mealType, List<PortionGetResponseDTO> portionsResponseDTO) {

		if (portionsResponseDTO.size() > 0) {
			listViewPortions.getItems().clear(); // Pulisci la listview
			meal.mealType = MealType.valueOf(mealType.toUpperCase());
			meal.mealUpdateDate = selectedDate;
			meal.fk_user = userDTO.id; // id temporaneo
			try {
				MealGetRequestDTO mgrDTO = new MealGetRequestDTO(selectedDate, MealType.valueOf(mealType.toUpperCase()),
						meal.fk_user); // fk_user da ottenere
				// Controllo se il pasto esiste già
				int id = mc.getExistingMeal(mgrDTO);
				// Non esiste e lo creo
				if (id == -1) {
					id = mc.getNewMeal(meal);
				}

				// Creo la porzione
				List<PortionCreateRequestDTO> portionsCreateRequestDTO = new ArrayList<>();
				for (PortionGetResponseDTO portion : portionsResponseDTO) {

					PortionCreateRequestDTO pcrDTO = new PortionCreateRequestDTO(portion.product_fk, portion.meal_fk,
							portion.grams, portion.calories, portion.proteins, portion.fats, portion.carbs,
							portion.sugars, portion.salt);
					portionsCreateRequestDTO.add(pcrDTO);
				}
				portionController.create(portionsCreateRequestDTO, id);
			} 
			catch (Exception ex) {
				// stampa errore
				System.out.println(ex.getMessage());
			}
		}

	}

	public StackPane getDailyReportView() {
		mainContent = new StackPane();
		mainContent.setPadding(new Insets(20, 20, 20, 20));
		mainContent.setAlignment(Pos.TOP_CENTER);

		VBox topContentBox = new VBox(20);
		topContentBox.setAlignment(Pos.TOP_LEFT);

		topContentBox.getChildren().addAll(createNavigationBar(), createCalendarView(), createCaloriesOverview());

		VBox mealsBox = new VBox(20);
		mealsBox.setAlignment(Pos.TOP_LEFT);
		mealsBox.getChildren().addAll(createMealItem("COLAZIONE", "50 kcal", "pippo", "/icons/coffee.png", mealsBox),
				createMealItem("PRANZO", "50 kcal", "pippo", "/icons/lunch.png", mealsBox),
				createMealItem("CENA", "50 kcal", "pippo", "/icons/meat.png", mealsBox),
				createMealItem("SPUNTINI", "50 kcal", "pippo", "/icons/snack.png", mealsBox));
		Node tableView = createListView();

		HBox mainBody = new HBox(20);
		mainBody.setAlignment(Pos.TOP_LEFT);
		mainBody.getChildren().addAll(mealsBox, tableView);
		mainBody.widthProperty().addListener((obs, oldVal, newVal) -> {
			double halfWidth = newVal.doubleValue() / 2 - 10; // Subtract spacing
			mealsBox.setPrefWidth(halfWidth);
			if (tableView instanceof Region) {
				((Region) tableView).setPrefWidth(halfWidth);
			}
		});

		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.TOP_LEFT);
		contentBox.getChildren().addAll(topContentBox, mainBody);

		mainContent.getChildren().add(contentBox);
		return mainContent;
	}

	private HBox createListView() {
		HBox listContainer = new HBox();
		listContainer.setPadding(new Insets(10));
		listContainer.setAlignment(Pos.TOP_RIGHT);
		listContainer.setStyle(
				"-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5;");

		listViewPortions
				.setCellFactory(new Callback<ListView<PortionGetResponseDTO>, ListCell<PortionGetResponseDTO>>() {
					@Override
					public ListCell<PortionGetResponseDTO> call(ListView<PortionGetResponseDTO> listView) {
						return new ListCell<PortionGetResponseDTO>() {
							private HBox content;
							private int id;
							private ImageView imageView;
							private VBox detailsBox;
							private Label nameLabel;
							private Label caloriesLabel;
							private Label proteinsLabel;
							private Label fatsLabel;
							private Label carbsLabel;
							private Label sugarsLabel;
							private Label saltLabel;
							private Button deleteButton;
							private Region spacer;
							{
								nameLabel = new Label();
								nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
								caloriesLabel = new Label();
								proteinsLabel = new Label();
								fatsLabel = new Label();
								carbsLabel = new Label();
								sugarsLabel = new Label();
								saltLabel = new Label();
								detailsBox = new VBox(2);
								detailsBox.getChildren().addAll(nameLabel, caloriesLabel, proteinsLabel, fatsLabel,
										carbsLabel, sugarsLabel, saltLabel);

								deleteButton = new Button("-");								
								deleteButton.setOnAction(event -> {
									PortionGetResponseDTO selectedItem = getItem();
									try {
										if (userConfirmation()) {
											PortionController portionControllerDelete = new PortionController(
													new PortionDeleteUseCase(new PortionDAO(new DBConnector())));
											portionControllerDelete.delete(selectedItem.id);
											populateListView(selectedItem.mealType);
										}
									} catch (Exception ex) {
										showAlert(ex.getMessage(), Alert.AlertType.WARNING);
									}
								});

								spacer = new Region();
								HBox.setHgrow(spacer, Priority.ALWAYS);
								content = new HBox(10, detailsBox, spacer, deleteButton);
								content.setAlignment(Pos.CENTER_LEFT);
							}

							@Override
							protected void updateItem(PortionGetResponseDTO item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setGraphic(null);
								}
								else {
									nameLabel.setText("Nome: " + item.product_name);
									caloriesLabel.setText(String.format("Energia (kcal): %.1f kcal", item.calories));
									proteinsLabel.setText(String.format("Proteine: %.1f g", item.proteins));
									fatsLabel.setText(String.format("Grassi: %.1f g", item.fats));
									carbsLabel.setText(String.format("Carboidrati: %.1f g", item.carbs));
									sugarsLabel.setText(String.format("Zuccheri: %.1f g", item.sugars));
									saltLabel.setText(String.format("Sale: %.1f g", item.salt));
									setGraphic(content);
								}
							}
						};
					}
				});

		listContainer.getChildren().add(listViewPortions);
		listViewPortions.setPrefWidth(400);
		HBox.setHgrow(listViewPortions, Priority.ALWAYS);
		updateDailyReport();
		return listContainer;
	}

	private HBox createNavigationBar() {
		previousButton = new Button("<");
		previousButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
		previousButton.setOnAction(e -> navigatePrevious());

		nextButton = new Button(">");
		nextButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
		nextButton.setOnAction(e -> navigateNext());

		monthLabel = new Label();
		monthLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		monthLabel.setAlignment(Pos.CENTER);

		HBox navigationBar = new HBox(20, previousButton, monthLabel, nextButton);
		navigationBar.setAlignment(Pos.CENTER);
		navigationBar.setPadding(new Insets(10, 0, 10, 0));

		updateMonthLabel(); // Set the label for the current month

		return navigationBar;
	}

	private void updateMonthLabel() {
		monthLabel.setText(currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());
	}

	private VBox createCalendarView() {
		calendarBox = createCalendarOverview();
		return calendarBox;
	}

	private void navigatePrevious() {
		YearMonth minYearMonth = YearMonth.from(startingDate);
		if (currentYearMonth.isAfter(minYearMonth)) {
			currentYearMonth = currentYearMonth.minusMonths(1);
			updateMonthLabel();
			updateCalendarView();
		}
	}

	private void navigateNext() {
		YearMonth maxYearMonth = YearMonth.from(LocalDate.now());
		if (currentYearMonth.isBefore(maxYearMonth)) {
			currentYearMonth = currentYearMonth.plusMonths(1);
			updateMonthLabel();
			updateCalendarView();
		}
	}

	private void updateCalendarView() {
		VBox newCalendar = createCalendarOverview();
		calendarBox.getChildren().clear();
		calendarBox.getChildren().addAll(newCalendar.getChildren());
	}

	private VBox createCalendarOverview() {
		// Get today's date
		LocalDate today = LocalDate.now();

		// Day of the week headers
		String[] dayNames = { "Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom" };
		GridPane calendarGrid = new GridPane();
		calendarGrid.setHgap(10);
		calendarGrid.setVgap(10);
		calendarGrid.setPadding(new Insets(10));
		calendarGrid.setAlignment(Pos.CENTER);

		// Add day headers
		for (int i = 0; i < dayNames.length; i++) {
			Label dayName = new Label(dayNames[i]);
			dayName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
			dayName.setAlignment(Pos.CENTER);
			calendarGrid.add(dayName, i, 0);
		}

		LocalDate firstOfMonth = currentYearMonth.atDay(1);
		int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue();
		int daysInMonth = currentYearMonth.lengthOfMonth();

		int row = 1;
		int col = firstDayOfWeek - 1;

		for (int day = 1; day <= daysInMonth; day++) {
			LocalDate currentDate = currentYearMonth.atDay(day);
			Button dayButton = new Button(String.valueOf(day));
			dayButton.setPrefSize(40, 40);

			boolean isEnabled = isDayEnabled(currentDate, today);

			if (isEnabled) {
				dayButton.setDisable(false);
				dayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

				if (currentDate.equals(today) && currentYearMonth.equals(YearMonth.from(today))) {
					dayButton.setStyle("-fx-background-color: #76ff03; -fx-text-fill: white; -fx-font-weight: bold;");
					selectedDayButton = dayButton;
					selectedDate = currentDate; // Impostare la data selezionata iniziale su oggi
					updateDailyReport();
				}

				dayButton.setOnAction(e -> {
					if (selectedDayButton != null) {
						listViewPortions.getItems().clear();
						selectedDayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
					}

					dayButton.setStyle("-fx-background-color: #76ff03; -fx-text-fill: white; -fx-font-weight: bold;");
					selectedDayButton = dayButton;
					selectedDate = currentDate; // Aggiorna la data selezionata
					updateDailyReport();
				});

			} else {
				dayButton.setDisable(true);
				dayButton.setStyle("-fx-background-color: #dddddd; -fx-text-fill: #888888;");
			}

			calendarGrid.add(dayButton, col, row);

			col++;
			if (col > 6) {
				col = 0;
				row++;
			}
		}

		VBox calendarContainer = new VBox(10, calendarGrid);
		calendarContainer.setAlignment(Pos.CENTER);
		calendarContainer.setPadding(new Insets(10));
		calendarContainer.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
				+ "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");

		return calendarContainer;
	}

	private boolean isDayEnabled(LocalDate date, LocalDate today) {
		// Disabilitare i giorni prima dell'inizio della dieta
		if (date.isBefore(startingDate)) {
			return false;
		}

		// Disabilitare i giorni successivi all'inizio della dieta
		if (date.isAfter(today)) {
			return false;
		}

		return true;
	}

	private VBox createCaloriesOverview() {
		Label title = new Label("Panoramica Nutrizionale");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		// Calorie progress bar
		caloriesProgressBar = new ProgressBar(0);
		caloriesProgressBar.setPrefWidth(300);
		caloriesProgressBar.setMinHeight(20);
		caloriesProgressBar.setStyle("-fx-accent: #76ff03;"); // Initial green color
		caloriesLabel.setStyle("-fx-font-size: 14px;");

		// Proteine progress bar
		proteinProgressBar = new ProgressBar(0);
		proteinProgressBar.setPrefWidth(100);
		proteinProgressBar.setMinHeight(10);
		proteinProgressBar.setStyle("-fx-accent: #76ff03;");
		proteinLabel.setStyle("-fx-font-size: 12px;");

		// Grassi progress bar
		fatProgressBar = new ProgressBar(0);
		fatProgressBar.setPrefWidth(100);
		fatProgressBar.setMinHeight(10);
		fatProgressBar.setStyle("-fx-accent: #76ff03;");
		fatLabel.setStyle("-fx-font-size: 12px;");

		// Carboidrati progress bar
		carbProgressBar = new ProgressBar(0);
		carbProgressBar.setPrefWidth(100);
		carbProgressBar.setMinHeight(10);
		carbProgressBar.setStyle("-fx-accent: #76ff03;");
		carbLabel.setStyle("-fx-font-size: 12px;");
		
		VBox calorieBox = new VBox(10);
		calorieBox.setAlignment(Pos.CENTER);
		calorieBox.getChildren().addAll(title, caloriesProgressBar, caloriesLabel);

		HBox macrosBox = new HBox(20);
		macrosBox.setAlignment(Pos.CENTER);
		macrosBox.getChildren().addAll(createMacroProgressBox("Proteine", proteinProgressBar, proteinLabel),
				createMacroProgressBox("Grassi", fatProgressBar, fatLabel),
				createMacroProgressBox("Carboidrati", carbProgressBar, carbLabel));

		calorieBox.getChildren().add(macrosBox);

		Button endDayButton = new Button("Termina Giornata");
	    endDayButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");
	    
	    endDayButton.setOnAction(e -> {	        
	        //terminateDay();
	    });
	    
	    calorieBox.getChildren().add(endDayButton);
		calorieBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
				+ "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");

		return calorieBox;
	}

	private void updateNutritionalOverview(List<MealGetResponseDTO> meals) {
		double totalCalories = 0;
		double totalProteins = 0;
		double totalFats = 0;
		double totalCarbs = 0;
				

		for (MealGetResponseDTO meal : meals) {
			System.out.println(meal.calories);
			totalCalories += meal.calories;
			totalProteins += meal.proteins;
			totalFats += meal.fats;
			totalCarbs += meal.carbs;
		}

		// Aggiorna le etichette
		caloriesLabel.setText(String.format("Calorie Assunte: %.1f / %d %s", totalCalories, MAX_CALORIES, "cal"));
		proteinLabel.setText(String.format("Proteine: %.1f g / %d g", totalProteins, MAX_PROTEINS));
		fatLabel.setText(String.format("Grassi: %.1f g / %d g", totalFats, MAX_FATS));
		carbLabel.setText(String.format("Carboidrati: %.1f g / %d g", totalCarbs, MAX_CARBS));

		// Aggiorna le barre di progresso		
		caloriesProgressBar.setProgress(totalCalories / MAX_CALORIES);
		proteinProgressBar.setProgress(totalProteins / MAX_PROTEINS < 0.1 ? 0.1:totalProteins / MAX_PROTEINS);
		fatProgressBar.setProgress(totalFats / MAX_FATS < 0.1 ? 0.1:totalFats / MAX_FATS);
		carbProgressBar.setProgress(totalCarbs / MAX_CARBS < 0.1 ? 0.1:totalCarbs / MAX_CARBS);

		// Cambia colore delle barre se si supera il massimo
		caloriesProgressBar.setStyle(totalCalories > MAX_CALORIES ? "-fx-accent: red;" : "-fx-accent: #76ff03;");
		proteinProgressBar.setStyle(totalProteins > MAX_PROTEINS ? "-fx-accent: red;" : "-fx-accent: #76ff03;");
		fatProgressBar.setStyle(totalFats > MAX_FATS ? "-fx-accent: red;" : "-fx-accent: #76ff03;");
		carbProgressBar.setStyle(totalCarbs > MAX_CARBS ? "-fx-accent: red;" : "-fx-accent: #76ff03;");
	}

	private VBox createMacroProgressBox(String name, ProgressBar progressBar, Label label) {
		Label macroNameLabel = new Label(name);
		macroNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

		VBox macroBox = new VBox(5);
		macroBox.setAlignment(Pos.CENTER);
		macroBox.getChildren().addAll(macroNameLabel, progressBar, label);

		return macroBox;
	}

	private HBox createMealItem(String mealName, String calories, String details, String path, VBox parentContainer) {
		HBox mealItem = new HBox(10);

		mealItem.setPadding(new Insets(10));
		mealItem.setAlignment(Pos.BASELINE_LEFT);
		mealItem.setStyle(
				"-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5;");

		Image icon;
		try {
			icon = new Image(getClass().getResource(path).toExternalForm());
		}

		catch (NullPointerException e) {
			System.err.println("Icon not found: " + path);
			icon = new Image("file:resources/default.png"); // Default image
		}

		ImageView iconView = new ImageView(icon);
		iconView.setFitWidth(40);
		iconView.setFitHeight(40);

		VBox textContainer = new VBox(5);
		Label mealLabel = new Label(mealName);
		mealLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		Label caloriesLabel = new Label(calories);
		caloriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
		Label detailsLabel = new Label(details);
		detailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");
		textContainer.getChildren().addAll(mealLabel, caloriesLabel, detailsLabel);

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Button addButton = new Button("+");
		addButton.setStyle(
				"-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #007bff; -fx-background-radius: 20;");
		addButton.setMinSize(30, 30);

		addButton.setOnMouseClicked(event -> {
			AddFoodForMealStage addFoodForMeal = new AddFoodForMealStage(mealName, this, this::updateDailyReport);
			Node foodForm = addFoodForMeal.searchFoodForm();
			VBox overlayPane = new VBox();
			overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
			overlayPane.setAlignment(Pos.CENTER);
			overlayPane.setPrefSize(mainContent.getWidth(), mainContent.getHeight());
			overlayPane.getChildren().add(foodForm);
			mainContent.getChildren().add(overlayPane);

			addFoodForMeal.setOverlayPane(overlayPane, mainContent);
		});

		Button viewMeals = new Button(">");
		viewMeals.setStyle(
				"-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #007bff; -fx-background-radius: 20;");
		viewMeals.setMinSize(30, 30);
		viewMeals.setOnMouseClicked(event -> {
			populateListView(mealName);
		});

		// Add elements to the HBox
		mealItem.getChildren().addAll(iconView, textContainer, spacer, addButton, viewMeals);
		return mealItem;
	}

	// AGGIORNA I VALORI NUTRIZIONALI ASSUNTI DURANTE LA GIORNATA
	private void updateDailyReport() {
		try {			
			MealGetRequestDTO mgrDTO = new MealGetRequestDTO(selectedDate, 1);
			List<MealGetResponseDTO> meals = mc.getDailyNutritions(mgrDTO);
			updateNutritionalOverview(meals);
		}
		catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
		}
	}

	private void populateListView(String mealName) {
		if (selectedDate == null) {
			showAlert("Seleziona la data", Alert.AlertType.WARNING);
			return;
		}

		PortionGetRequestDTO pgrDTO = new PortionGetRequestDTO(MealType.valueOf(mealName.toUpperCase()), selectedDate, 
				userDTO.id);
		try {
			List<PortionGetResponseDTO> portionsDTO = portionController.getPortionsDTO(pgrDTO);
			// Esegui l'aggiornamento dell'interfaccia grafica sul thread JavaFX
			Platform.runLater(() -> {
				listViewPortions.getItems().clear();
				listViewPortions.getItems().addAll(portionsDTO);
			});
		} catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
			ex.printStackTrace();
		}
	}

	private void showAlert(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Errore");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public boolean userConfirmation() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Conferma");
		alert.setHeaderText("Azione Richiesta");
		alert.setContentText("Sei sicuro di voler procedere?");

		// Crea i pulsanti personalizzati
		ButtonType buttonYes = new ButtonType("Sì", ButtonBar.ButtonData.YES);
		ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);

		// Rimuovi i pulsanti predefiniti e aggiungi quelli personalizzati
		alert.getButtonTypes().setAll(buttonYes, buttonNo);

		// Mostra l'Alert e aspetta la risposta dell'utente
		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()) {
			if (result.get() == buttonYes) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
