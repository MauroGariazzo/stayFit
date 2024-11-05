package com.stayFit.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainForm extends Application {

    private BorderPane root;    

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Stay Fit");

        // Configura la sidebar e il layout principale
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setStyle("-fx-background-color: #b22222;");
        
        Label appTitle = new Label("Food App");
        appTitle.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label exitButton = createSidebarButtonWithImage("Exit", "/icons/exit.png");
        exitButton.setOnMouseClicked(e -> primaryStage.close());
        
        Label foodsButton = createSidebarButtonWithImage("Foods", "/icons/report.png");
        foodsButton.setOnMouseClicked(e -> {
            VBox content = new DailyReport(2000,150,200,300).getDailyReportView();
            root.setCenter(content);
        });

        Label drinksButton = createSidebarButtonWithImage("Drinks", "/icons/yourData.png");
        drinksButton.setOnMouseClicked(e -> showDrinksContent());

        Label dessertsButton = createSidebarButtonWithImage("Desserts", "/icons/credits.png");
        dessertsButton.setOnMouseClicked(e -> showDessertsContent());

        Label myCartButton = createSidebarButtonWithImage("MyCart", "/icons/credits.png");
        myCartButton.setOnMouseClicked(e -> showCartContent());

        Label aboutButton = createSidebarButtonWithImage("About", "/icons/report.png");
        aboutButton.setOnMouseClicked(e -> showAboutContent());

        sidebar.getChildren().addAll(appTitle, foodsButton, drinksButton, dessertsButton, myCartButton, aboutButton, exitButton);

        // Layout principale con area centrale modificabile
        root = new BorderPane();
        root.setLeft(sidebar);

        // Mostra un contenuto predefinito all'avvio
        showWelcomeContent();

        // Incapsula BorderPane in uno ScrollPane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);  // Adatta lo scrollbar orizzontale alla larghezza
        scrollPane.setFitToHeight(true); // Adatta lo scrollbar verticale all'altezza

        Scene scene = new Scene(scrollPane);  // Imposta ScrollPane come nodo principale della scena
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
            // Puoi aggiungere un'immagine di default o gestire diversamente l'errore
            image = new Image("file:resources/default.png"); // Assicurati di avere un'immagine di default
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);

        Label button = new Label(text, imageView);
        button.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: transparent;");
        button.setAlignment(Pos.CENTER_LEFT);

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #cc3333; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;"));

        return button;
    }

    // Metodo per mostrare il contenuto della sezione "Drinks"
    private void showDrinksContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("Drinks Section");
        TextField drinkName = new TextField();
        drinkName.setPromptText("Enter drink name");

        ComboBox<String> drinkType = new ComboBox<>();
        drinkType.getItems().addAll("Soft Drink", "Juice", "Alcoholic");

        content.getChildren().addAll(title, drinkName, drinkType);
        root.setCenter(content);
    }

    // Metodo per mostrare il contenuto della sezione "Desserts"
    private void showDessertsContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("Desserts Section");
        TextField dessertName = new TextField();
        dessertName.setPromptText("Enter dessert name");

        content.getChildren().addAll(title, dessertName);
        root.setCenter(content);
    }

    // Metodo per mostrare il contenuto della sezione "MyCart"
    private void showCartContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("My Cart");
        Label cartDetails = new Label("Your cart is currently empty.");

        content.getChildren().addAll(title, cartDetails);
        root.setCenter(content);
    }

    // Metodo per mostrare il contenuto della sezione "About"
    private void showAboutContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("About Food App");
        Label aboutDetails = new Label("This is a sample application created with JavaFX.");

        content.getChildren().addAll(title, aboutDetails);
        root.setCenter(content);
    }

    // Metodo per mostrare un contenuto di benvenuto predefinito
    private void showWelcomeContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("Welcome to the Food App!");
        Label message = new Label("Select a category from the left to get started.");

        content.getChildren().addAll(title, message);
        root.setCenter(content);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
