package com.stayFit.views;

import java.util.ArrayList;
import java.util.List;

import com.stayFit.portion.PortionController;
import com.stayFit.portion.PortionCreateRequestDTO;
import com.stayFit.portion.PortionDAO;
import com.stayFit.portion.PortionGetResponseDTO;
import com.stayFit.product.ProductController;
import com.stayFit.product.ProductCreateRequestDTO;
import com.stayFit.product.ProductDAO;
import com.stayFit.product.ProductGetRequestDTO;
import com.stayFit.product.ProductGetResponseDTO;
import com.stayFit.utils.OpenFoodFactsAPI;
import com.stayFit.utils.PortionListener;
import com.stayFit.portion.PortionGetUseCase;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import com.stayFit.product.ProductCreateUseCase;
import com.stayFit.product.ProductGetUseCase;
import com.stayFit.repository.DBConnector;

public class AddFoodForMealStage {

	/*private final int MAX_CALORIES;
	private final int MAX_PROTEINS;
	private final int MAX_FATS;
	private final int MAX_CARBS;*/
	private VBox overlayPane;
	private StackPane mainContent;
	private ListView<ProductGetResponseDTO> foodListView;
	private String mealType;
	private double grammage;
	public List<PortionGetResponseDTO>portions;
	private PortionListener listener;
	private Runnable updateDailyReport;
	
