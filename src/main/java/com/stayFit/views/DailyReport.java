package com.stayFit.views;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane; // Changed to StackPane
import javafx.scene.layout.VBox;

import com.stayFit.models.Product;

public class DailyReport {

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

    private StackPane mainContent; // Changed from VBox to StackPane

    /**
     * Constructor of the DailyReport class.
     *
     * @param MAX_CALORIES  Maximum daily calories.
     * @param MAX_PROTEINS  Maximum daily proteins.
     * @param MAX_FATS      Maximum daily fats.
     * @param MAX_CARBS     Maximum daily carbs.
     * @param startingDate  Starting date for the calendar.
     */
    public DailyReport(final int MAX_CALORIES, final int MAX_PROTEINS, final int MAX_FATS, final int MAX_CARBS, LocalDate startingDate) {
        this.MAX_CALORIES = MAX_CALORIES;
        this.MAX_PROTEINS = MAX_PROTEINS;
        this.MAX_FATS = MAX_FATS;
        this.MAX_CARBS = MAX_CARBS;
        this.startingDate = startingDate;
        this.currentYearMonth = YearMonth.now(); // Set the current month on startup
    }

    /**
     * Gets the view of the Daily Report.
     *
     * @return A StackPane containing all the sections of the Daily Report.
     */
    public StackPane getDailyReportView() {
        // Initialize the main container as a StackPane
        mainContent = new StackPane();
        mainContent.setPadding(new Insets(20, 20, 20, 20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        // Create a VBox to hold the top content
        VBox topContentBox = new VBox(20); // Spacing between elements
        topContentBox.setAlignment(Pos.TOP_LEFT);

        // Add the top sections
        topContentBox.getChildren().addAll(
            createNavigationBar(),
            createCalendarView(),
            createCalorieOverview()
        );

        // Create a VBox for meal items
        VBox mealsBox = new VBox(20); // Spacing between meal items
        mealsBox.setAlignment(Pos.TOP_LEFT);
        mealsBox.getChildren().addAll(
            createMealItem("Colazione", "50 kcal", "pippo", "/icons/coffee.png", mealsBox),
            createMealItem("Pranzo", "50 kcal", "pippo", "/icons/lunch.png", mealsBox),
            createMealItem("Cena", "50 kcal", "pippo", "/icons/meat.png", mealsBox),
            createMealItem("Spuntini", "50 kcal", "pippo", "/icons/snack.png", mealsBox)
        );

        // Create the table view
        Node tableView = createTableView();

        // Create an HBox to hold the mealsBox and tableView side by side
        HBox mainBody = new HBox(20); // Spacing between mealsBox and tableView
        mainBody.setAlignment(Pos.TOP_LEFT);

        // Add mealsBox and tableView to mainBody
        mainBody.getChildren().addAll(mealsBox, tableView);

        // Bind the prefWidthProperty to divide the space equally
        mainBody.widthProperty().addListener((obs, oldVal, newVal) -> {
            double halfWidth = newVal.doubleValue() / 2 - 10; // Subtract spacing
            mealsBox.setPrefWidth(halfWidth);
            if (tableView instanceof Region) {
                ((Region) tableView).setPrefWidth(halfWidth);
            }
        });

        // Alternatively, using bindings
        /*
        mealsBox.prefWidthProperty().bind(mainBody.widthProperty().multiply(0.5).subtract(10));
        if (tableView instanceof Region) {
            ((Region) tableView).prefWidthProperty().bind(mainBody.widthProperty().multiply(0.5).subtract(10));
        }
        */

        // Create a VBox to hold all content
        VBox contentBox = new VBox(20); // Spacing between topContentBox and mainBody
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.getChildren().addAll(
            topContentBox,
            mainBody
        );

        // Add contentBox to mainContent        
        mainContent.getChildren().add(contentBox);

        return mainContent;
    }

    private HBox createTableView() {    	
        HBox tableContainer = new HBox();
        tableContainer.setPadding(new Insets(10));
        tableContainer.setAlignment(Pos.TOP_RIGHT);
        tableContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5;");

        // Crea e configura la tua TableView qui
        TableView<Product> tableView = new TableView<>();
        // Configura le colonne e aggiungi i dati alla tableView

        // Esempio di configurazione delle colonne
        TableColumn<Product, String> column1 = new TableColumn<>("Colonna 1");
        column1.setCellValueFactory(new PropertyValueFactory<>("property1"));
        TableColumn<Product, String> column2 = new TableColumn<>("Colonna 2");
        column2.setCellValueFactory(new PropertyValueFactory<>("property2"));
        tableView.getColumns().addAll(column1, column2);

        // Aggiungi la tableView al container
        tableContainer.getChildren().add(tableView);
        
        // Imposta la larghezza preferita della tableView
        tableView.setPrefWidth(400); // Questo sarà sovrascritto dal binding

        // Assicurati che tableView possa crescere per riempire lo spazio
        HBox.setHgrow(tableView, Priority.ALWAYS);
        
        return tableContainer;
    }


    /**
     * Creates the navigation bar for the calendar with "Previous" and "Next" buttons.
     *
     * @return An HBox containing the navigation buttons.
     */
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
     * Creates a monthly calendar view where only days up to a specific date are enabled.
     * Days before startingDate and after today are disabled.
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
        // Calculate the day of the week of the first day of the month (Mon = 1, Sun = 7)
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // Number of days in the current month
        int daysInMonth = currentYearMonth.lengthOfMonth();

        // Populate the calendar with days
        int row = 1;
        int col = firstDayOfWeek - 1; // Java DayOfWeek starts from 1 (Monday)
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = currentYearMonth.atDay(day);
            Button dayButton = new Button(String.valueOf(day));
            dayButton.setPrefSize(40, 40);

            // Determine if the day is enabled
            boolean isEnabled = isDayEnabled(currentDate, today);

            if (isEnabled) {
                dayButton.setDisable(false);
                // Set default style to blue
                dayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

                // Highlight today's day if the current month is the current one
                if (currentDate.equals(today) && currentYearMonth.equals(YearMonth.from(today))) {
                    dayButton.setStyle("-fx-background-color: #76ff03; -fx-text-fill: white; -fx-font-weight: bold;");
                    selectedDayButton = dayButton; // Set today's day as default selected
                }

                // Event handler for day selection
                dayButton.setOnAction(e -> {
                    // Restore the style of the previously selected button
                    if (selectedDayButton != null) {
                        // Restore previous style
                        selectedDayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                    }

                    // Set the style of the new selected button
                    dayButton.setStyle("-fx-background-color: #76ff03; -fx-text-fill: white; -fx-font-weight: bold;");
                    selectedDayButton = dayButton;

                    // You can add further actions to execute when a day is selected
                    // For example: show daily report details
                    // handleDayClick(currentDate);
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

    /**
     * Determines if a day is enabled based on the starting date and today's date.
     *
     * @param date  The date of the day to check.
     * @param today Today's date.
     * @return true if the day is enabled, false otherwise.
     */
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

    /**
     * Creates the nutritional overview section with progress bars.
     *
     * @return A VBox containing the nutritional overview.
     */
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
        macrosBox.getChildren().addAll(
            createMacroProgressBox("Proteine", proteinProgressBar, proteinLabel),
            createMacroProgressBox("Grassi", fatProgressBar, fatLabel),
            createMacroProgressBox("Carboidrati", carbProgressBar, carbLabel)
        );

        calorieBox.getChildren().add(macrosBox);

        // Style for calorieBox
        calorieBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
                + "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");

        return calorieBox;
    }

    /**
     * Creates a single progress bar for a macronutrient.
     *
     * @param name        The name of the macronutrient.
     * @param progressBar The progress bar.
     * @param label       The associated label.
     * @return A VBox containing the macronutrient.
     */
    private VBox createMacroProgressBox(String name, ProgressBar progressBar, Label label) {
        Label macroNameLabel = new Label(name);
        macroNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox macroBox = new VBox(5);
        macroBox.setAlignment(Pos.CENTER);
        macroBox.getChildren().addAll(macroNameLabel, progressBar, label);

        return macroBox;
    }

    /**
     * Creates a meal item with icon, name, calories, and an add button.
     *
     * @param mealName Name of the meal.
     * @param calories Calories of the meal.
     * @param details  Additional details.
     * @param path     Path to the icon.
     * @return An HBox representing the meal item.
     */
    private HBox createMealItem(String mealName, String calories, String details, String path, VBox parentContainer) {
        HBox mealItem = new HBox(10);
        mealItem.setPadding(new Insets(10));
        mealItem.setAlignment(Pos.BASELINE_LEFT);
        mealItem.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5;");

        // Imposta la larghezza massima a metà della larghezza del contenitore padre
        //mealItem.maxWidthProperty().bind(parentContainer.widthProperty().divide(2));

        // Meal icon
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

        // Text container (Meal name and calories)
        VBox textContainer = new VBox(5);
        Label mealLabel = new Label(mealName);
        mealLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label caloriesLabel = new Label(calories);
        caloriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        Label detailsLabel = new Label(details);
        detailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");
        textContainer.getChildren().addAll(mealLabel, caloriesLabel, detailsLabel);

        // Spacer to push the button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // "+" Button
        Button addButton = new Button("+");
        addButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #007bff; -fx-background-radius: 20;");
        addButton.setMinSize(30, 30);
        addButton.setOnMouseClicked(event -> {
            // Create an instance of AddFoodForMeal
            AddFoodForMeal addFoodForMeal = new AddFoodForMeal(mealName);
            // Assuming createFoodForm returns a Node (e.g., VBox or Pane)
            Node foodForm = addFoodForMeal.searchFoodForm();

            // Create an overlay pane with semi-transparent background
            VBox overlayPane = new VBox();
            overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
            overlayPane.setAlignment(Pos.CENTER);
            overlayPane.setPrefSize(mainContent.getWidth(), mainContent.getHeight());

            // Add the foodForm to the overlayPane
            overlayPane.getChildren().add(foodForm);

            // Add the overlayPane to the mainContent StackPane
            mainContent.getChildren().add(overlayPane);

            // Handle closing the overlay when done, perhaps in the foodForm's own controls
            // For example, in the AddFoodForMeal class, have a button that, when clicked, removes the overlayPane from mainContent

            // Alternatively, you can pass the overlayPane and mainContent to AddFoodForMeal
            addFoodForMeal.setOverlayPane(overlayPane, mainContent);
        });
        
        Button viewMeals = new Button(">");
        viewMeals.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #007bff; -fx-background-radius: 20;");
        viewMeals.setMinSize(30, 30);

        // Add elements to the HBox
        mealItem.getChildren().addAll(iconView, textContainer, spacer, addButton, viewMeals);
        return mealItem;
    }


    /*
    // Original updateCalories method and other methods commented out
    public void updateCalories(int additionalCalories) {
        totalCalories += additionalCalories;
        caloriesLabel.setText("Calorie Assunte: " + totalCalories + " / " + MAX_CALORIES);

        // Calculate progress
        double progress = (double) totalCalories / MAX_CALORIES;
        caloriesProgressBar.setProgress(progress > 1.0 ? 1.0 : progress);

        // Beyond the established limit
        if (totalCalories > MAX_CALORIES) {
            caloriesProgressBar.setStyle("-fx-accent: red;");
        } else {
            caloriesProgressBar.setStyle("-fx-accent: #76ff03;"); // Green
        }
    }

    // Other update methods and showError...
    */
}
