package com.stayFit.views;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import com.stayFit.dailyNutrition.DailyNutritionController;
import com.stayFit.dailyNutrition.DailyNutritionDAO;
import com.stayFit.dailyNutrition.DailyNutritionGetUseCase;
import com.stayFit.dailyNutrition.DailyNutritionResponseGetDTO;
import com.stayFit.enums.MealType;
import com.stayFit.meal.*;
import com.stayFit.mealNutrition.MealNutritionController;
import com.stayFit.mealNutrition.MealNutritionDAO;
import com.stayFit.portion.*;
import com.stayFit.registration.ResponseUserDTO;
import com.stayFit.repository.DBConnector;
import com.stayFit.mealNutrition.MealNutritionGetUseCase;

public class DailyReportStage implements PortionListener {

	/*private final int MAX_CALORIES;
	private final int MAX_PROTEINS;
	private final int MAX_FATS;
	private final int MAX_CARBS;*/
	
	private int MAX_CALORIES;
	private int MAX_PROTEINS;
	private int MAX_FATS;
	private int MAX_CARBS;

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

	private List<Button> addButtons;
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
	private DailyNutritionResponseGetDTO response;
	private MealNutritionController mealNutritionController;
	private Button endDayButton;
	
	private BooleanProperty isDayTerminated = new SimpleBooleanProperty(false);
	private List<PortionGetResponseDTO> portionsDTO;
	private VBox mealsBox = new VBox(20);