	public AddFoodForMealStage(String mealType, PortionListener listener, Runnable updateDailyReport) {
		this.mealType = mealType;
		this.grammage = 0;
		this.portions = new ArrayList<>();
		this.listener = listener;
		this.updateDailyReport = updateDailyReport;
	}
	

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
	            return;
	        }
	        foodListView.getItems().clear();  // Pulisce la lista
	        Label loadingMessage = new Label("Caricamento dei prodotti in corso...");
	        foodListView.setPlaceholder(loadingMessage);
	        
	        Task<Void> task = new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	                populateListView(name);
	                return null;
	            }

	            @Override
	            protected void succeeded() {
	                super.succeeded();
	                Platform.runLater(() -> foodName.clear()); // Pulisce il campo di testo
	            }

	            @Override
	            protected void failed() {
	                super.failed();
	                Throwable exception = getException();
	                Platform.runLater(() -> showAlert(exception.getMessage(), Alert.AlertType.WARNING));
	                exception.printStackTrace();
	            }
	        };
	        
	        new Thread(task).start();
	    });


	    // ListView per la lista degli alimenti
	    foodListView = new ListView<>();
	    foodListView.setPrefHeight(300);
	    VBox.setVgrow(foodListView, javafx.scene.layout.Priority.ALWAYS);

	    foodListView.setCellFactory(createCellFactory());

	    // Layout per il bottone di ricerca
	    HBox searchButtonBox = new HBox(searchButton);
	    searchButtonBox.setAlignment(Pos.CENTER_RIGHT);
 
	    Button exitButton = new Button("Esci");
	    exitButton.setOnAction(e -> closeForm());


	    HBox buttonBox = new HBox(10, exitButton);
	    buttonBox.setAlignment(Pos.CENTER);
	    buttonBox.setPadding(new Insets(10, 0, 0, 0));

	    // Contenitore principale per il form
	    VBox formBox = new VBox(10, title, foodName, searchButtonBox, foodListView, buttonBox);
	    formBox.setAlignment(Pos.CENTER);
	    formBox.setPadding(new Insets(10));
	    formBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; "
	            + "-fx-background-radius: 10; -fx-background-color: #f9f9f9;");
	    formBox.setMaxHeight(Double.MAX_VALUE);

	    return formBox;
	}
	

	private Callback<ListView<ProductGetResponseDTO>, ListCell<ProductGetResponseDTO>> createCellFactory() {
	    return new Callback<ListView<ProductGetResponseDTO>, ListCell<ProductGetResponseDTO>>() {
	        @Override
	        public ListCell<ProductGetResponseDTO> call(ListView<ProductGetResponseDTO> listView) {
	            return new ListCell<ProductGetResponseDTO>() {
	                private HBox content;
	                private int id;
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
	                private Button addButton;
	                private Region spacer;
	                
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

	                    addButton = new Button("+");
	                    
	                    addButton.setOnAction(event -> {
	                    	ProductGetResponseDTO product = getItem();
	                    	grammage = new GrammageModal().openGrammageModal();
	                    	createPortion(product);
	                    });


	                    spacer = new Region();
	                    HBox.setHgrow(spacer, Priority.ALWAYS);

	                    content = new HBox(10, imageView, detailsBox, spacer, addButton);
	                    content.setAlignment(Pos.CENTER_LEFT);
	                }

	                @Override
	                protected void updateItem(ProductGetResponseDTO item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item != null && !empty) {
	                        if (item.productImage != null) {
	                            imageView.setImage(item.productImage);
	                        } 
	                        else {
	                            imageView.setImage(null);
	                        }	                        
	                        id = item.id;
	                        nameLabel.setText("Nome: " + item.productName);
	                        brandLabel.setText("Marca: " + item.brand);
	                        categoryLabel.setText("Categoria: " + item.category);
	                        caloriesLabel.setText(String.format("Energia (kcal): %.1f kcal", item.calories));
	                        proteinsLabel.setText(String.format("Proteine: %.1f g", item.proteins));
	                        fatsLabel.setText(String.format("Grassi: %.1f g", item.fats));
	                        carbsLabel.setText(String.format("Carboidrati: %.1f g", item.carbs));
	                        sugarsLabel.setText(String.format("Zuccheri: %.1f g", item.sugars));
	                        saltLabel.setText(String.format("Sale: %.1f g", item.salt));
	                        setGraphic(content);
	                    } 
	                    else {
	                        setGraphic(null);
	                    }
	                }

	                
	                /*private void showError(String message) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Errore");
	                    alert.setHeaderText(null);
	                    alert.setContentText(message);
	                    alert.showAndWait();
	                }*/ 
	            };
	        }
	    };
	}

	
	public void setOverlayPane(VBox overlayPane, StackPane mainContent) {
        this.overlayPane = overlayPane;
        this.mainContent = mainContent;
    }

	public void closeForm() {
	    if (listener != null && !portions.isEmpty()) {
	        listener.onPortionsAdded(mealType, portions);
	    }
	    mainContent.getChildren().remove(overlayPane);
	    updateDailyReport.run();
	}

    
	private void populateListView(String name) {	    
	    try {
	        ProductController controller = new ProductController(                     
	                 new ProductCreateUseCase(new ProductDAO(new DBConnector())),
	                 new ProductGetUseCase(new ProductDAO(new DBConnector())));
	        
	        ProductGetRequestDTO productDTO = new ProductGetRequestDTO(); 
	        productDTO.searchedProduct = name;
	        List<ProductGetResponseDTO> products = controller.get(productDTO);	        
	        	        
	        if(products.size() > 0) {
	            populateFromDB(products);
	        }
	        else {	        	
	        	Platform.runLater(() -> {
	                foodListView.setPlaceholder(new Label("Nessun prodotto trovato"));
	            });            
	        }
	        
	    } 
	    catch (Exception ex) {
	        Platform.runLater(() -> showAlert(ex.getMessage(), Alert.AlertType.WARNING));
	        ex.printStackTrace();
	    }
	}

    
    private void populateFromDB(List<ProductGetResponseDTO> products) {
        Platform.runLater(() -> {
            for (ProductGetResponseDTO product : products) {                        
                foodListView.getItems().add(product);
            }
        });
    }

    
    /*private void populateFromAPI(String name, ProductController controller, List<ProductGetResponseDTO> products) throws Exception {        
        for (ProductGetResponseDTO product : OpenFoodFactsAPI.search(name)) {
            ProductCreateRequestDTO pcrDTO = new ProductCreateRequestDTO();
            
            pcrDTO.productName = product.productName;
            pcrDTO.brand = product.brand;
            pcrDTO.category = product.category;
            pcrDTO.productImage = product.productImage;            
            pcrDTO.calories = product.calories;
            pcrDTO.carbs = product.carbs;
            pcrDTO.fats = product.fats;
            pcrDTO.sugars = product.sugars;
            pcrDTO.salt = product.salt;
            pcrDTO.proteins = product.proteins;
            controller.create(pcrDTO);
            products.add(product);            
        }
        
        ProductGetRequestDTO productDTO = new ProductGetRequestDTO(); 
        productDTO.productName = name;
        productDTO.brand = name;
        productDTO.category = name;
        products = controller.get(productDTO);
        
        populateFromDB(products);
    }*/

    
    private void createPortion(ProductGetResponseDTO selectedProduct) {
    	PortionCreateRequestDTO pcrDTO = new PortionCreateRequestDTO();
    	ProductGetResponseDTO product = selectedProduct;
    	
    	pcrDTO.grams = grammage;    	    	
    	pcrDTO.product_fk = product.id;
    	pcrDTO.calories = product.calories;
    	pcrDTO.carbs = product.carbs;
    	pcrDTO.fats = product.fats;
    	pcrDTO.proteins = product.proteins;
    	pcrDTO.sugars = product.sugars;
    	pcrDTO.salt = product.salt;
 
    	PortionController pc = new PortionController(new PortionGetUseCase(new PortionDAO(new DBConnector())));
    	try {    		   		
    		PortionGetResponseDTO pgrDTO = pc.getPortionDTO(pcrDTO);    		
    		portions.add(pgrDTO);
    	}
    	catch(Exception ex) {
    		showAlert(ex.getMessage(), Alert.AlertType.WARNING);
    	}
    	   	
    }
    
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }      
}