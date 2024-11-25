package com.stayFit.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.stayFit.product.ProductGetResponseDTO;

public class GrammageModal extends ListCell<ProductGetResponseDTO> {
	double grammage = 0;
	
	public double openGrammageModal() {						
	    Stage modalStage = new Stage();
	    modalStage.initModality(Modality.WINDOW_MODAL);
	    modalStage.setTitle("Inserisci Grammatura");

	    Label instructionLabel = new Label("Inserisci la grammatura del prodotto:");
	    TextField grammageField = new TextField();
	    grammageField.setPromptText("Grammi");

	    Button confirmButton = new Button("Conferma");
	    Button cancelButton = new Button("Annulla");

	    cancelButton.setOnAction(e -> {
	        modalStage.close();
	    });

	    confirmButton.setOnAction(e -> {
	        String input = grammageField.getText().trim();            
	        try {            	
	            grammage = Double.parseDouble(input);                
	            if (grammage <= 0) {
	                throw new NumberFormatException("La grammatura deve essere un numero positivo.");
	            }
	            //System.out.println("Grammage set to: " + grammage);
	            modalStage.close();
	        } 
	        catch (NumberFormatException ex) {
	            System.out.println(ex.getMessage());
	        }            
	    });

	    // Layout per i pulsanti del modal
	    HBox buttonsBox = new HBox(10, confirmButton, cancelButton);
	    buttonsBox.setAlignment(Pos.CENTER);

	    // Layout principale del modal
	    VBox modalLayout = new VBox(10, instructionLabel, grammageField, buttonsBox);
	    modalLayout.setAlignment(Pos.CENTER);
	    modalLayout.setPadding(new Insets(20));

	    Scene modalScene = new Scene(modalLayout);
	    modalStage.setScene(modalScene);
	    modalStage.showAndWait();	    
	    //System.out.println("Grammage after modal closed: " + grammage); // Sposta qui la stampa

	    return grammage;
	}
}