	/*public DailyReportStage(final ResponseUserDTO userDTO, final int MAX_CALORIES, final int MAX_PROTEINS,
	final int MAX_FATS, final int MAX_CARBS, LocalDate startingDate, DailyNutritionResponseGetDTO response)*/
	public DailyReportStage(final ResponseUserDTO userDTO, LocalDate startingDate) {
		this.userDTO = userDTO;
		/*this.MAX_CALORIES = MAX_CALORIES;
		this.MAX_PROTEINS = MAX_PROTEINS;
		this.MAX_FATS = MAX_FATS;
		this.MAX_CARBS = MAX_CARBS;*/
		this.startingDate = startingDate;
		this.currentYearMonth = YearMonth.now();
		this.listViewPortions = new ListView<>();
		this.addButtons = new ArrayList<>();
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

		this.caloriesLabel = new Label();
		this.proteinLabel = new Label();
		this.fatLabel = new Label();
		this.carbLabel = new Label();
		this.sugarsLabel = new Label();
		this.saltLabel = new Label();
		this.caloriesProgressBar = new ProgressBar();
		this.proteinProgressBar = new ProgressBar();
		this.fatProgressBar = new ProgressBar();
		this.carbProgressBar = new ProgressBar();

		this.mealNutritionController = new MealNutritionController(
				new MealNutritionGetUseCase(new MealNutritionDAO(new DBConnector())));

		this.portionsDTO = new ArrayList<>();
		DailyNutritionController dailyNutritionController = new DailyNutritionController(
				new DailyNutritionGetUseCase(new DailyNutritionDAO(new DBConnector())));

		response = new DailyNutritionResponseGetDTO();
		try {			
			response = dailyNutritionController.get(userDTO.id);
			this.MAX_CALORIES = response.dailyCalories;
			this.MAX_PROTEINS = response.dailyProteins;
			this.MAX_CARBS = response.dailyCarbs;
			this.MAX_FATS = response.dailyFats;
		} 
		catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
		}
	}

	@Override
	public void onPortionsAdded(String mealType, List<PortionGetResponseDTO> portionsResponseDTO) {
		if (portionsResponseDTO.size() > 0) {
			listViewPortions.getItems().clear(); // Pulisci la listview
			meal.mealType = MealType.valueOf(mealType.toUpperCase());
			meal.mealUpdateDate = selectedDate;
			meal.fk_user = userDTO.id;
			try {
				MealGetRequestDTO mgrDTO = new MealGetRequestDTO(selectedDate, MealType.valueOf(mealType.toUpperCase()),
						meal.fk_user); // fk_user da ottenere
				// Controllo se il pasto esiste già
				int id = mc.getExistingMeal(mgrDTO);
				// Non esiste e lo creo
				if (id == -1) {
					id = mc.getNewMeal(meal);
				}

				// Inserisco la porzione sul DB
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
				System.out.println(ex.getMessage());
			}
		}
		updateMealsSection();
		populateListView(mealType);
	}
		

	public StackPane getDailyReportView() {
	    mainContent = new StackPane();
	    mainContent.setPadding(new Insets(20, 20, 20, 20));
	    mainContent.setAlignment(Pos.TOP_CENTER);

	    VBox topContentBox = new VBox(20);
	    //topContentBox.setAlignment(Pos.TOP_LEFT);
	    topContentBox.setAlignment(Pos.TOP_CENTER);
	    topContentBox.getChildren().addAll(createNavigationBar(), createCalendarView(), createCaloriesOverview());

	    mealsBox = createMealsSection(); // Assegna a mealsBox

	    Node tableView = createListView();

	    HBox mainBody = new HBox(20);
	    mainBody.setAlignment(Pos.TOP_LEFT);
	    mainBody.getChildren().addAll(mealsBox, tableView);
	    mainBody.widthProperty().addListener((obs, oldVal, newVal) -> {
	        double halfWidth = newVal.doubleValue() / 2 - 10;
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
	

	private VBox createMealsSection() {
	    VBox newMealsBox = new VBox(20);	    
	    newMealsBox.setAlignment(Pos.TOP_CENTER);
	    try {
	        PortionGetResponseDTO portionBreakFast = loadPortions(MealType.COLAZIONE);
	        PortionGetResponseDTO portionLunch = loadPortions(MealType.PRANZO);
	        PortionGetResponseDTO portionDinner = loadPortions(MealType.CENA);
	        PortionGetResponseDTO portionSnacks = loadPortions(MealType.SPUNTINO);
	        
	        System.out.println(portionBreakFast.proteins);

	        newMealsBox.getChildren().addAll(
	            createMealItem("COLAZIONE",
	                "Calorie: " + portionBreakFast.calories + "/" + mealNutritionController.get(response.id, MealType.COLAZIONE).calories + " kcal",
	                "Proteine: " + portionBreakFast.proteins + "/" + mealNutritionController.get(response.id, MealType.COLAZIONE).proteins + " gr",
	                "Carboidrati: " + portionBreakFast.carbs + "/" + mealNutritionController.get(response.id, MealType.COLAZIONE).carbs + " gr",
	                "Grassi: " + portionBreakFast.fats + "/" + mealNutritionController.get(response.id, MealType.COLAZIONE).fats + " gr",
	                "/icons/coffee.png", newMealsBox),

	            createMealItem("PRANZO",
	                "Calorie: " + portionLunch.calories + "/" + mealNutritionController.get(response.id, MealType.PRANZO).calories + " kcal",
	                "Proteine: " + portionLunch.proteins + "/" + mealNutritionController.get(response.id, MealType.PRANZO).proteins + " gr",
	                "Carboidrati: " + portionLunch.carbs + "/" +  mealNutritionController.get(response.id, MealType.PRANZO).carbs + " gr",
	                "Grassi: " + portionLunch.fats + "/" +  mealNutritionController.get(response.id, MealType.PRANZO).fats + " gr",
	                "/icons/lunch.png", newMealsBox),

	            createMealItem("CENA",
	                "Calorie: " + portionDinner.calories + "/" + mealNutritionController.get(response.id, MealType.CENA).calories + " kcal",
	                "Proteine: " + portionDinner.proteins + "/" + mealNutritionController.get(response.id, MealType.CENA).proteins + " gr",
	                "Carboidrati: " + portionDinner.carbs + "/" + mealNutritionController.get(response.id, MealType.CENA).carbs + " gr",
	                "Grassi: " + portionDinner.fats + "/" + mealNutritionController.get(response.id, MealType.CENA).fats + " gr",
	                "/icons/meat.png", newMealsBox),

	            createMealItem("SPUNTINO",
	                "Calorie: " + portionSnacks.calories + "/" + mealNutritionController.get(response.id, MealType.SPUNTINO).calories + " kcal",
	                "Proteine: " + portionSnacks.proteins + "/" + mealNutritionController.get(response.id, MealType.SPUNTINO).proteins + " gr",
	                "Carboidrati: " + portionSnacks.carbs + "/" + mealNutritionController.get(response.id, MealType.SPUNTINO).carbs + " gr",
	                "Grassi: " + portionSnacks.fats + "/" + mealNutritionController.get(response.id, MealType.SPUNTINO).fats + " gr",
	                "/icons/snack.png", newMealsBox)
	        );
	    } 
	    catch (Exception ex) {
	        System.out.println(ex.getMessage());
	    }
	    return newMealsBox;
	}


	private void updateMealsSection() {
		mealsBox.getChildren().clear();
	    VBox newMealsSection = createMealsSection();	   
	    mealsBox.getChildren().addAll(newMealsSection.getChildren());
	}
	
	private PortionGetResponseDTO loadPortions(MealType mealType) {
		PortionGetRequestDTO pgrDTO = new PortionGetRequestDTO(mealType,selectedDate, userDTO.id);
		PortionGetResponseDTO portionDTO = new PortionGetResponseDTO(); 
		try {
			portionsDTO = portionController.getPortionsDTO(pgrDTO);		
			for(PortionGetResponseDTO portion : portionsDTO) {
				portionDTO.calories += portion.calories;
				portionDTO.proteins += portion.proteins;
				portionDTO.carbs += portion.carbs;
				portionDTO.fats += portion.fats;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return portionDTO;
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
								
								disableEnableAddButtons();
								//System.out.println(isDayTerminated);
								deleteButton = new Button("-");
								deleteButton.disableProperty().bind(isDayTerminated);

								deleteButton.setOnAction(event -> {
									PortionGetResponseDTO selectedItem = getItem();
									try {
										if (userConfirmation()) {
											PortionController portionControllerDelete = new PortionController(
													new PortionDeleteUseCase(new PortionDAO(new DBConnector())));
											portionControllerDelete.delete(selectedItem.id);
											updateMealsSection();
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
								} else {
									nameLabel.setText("Nome: " + item.product_name);
									caloriesLabel.setText(String.format("Energia (kcal): %.1f kcal", item.calories));
									proteinsLabel.setText(String.format("Proteine: %.1f g", item.proteins));
									fatsLabel.setText(String.format("Grassi: %.1f g", item.fats));
									carbsLabel.setText(String.format("Carboidrati: %.1f g", item.carbs));
									sugarsLabel.setText(String.format("Zuccheri: %.1f g", item.sugars));
									saltLabel.setText(String.format("Sale: %.1f g", item.salt));
									deleteButton.disableProperty().bind(isDayTerminated);
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
		navigationBar.setAlignment(Pos.CENTER);
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
				        selectedDate = currentDate;
				        updateMealsSection(); // Chiama updateMealsSection() per aggiornare la sezione pasti
				    }

				    dayButton.setStyle("-fx-background-color: #76ff03; -fx-text-fill: white; -fx-font-weight: bold;");
				    selectedDayButton = dayButton;
				    selectedDate = currentDate; // Aggiorna la data selezionata
				    updateDailyReport();
				    disableEnableAddButtons();
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
		//calendarContainer.setAlignment(Pos.CENTER);
		calendarContainer.setMaxWidth(400);
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

		VBox caloriesBox = new VBox(10);
		caloriesBox.setAlignment(Pos.CENTER);
		caloriesBox.setMaxWidth(500); 
		caloriesBox.getChildren().addAll(title, caloriesProgressBar, caloriesLabel);

		HBox macrosBox = new HBox(20);
		macrosBox.setAlignment(Pos.CENTER);
		macrosBox.getChildren().addAll(createMacroProgressBox("Proteine", proteinProgressBar, proteinLabel),
				createMacroProgressBox("Grassi", fatProgressBar, fatLabel),
				createMacroProgressBox("Carboidrati", carbProgressBar, carbLabel));

		caloriesBox.getChildren().add(macrosBox);

		endDayButton = new Button("Termina Giornata");
		endDayButton.setStyle(
				"-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");

		endDayButton.setOnAction(e -> {
			try {
				if (userConfirmation()) {
					for (Button b : addButtons) {
						b.setDisable(true);
						MealController mealController = new MealController(
								new MealUpdateUseCase(new MealDAO(new DBConnector())));
						mealController.terminateDay(selectedDate);
						endDayButton.setDisable(true);
						isDayTerminated.set(true);
					}
				}
			} 
			catch (Exception ex) {
				ex.printStackTrace();
			}
		});
				
		caloriesBox.getChildren().add(endDayButton);
		caloriesBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
				+ "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");

		return caloriesBox;
	}

	private void updateNutritionalOverview(List<MealGetResponseDTO> meals) {
		double totalCalories = 0;
		double totalProteins = 0;
		double totalFats = 0;
		double totalCarbs = 0;

		for (MealGetResponseDTO meal : meals) {
			totalCalories += meal.calories;
			totalProteins += meal.proteins;
			totalFats += meal.fats;
			totalCarbs += meal.carbs;
		}

		// Aggiorna le etichette
		caloriesLabel.setText(String.format("Calorie Assunte: %.1f / %d %s", totalCalories, MAX_CALORIES, "kcal"));
		proteinLabel.setText(String.format("Proteine: %.1f g / %d g", totalProteins, MAX_PROTEINS));
		fatLabel.setText(String.format("Grassi: %.1f g / %d g", totalFats, MAX_FATS));
		carbLabel.setText(String.format("Carboidrati: %.1f g / %d g", totalCarbs, MAX_CARBS));

		// Aggiorna le barre di progresso
		caloriesProgressBar.setProgress(totalCalories / MAX_CALORIES);
		proteinProgressBar.setProgress(totalProteins / MAX_PROTEINS < 0.1 ? 0.1 : totalProteins / MAX_PROTEINS);
		fatProgressBar.setProgress(totalFats / MAX_FATS < 0.1 ? 0.1 : totalFats / MAX_FATS);
		carbProgressBar.setProgress(totalCarbs / MAX_CARBS < 0.1 ? 0.1 : totalCarbs / MAX_CARBS);

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

	private HBox createMealItem(String mealName, String calories, String proteins, String carbs, String fats,
			String path, VBox parentContainer) {

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
		Label proteinsLabel = new Label(proteins);
		proteinsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");
		Label carbsLabel = new Label(carbs);
		carbsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");
		Label fatsLabel = new Label(fats);
		fatsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");
		textContainer.getChildren().addAll(mealLabel, caloriesLabel, proteinsLabel, carbsLabel, fatsLabel);

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Button addButton = new Button("+");
		addButtons.add(addButton);
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
		// Disabilita gli addButtons se la giornata è terminata
		disableEnableAddButtons();
		return mealItem;
	}

	private void disableEnableAddButtons() {
	    List<MealGetResponseDTO> meals = getMeals();
	    if (meals.size() > 0) {
	        if (meals.get(0).isTerminated) {
	            for (Button b : addButtons) {
	                b.setDisable(true);
	            }
	            endDayButton.setDisable(true);
	            isDayTerminated.set(true);
	        } else {
	            for (Button b : addButtons) {
	                b.setDisable(false);
	            }
	            endDayButton.setDisable(false);
	            isDayTerminated.set(false);
	        }
	    } else {
	        for (Button b : addButtons) {
	            b.setDisable(false);
	        }
	        endDayButton.setDisable(false);
	        isDayTerminated.set(false);
	    }
	    listViewPortions.refresh(); // Add this line
	}



	private List<MealGetResponseDTO> getMeals() {
		List<MealGetResponseDTO> meals = new ArrayList<>();
		try {			
			MealGetRequestDTO mgrDTO = new MealGetRequestDTO(selectedDate, userDTO.id);			
			meals = mc.getDailyNutritions(mgrDTO);
		} catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
		}
		return meals;
	}

	// AGGIORNA I VALORI NUTRIZIONALI ASSUNTI DURANTE LA GIORNATA
	private void updateDailyReport() {		
		try {
			List<MealGetResponseDTO> meals = getMeals();
			updateNutritionalOverview(meals);
			
		} catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
		}
	}
	
		
	private void populateListView(String mealName) {		
		if (selectedDate == null) {
			showAlert("Seleziona la data", Alert.AlertType.WARNING);
			return;
		}

		PortionGetRequestDTO pgrDTO = new PortionGetRequestDTO(MealType.valueOf(mealName.toUpperCase()), 
				selectedDate, userDTO.id);
		try {
			portionsDTO = portionController.getPortionsDTO(pgrDTO);			
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

