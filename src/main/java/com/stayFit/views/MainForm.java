package com.stayFit.views;

import java.time.LocalDate;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainForm extends Application {

    private BorderPane root;    
    private ScrollPane scrollPane; // Aggiunto ScrollPane come variabile di istanza

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

        LocalDate dateTest = LocalDate.of(2024, 10, 25);
        Label foodsButton = createSidebarButtonWithImage("Foods", "/icons/report.png");
        foodsButton.setOnMouseClicked(e -> {
            StackPane content = new DailyReport(2000, 150, 200, 300, dateTest).getDailyReportView();
            scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
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

        // Incapsula solo l'area centrale in uno ScrollPane
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        root.setCenter(scrollPane); // Imposta lo ScrollPane come contenuto centrale

        // Mostra un contenuto predefinito all'avvio
        showWelcomeContent();

        Scene scene = new Scene(root);  // Usa root come nodo principale della scena
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
        scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
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
        scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
    }

    // Metodo per mostrare il contenuto della sezione "MyCart"
    private void showCartContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("My Cart");
        Label cartDetails = new Label("Your cart is currently empty.");

        content.getChildren().addAll(title, cartDetails);
        scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
    }

    // Metodo per mostrare il contenuto della sezione "About"
    private void showAboutContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("About Food App");
        Label aboutDetails = new Label("This is a sample application created with JavaFX.");

        content.getChildren().addAll(title, aboutDetails);
        scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
    }

    // Metodo per mostrare un contenuto di benvenuto predefinito
    private void showWelcomeContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label title = new Label("Welcome to the Food App!");
        Label message = new Label("Select a category from the left to get started.");

        content.getChildren().addAll(title, message);
        scrollPane.setContent(content); // Aggiorna il contenuto dello ScrollPane
    }

    public static void main(String[] args) {
        launch(args);
    }
}
