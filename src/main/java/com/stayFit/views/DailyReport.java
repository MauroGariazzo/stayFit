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
import com.stayFit.repository.DBConnector;

public class DailyReport implements PortionListener {

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

	private StackPane mainContent; // Changed from VBox to StackPane
	// private List<PortionCreateRequestDTO>portions;
	private ListView<PortionGetResponseDTO> listViewPortions;

	public DailyReport(final int MAX_CALORIES, final int MAX_PROTEINS, final int MAX_FATS, final int MAX_CARBS,
			LocalDate startingDate) {
		this.MAX_CALORIES = MAX_CALORIES;
		this.MAX_PROTEINS = MAX_PROTEINS;
		this.MAX_FATS = MAX_FATS;
		this.MAX_CARBS = MAX_CARBS;
		this.startingDate = startingDate;
		this.currentYearMonth = YearMonth.now(); // Set the current month on startup
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
		// this.pcrDTO = new PortionCreateRequestDTO();
	}

	@Override
	public void onPortionsAdded(String mealType, List<PortionGetResponseDTO> portionsResponseDTO) {

		if (portionsResponseDTO.size() > 0) {
			listViewPortions.getItems().clear(); // Pulisci la listview
			meal.mealType = MealType.valueOf(mealType.toUpperCase());
			meal.mealUpdateDate = selectedDate;
			meal.fk_user = 1; // id temporaneo
			try {
				MealGetRequestDTO mgrDTO = new MealGetRequestDTO(selectedDate, MealType.valueOf(mealType.toUpperCase()),
						1); // fk_user da ottenere
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
			} catch (Exception ex) {
				// stampa errore
				System.out.println(ex.getMessage());
			}
		}

		// Esempio: aggiornare la tabella delle porzioni o le barre di progresso
		// updatePortionsTable(mealType, portions);
		// updateNutritionalOverview(portions);
	}

