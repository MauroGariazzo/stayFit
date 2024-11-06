package com.stayFit.views;


import com.stayFit.models.Product;
import com.stayFit.utils.OpenFoodFactsAPI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class AddFoodForMeal {

	/*private final int MAX_CALORIES;
	private final int MAX_PROTEINS;
	private final int MAX_FATS;
	private final int MAX_CARBS;*/
	private VBox overlayPane;
	private StackPane mainContent;

	private ProgressBar caloriesProgressBar;
	private Label caloriesLabel;

	private ProgressBar proteinProgressBar;
	private Label proteinLabel;
	private ProgressBar fatProgressBar;
	private Label fatLabel;
	private ProgressBar carbProgressBar;
	private Label carbLabel;
	private ListView<Product> foodListView;
	private String mealType;
	
	public AddFoodForMeal(String mealType) {
		this.mealType = mealType;
	}
	/*private int totalCalories = 0;
	private int totalProteins = 0;
	private int totalFats = 0;
	private int totalCarbs = 0;

	private ListView<Product> foodListView; // Modificato per usare Product*/

	/*public AddFoodForMeal(final int MAX_CALORIES, final int MAX_PROTEINS, final int MAX_FATS, final int MAX_CARBS) {
		this.MAX_CALORIES = MAX_CALORIES;
		this.MAX_PROTEINS = MAX_PROTEINS;
		this.MAX_FATS = MAX_FATS;
		this.MAX_CARBS = MAX_CARBS;
	}*/

	
	/*private VBox createCaloriesOverview() {
		// Titolo della sezione
		Label title = new Label("Panoramica Nutrizionale");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		// Barra delle calorie
		caloriesProgressBar = new ProgressBar(0);
		caloriesProgressBar.setPrefWidth(300);
		caloriesProgressBar.setStyle("-fx-accent: #76ff03;"); // Colore iniziale verde

		// Etichetta calorie
		caloriesLabel = new Label("Calorie Assunte: 0 / " + MAX_CALORIES);
		caloriesLabel.setStyle("-fx-font-size: 14px;");

		// Barra delle proteine
		proteinProgressBar = new ProgressBar(0);
		proteinProgressBar.setPrefWidth(100);
		proteinProgressBar.setStyle("-fx-accent: #2196F3;"); // Blu

		// Etichetta proteine
		proteinLabel = new Label("Proteine: 0g / " + MAX_PROTEINS + "g");
		proteinLabel.setStyle("-fx-font-size: 12px;");

		// Barra dei grassi
		fatProgressBar = new ProgressBar(0);
		fatProgressBar.setPrefWidth(100);
		fatProgressBar.setStyle("-fx-accent: #FFC107;"); // Giallo

		// Etichetta grassi
		fatLabel = new Label("Grassi: 0g / " + MAX_FATS + "g");
		fatLabel.setStyle("-fx-font-size: 12px;");

		// Barra dei carboidrati
		carbProgressBar = new ProgressBar(0);
		carbProgressBar.setPrefWidth(100);
		carbProgressBar.setStyle("-fx-accent: #FF5722;"); // Arancione

		// Etichetta carboidrati
		carbLabel = new Label("Carboidrati: 0g / " + MAX_CARBS + "g");
		carbLabel.setStyle("-fx-font-size: 12px;");

		// Layout per le barre nutrizionali
		VBox calorieBox = new VBox(10);
		calorieBox.setAlignment(Pos.CENTER);
		calorieBox.getChildren().addAll(title, caloriesProgressBar, caloriesLabel);

		// Layout per le barre proteine, grassi e carboidrati
		HBox macrosBox = new HBox(20);
		macrosBox.setAlignment(Pos.CENTER);
		macrosBox.getChildren().addAll(createMacroProgressBox("Proteine", proteinProgressBar, proteinLabel),
				createMacroProgressBox("Grassi", fatProgressBar, fatLabel),
				createMacroProgressBox("Carboidrati", carbProgressBar, carbLabel));

		calorieBox.getChildren().add(macrosBox);

		// Stile per calorieBox
		calorieBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
				+ "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");

		return calorieBox;
	}*/


	/*private VBox createMacroProgressBox(String name, ProgressBar progressBar, Label label) {
		Label macroNameLabel = new Label(name);
		macroNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

		VBox macroBox = new VBox(5);
		macroBox.setAlignment(Pos.CENTER);
		macroBox.getChildren().addAll(macroNameLabel, progressBar, label);

		return macroBox;
	}*/
	
	/*private HBox createMealItem(String mealName, String calories, String details, String path) {
	    HBox mealItem = new HBox(10);
	    mealItem.setPadding(new Insets(10));
	    mealItem.setAlignment(Pos.BASELINE_LEFT);
	    mealItem.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5;");
	    mealItem.setMaxWidth(500);

	    // Icona pasto
	    Image icon = new Image(path);
	    ImageView iconView = new ImageView(icon);
	    iconView.setFitWidth(40);
	    iconView.setFitHeight(40);

	    // Contenitore testo (Nome pasto e calorie)
	    VBox textContainer = new VBox(5);
	    Label mealLabel = new Label(mealName);
	    mealLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    Label caloriesLabel = new Label(calories);
	    caloriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
	    Label detailsLabel = new Label(details);
	    detailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");
	    textContainer.getChildren().addAll(mealLabel, caloriesLabel, detailsLabel);

	    // Spaziatore per spingere il pulsante a destra
	    Region spacer = new Region();
	    HBox.setHgrow(spacer, Priority.ALWAYS);

	    // Pulsante "+"
	    Button addButton = new Button("+");
	    addButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #007bff; -fx-background-radius: 20;");
	    addButton.setMinSize(30, 30);
	    //addButton.setOnAction(event -> openForm());
	    // Aggiungere elementi all'HBox
	    mealItem.getChildren().addAll(iconView, textContainer, spacer, addButton);
	    return mealItem;
	}*/


	public VBox searchFoodForm() {
	    // Titolo "Foods Section"
	    Label title = new Label(mealType);
	    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

	    // Campo di testo per il nome del cibo
	    TextField foodName = new TextField();
	    foodName.setPromptText("Inserisci prodotto");

	    // Bottone per cercare il cibo
	    Button searchButton = new Button("Cerca");
	    searchButton.setOnAction(e -> {
	        String name = foodName.getText().trim();
	        if (name.isEmpty()) {
	            //showError("Per favore, inserisci il nome del cibo.");
	            return;
	        }
	        populateTableView(name);
	        foodName.clear();
	    });

	    // ListView per la lista degli alimenti
	    foodListView = new ListView<>();
	    foodListView.setPrefHeight(300);
	    VBox.setVgrow(foodListView, javafx.scene.layout.Priority.ALWAYS);

	    // Configurazione del CellFactory per visualizzare le informazioni del prodotto
	    foodListView.setCellFactory(createCellFactory());

	    // Layout per il bottone di ricerca
	    HBox searchButtonBox = new HBox(searchButton);
	    searchButtonBox.setAlignment(Pos.CENTER_RIGHT);

	    // Crea due nuovi pulsanti sotto la ListView
	    Button addProductButton = new Button("Aggiungi");	    
	    Button exitButton = new Button("Esci");
	    exitButton.setOnAction(e -> closeForm());

	    // Layout per i pulsanti
	    HBox buttonBox = new HBox(10, addProductButton, exitButton);
	    buttonBox.setAlignment(Pos.CENTER);
	    buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Aggiungi padding per distanziarli dalla ListView

	    // Contenitore principale per il form
	    VBox formBox = new VBox(10, title, foodName, searchButtonBox, foodListView, buttonBox);
	    formBox.setAlignment(Pos.CENTER);
	    formBox.setPadding(new Insets(10));
	    formBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
	            + "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");
	    formBox.setMaxHeight(Double.MAX_VALUE);

	    return formBox;
	}
	
	// All'interno della tua classe
	private Callback<ListView<Product>, ListCell<Product>> createCellFactory() {
	    return new Callback<ListView<Product>, ListCell<Product>>() {
	        @Override
	        public ListCell<Product> call(ListView<Product> listView) {
	            return new ListCell<Product>() {
	                private HBox content;
	                private ImageView imageView;
	                private VBox detailsBox;
	                private Label nameLabel;
	                private Label brandLabel;
	                private Label categoryLabel;
	                private Label caloriesLabel;
	                private Label proteinsLabel;
	                private Label fatsLabel;
	                private Label carbsLabel;
	                private Label sugarsLabel;
	                private Label saltLabel;

	                {
	                    imageView = new ImageView();
	                    imageView.setFitWidth(50);
	                    imageView.setFitHeight(50);
	                    imageView.setPreserveRatio(true);
	                    imageView.setSmooth(true);
	                    imageView.setCache(true);

	                    nameLabel = new Label();
	                    nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
	                    brandLabel = new Label();
	                    categoryLabel = new Label();
	                    caloriesLabel = new Label();
	                    proteinsLabel = new Label();
	                    fatsLabel = new Label();
	                    carbsLabel = new Label();
	                    sugarsLabel = new Label();
	                    saltLabel = new Label();

	                    detailsBox = new VBox(2);
	                    detailsBox.getChildren().addAll(nameLabel, brandLabel, categoryLabel, caloriesLabel,
	                            proteinsLabel, fatsLabel, carbsLabel, sugarsLabel, saltLabel);

	                    content = new HBox(10, imageView, detailsBox);
	                    content.setAlignment(Pos.CENTER_LEFT);
	                }

	                @Override
	                protected void updateItem(Product item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item != null && !empty) {
	                        if (item.getProductImage() != null) {
	                            imageView.setImage(item.getProductImage());
	                        } else {
	                            imageView.setImage(null);
	                        }
	                        nameLabel.setText("Nome: " + item.getProductName());
	                        brandLabel.setText("Marca: " + item.getBrand());
	                        categoryLabel.setText("Categoria: " + item.getCategory());
	                        caloriesLabel.setText(String.format("Energia (kcal): %.1f kcal", item.getCalories()));
	                        proteinsLabel.setText(String.format("Proteine: %.1f g", item.getProteins()));
	                        fatsLabel.setText(String.format("Grassi: %.1f g", item.getFats()));
	                        carbsLabel.setText(String.format("Carboidrati: %.1f g", item.getCarbs()));
	                        sugarsLabel.setText(String.format("Zuccheri: %.1f g", item.getSugars()));
	                        saltLabel.setText(String.format("Sale: %.1f g", item.getSalt()));
	                        setGraphic(content);
	                    } 
	                    else {
	                        setGraphic(null);
	                    }
	                }
	            };
	        }
	    };
	}

	
	public void setOverlayPane(VBox overlayPane, StackPane mainContent) {
        this.overlayPane = overlayPane;
        this.mainContent = mainContent;
    }

    // Method to close the overlay
    public void closeForm() {
        mainContent.getChildren().remove(overlayPane);
    }
    
    private void populateTableView(String name) {
    	foodListView.getItems().clear();
    	try {
            for (Product product : OpenFoodFactsAPI.search(name)) {                
                foodListView.getItems().add(product);
            }
        } 
    	catch (Exception ex) {
    		showError(ex.getMessage());
        }
    }
    
    private void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/*public void updateCalories(int additionalCalories) {
		totalCalories += additionalCalories;
		caloriesLabel.setText("Calorie Assunte: " + totalCalories + " / " + MAX_CALORIES);

		// Calcolare il progresso
		double progress = (double) totalCalories / MAX_CALORIES;
		caloriesProgressBar.setProgress(progress > 1.0 ? 1.0 : progress);

		// Al di fuori del limite stabilito
		if (totalCalories > MAX_CALORIES) {
			caloriesProgressBar.setStyle("-fx-accent: red;");
		} else {
			caloriesProgressBar.setStyle("-fx-accent: #76ff03;"); // Verde
		}
	}


	public void updateProteins(int additionalProteins) {
		// Aggiornare il totale delle proteine
		totalProteins += additionalProteins;

		// Aggiornare l'etichetta
		proteinLabel.setText("Proteine: " + totalProteins + "g / " + MAX_PROTEINS + "g");

		// Calcolare il progresso
		double progress = (double) totalProteins / MAX_PROTEINS;
		proteinProgressBar.setProgress(progress > 1.0 ? 1.0 : progress);

		// Cambiare il colore della ProgressBar in base al superamento del limite
		if (totalProteins > MAX_PROTEINS) {
			proteinProgressBar.setStyle("-fx-accent: red;");
		} else {
			proteinProgressBar.setStyle("-fx-accent: #2196F3;"); // Blu
		}
	}


	public void updateFats(int additionalFats) {
		// Aggiornare il totale dei grassi
		totalFats += additionalFats;

		// Aggiornare l'etichetta
		fatLabel.setText("Grassi: " + totalFats + "g / " + MAX_FATS + "g");

		// Calcolare il progresso
		double progress = (double) totalFats / MAX_FATS;
		fatProgressBar.setProgress(progress > 1.0 ? 1.0 : progress);

		// Cambiare il colore della ProgressBar in base al superamento del limite
		if (totalFats > MAX_FATS) {
			fatProgressBar.setStyle("-fx-accent: red;");
		} else {
			fatProgressBar.setStyle("-fx-accent: #FFC107;"); // Giallo
		}
	}


	public void updateCarbs(int additionalCarbs) {
		// Aggiornare il totale dei carboidrati
		totalCarbs += additionalCarbs;

		// Aggiornare l'etichetta
		carbLabel.setText("Carboidrati: " + totalCarbs + "g / " + MAX_CARBS + "g");

		// Calcolare il progresso
		double progress = (double) totalCarbs / MAX_CARBS;
		carbProgressBar.setProgress(progress > 1.0 ? 1.0 : progress);

		// Cambiare il colore della ProgressBar in base al superamento del limite
		if (totalCarbs > MAX_CARBS) {
			carbProgressBar.setStyle("-fx-accent: red;");
		} else {
			carbProgressBar.setStyle("-fx-accent: #FF5722;"); // Arancione
		}
	}


	private void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}*/
}