	public StackPane getDailyReportView() {
		mainContent = new StackPane();
		mainContent.setPadding(new Insets(20, 20, 20, 20));
		mainContent.setAlignment(Pos.TOP_CENTER);

		VBox topContentBox = new VBox(20);
		topContentBox.setAlignment(Pos.TOP_LEFT);

		// Add the top sections
		topContentBox.getChildren().addAll(createNavigationBar(), createCalendarView(), createCalorieOverview());

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

		// Imposta il CellFactory per personalizzare la visualizzazione di ogni elemento
		listViewPortions.setCellFactory(new Callback<ListView<PortionGetResponseDTO>, ListCell<PortionGetResponseDTO>>() {
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
	                    detailsBox.getChildren().addAll(nameLabel, caloriesLabel,
	                            proteinsLabel, fatsLabel, carbsLabel, sugarsLabel, saltLabel);
	                    
	                    deleteButton = new Button("-");
	                    
	                    deleteButton.setOnAction(event -> {
	                    	PortionGetResponseDTO selectedItem = getItem();
	                    	try {
	                    		if(areUSure()) {
	                    			PortionController portionControllerDelete = new PortionController(new PortionDeleteUseCase(new PortionDAO(new DBConnector())));
	                    			portionControllerDelete.delete(selectedItem.id);	                    			
	                    			populateListView(selectedItem.mealType);
	                    		}
	                    	}
	                    	catch(Exception ex) {
	                    		showAlert(ex.getMessage(), Alert.AlertType.WARNING);
	                    	}
	                    });
	                    
	                    spacer = new Region();
	                    HBox.setHgrow(spacer, Priority.ALWAYS);

	                    //content = new HBox(10, imageView, detailsBox, spacer, addButton);
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
							System.out.println(item.id);
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

		// List<Product> products = mc.
		// listView.getItems().addAll(listaDiProdotti);

		listContainer.getChildren().add(listViewPortions);
		listViewPortions.setPrefWidth(400);
		HBox.setHgrow(listViewPortions, Priority.ALWAYS);

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

	/**
	 * Updates the label of the current month.
	 */
	private void updateMonthLabel() {
		monthLabel.setText(currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());
	}

	/**
	 * Creates the calendar view.
	 *
	 * @return A VBox containing the calendar.
	 */
	private VBox createCalendarView() {
		calendarBox = createCalendarOverview();
		return calendarBox;
	}

	/**
	 * Navigates to the previous month and updates the calendar.
	 */
	private void navigatePrevious() {
		YearMonth minYearMonth = YearMonth.from(startingDate);
		if (currentYearMonth.isAfter(minYearMonth)) {
			currentYearMonth = currentYearMonth.minusMonths(1);
			updateMonthLabel();
			updateCalendarView();			
		}
	}

	/**
	 * Navigates to the next month and updates the calendar.
	 */
	private void navigateNext() {
		YearMonth maxYearMonth = YearMonth.from(LocalDate.now());		
		if (currentYearMonth.isBefore(maxYearMonth)) {
			currentYearMonth = currentYearMonth.plusMonths(1);
			updateMonthLabel();
			updateCalendarView();			
		}
	}

	/**
	 * Updates the calendar view.
	 */
	private void updateCalendarView() {
		VBox newCalendar = createCalendarOverview();
		calendarBox.getChildren().clear();
		calendarBox.getChildren().addAll(newCalendar.getChildren());
	}

	/**
	 * Creates a monthly calendar view where only days up to a specific date are
	 * enabled. Days before startingDate and after today are disabled.
	 *
	 * @return A VBox containing the calendar.
	 */
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

		// Get the first day of the current month
		LocalDate firstOfMonth = currentYearMonth.atDay(1);
		// Calculate the day of the week of the first day of the month (Mon = 1, Sun =
		// 7)
		int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue();

		// Number of days in the current month
		int daysInMonth = currentYearMonth.lengthOfMonth();

		// Populate the calendar with days
		int row = 1;
		int col = firstDayOfWeek - 1; // Java DayOfWeek starts from 1 (Monday)
		// Dentro il metodo createCalendarOverview
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
					selectedDate = currentDate; // Imposta la data selezionata iniziale su oggi
				}

				dayButton.setOnAction(e -> {
					if (selectedDayButton != null) {
						listViewPortions.getItems().clear();
						selectedDayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
					}

					dayButton.setStyle("-fx-background-color: #76ff03; -fx-text-fill: white; -fx-font-weight: bold;");
					selectedDayButton = dayButton;

					selectedDate = currentDate; // Aggiorna la data selezionata
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
		// Disable days before startingDate
		if (date.isBefore(startingDate)) {
			return false;
		}

		// Disable days after today
		if (date.isAfter(today)) {
			return false;
		}

		return true;
	}

	private VBox createCalorieOverview() {
		// Section title
		Label title = new Label("Panoramica Nutrizionale");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		// Calories progress bar
		caloriesProgressBar = new ProgressBar(0);
		caloriesProgressBar.setPrefWidth(300);
		caloriesProgressBar.setStyle("-fx-accent: #76ff03;"); // Initial green color

		// Calories label
		caloriesLabel = new Label("Calorie Assunte: 0 / " + MAX_CALORIES);
		caloriesLabel.setStyle("-fx-font-size: 14px;");

		// Protein progress bar
		proteinProgressBar = new ProgressBar(0);
		proteinProgressBar.setPrefWidth(100);
		proteinProgressBar.setStyle("-fx-accent: #2196F3;"); // Blue

		// Protein label
		proteinLabel = new Label("Proteine: 0g / " + MAX_PROTEINS + "g");
		proteinLabel.setStyle("-fx-font-size: 12px;");

		// Fat progress bar
		fatProgressBar = new ProgressBar(0);
		fatProgressBar.setPrefWidth(100);
		fatProgressBar.setStyle("-fx-accent: #FFC107;"); // Yellow

		// Fat label
		fatLabel = new Label("Grassi: 0g / " + MAX_FATS + "g");
		fatLabel.setStyle("-fx-font-size: 12px;");

		// Carb progress bar
		carbProgressBar = new ProgressBar(0);
		carbProgressBar.setPrefWidth(100);
		carbProgressBar.setStyle("-fx-accent: #FF5722;"); // Orange

		// Carb label
		carbLabel = new Label("Carboidrati: 0g / " + MAX_CARBS + "g");
		carbLabel.setStyle("-fx-font-size: 12px;");

		// Layout for calorie bars
		VBox calorieBox = new VBox(10);
		calorieBox.setAlignment(Pos.CENTER);
		calorieBox.getChildren().addAll(title, caloriesProgressBar, caloriesLabel);

		// Layout for protein, fat, and carb bars
		HBox macrosBox = new HBox(20);
		macrosBox.setAlignment(Pos.CENTER);
		macrosBox.getChildren().addAll(createMacroProgressBox("Proteine", proteinProgressBar, proteinLabel),
				createMacroProgressBox("Grassi", fatProgressBar, fatLabel),
				createMacroProgressBox("Carboidrati", carbProgressBar, carbLabel));

		calorieBox.getChildren().add(macrosBox);

		// Style for calorieBox
		calorieBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
				+ "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");

		return calorieBox;
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
			AddFoodForMeal addFoodForMeal = new AddFoodForMeal(mealName, this, this::updateDailyReport);
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

	private void updateDailyReport() {
		System.out.println("yahoo");
	}

	private void populateListView(String mealName) {
		if (selectedDate == null) {
			showAlert("Seleziona la data", Alert.AlertType.WARNING);
			return;
		}

		PortionGetRequestDTO pgrDTO = new PortionGetRequestDTO(MealType.valueOf(mealName.toUpperCase()), selectedDate, 1);
		try {
			List<PortionGetResponseDTO> portionsDTO = portionController.getPortionsDTO(pgrDTO);				
			// Esegui l'aggiornamento dell'interfaccia grafica sul thread JavaFX
			Platform.runLater(() -> {
				listViewPortions.getItems().clear();
				listViewPortions.getItems().addAll(portionsDTO);
			});
		} 
		catch (Exception ex) {
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
	
	public boolean areUSure() {	    
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
	        if (result.get() == buttonYes){
	            return true;
	        } 
	        else {	        	        	
	            return false;
	        }
	    }
	    return false;
	}


	/*
	 * public void updateCalories(int additionalCalories) { totalCalories +=
	 * additionalCalories; caloriesLabel.setText("Calorie Assunte: " + totalCalories
	 * + " / " + MAX_CALORIES);
	 * 
	 * // Calculate progress double progress = (double) totalCalories /
	 * MAX_CALORIES; caloriesProgressBar.setProgress(progress > 1.0 ? 1.0 :
	 * progress);
	 * 
	 * // Beyond the established limit if (totalCalories > MAX_CALORIES) {
	 * caloriesProgressBar.setStyle("-fx-accent: red;"); } else {
	 * caloriesProgressBar.setStyle("-fx-accent: #76ff03;"); // Green } }
	 */
}
