package com.stayFit.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ReportStage {

    private StackedBarChart<String, Number> nutrientChart;
    private Label carbsLabel, proteinsLabel, fatsLabel;

    public StackPane createReportContent() {
        // ** GRAFICO DEL PESO **
        LineChart<Number, Number> weightChart = createWeightChart();

        // ** SEZIONE NUTRIENTI **
        VBox nutrientReport = createNutrientReport();

        // ** LAYOUT GENERALE **
        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.getChildren().addAll(weightChart, nutrientReport);

        // Contenitore principale
        StackPane reportPane = new StackPane(content);
        reportPane.setPrefHeight(800);

        return reportPane;
    }

    private LineChart<Number, Number> createWeightChart() {
        // Assi del grafico del peso
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Tempo (giorni)");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Peso (kg)");

        // Creazione del grafico del peso
        LineChart<Number, Number> weightChart = new LineChart<>(xAxis, yAxis);
        weightChart.setTitle("Andamento del Peso nel Tempo");
        weightChart.setPrefHeight(300);

        // Serie di dati (esempio)
        XYChart.Series<Number, Number> weightSeries = new XYChart.Series<>();
        weightSeries.setName("Peso");
        weightSeries.getData().add(new XYChart.Data<>(1, 70)); // Giorno 1, Peso 70kg
        weightSeries.getData().add(new XYChart.Data<>(2, 69.5)); // Giorno 2, Peso 69.5kg
        weightSeries.getData().add(new XYChart.Data<>(3, 69)); // Giorno 3, Peso 69kg
        weightSeries.getData().add(new XYChart.Data<>(4, 68.5)); // Giorno 4, Peso 68.5kg

        weightChart.getData().add(weightSeries);

        return weightChart;
    }

    private VBox createNutrientReport() {
        // ** ComboBox per selezione periodo **
        ComboBox<String> periodComboBox = new ComboBox<>();
        periodComboBox.getItems().addAll("Giornaliero", "Settimanale", "Mensile");
        periodComboBox.setValue("Giornaliero"); // Valore predefinito

        periodComboBox.setOnAction(e -> {
            String selectedPeriod = periodComboBox.getValue();
            updateNutrientData(selectedPeriod);
        });

        // ** Etichette per riepilogo nutrienti **
        carbsLabel = new Label("Carboidrati: -");
        proteinsLabel = new Label("Proteine: -");
        fatsLabel = new Label("Grassi: -");

        VBox summaryBox = new VBox(10, carbsLabel, proteinsLabel, fatsLabel);
        summaryBox.setAlignment(Pos.CENTER_LEFT);

        // ** Grafico nutrienti **
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Data");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Quantità (g)");

        nutrientChart = new StackedBarChart<>(xAxis, yAxis);
        nutrientChart.setTitle("Consumi Nutrienti");
        nutrientChart.setPrefHeight(300);

        // Contenitore per grafico e riepilogo
        VBox nutrientReport = new VBox(20);
        nutrientReport.setAlignment(Pos.TOP_CENTER);
        nutrientReport.getChildren().addAll(periodComboBox, summaryBox, nutrientChart);

        // Aggiorna inizialmente con dati giornalieri
        updateNutrientData("Giornaliero");

        return nutrientReport;
    }

    private void updateNutrientData(String period) {
        nutrientChart.getData().clear();

        XYChart.Series<String, Number> carbsSeries = new XYChart.Series<>();
        carbsSeries.setName("Carboidrati");

        XYChart.Series<String, Number> proteinsSeries = new XYChart.Series<>();
        proteinsSeries.setName("Proteine");

        XYChart.Series<String, Number> fatsSeries = new XYChart.Series<>();
        fatsSeries.setName("Grassi");

        if ("Giornaliero".equals(period)) {
            carbsSeries.getData().add(new XYChart.Data<>("1 Ott", 23));
            proteinsSeries.getData().add(new XYChart.Data<>("1 Ott", 5));
            fatsSeries.getData().add(new XYChart.Data<>("1 Ott", 1));

            carbsSeries.getData().add(new XYChart.Data<>("3 Ott", 24));
            proteinsSeries.getData().add(new XYChart.Data<>("3 Ott", 6));
            fatsSeries.getData().add(new XYChart.Data<>("3 Ott", 2));

            updateSummary(23, 5, 1); // Media giornaliera

        } else if ("Settimanale".equals(period)) {
            carbsSeries.getData().add(new XYChart.Data<>("Settimana 1", 150));
            proteinsSeries.getData().add(new XYChart.Data<>("Settimana 1", 35));
            fatsSeries.getData().add(new XYChart.Data<>("Settimana 1", 10));

            carbsSeries.getData().add(new XYChart.Data<>("Settimana 2", 160));
            proteinsSeries.getData().add(new XYChart.Data<>("Settimana 2", 40));
            fatsSeries.getData().add(new XYChart.Data<>("Settimana 2", 12));

            updateSummary(155, 37, 11); // Media settimanale

        } else if ("Mensile".equals(period)) {
            carbsSeries.getData().add(new XYChart.Data<>("Ottobre", 600));
            proteinsSeries.getData().add(new XYChart.Data<>("Ottobre", 120));
            fatsSeries.getData().add(new XYChart.Data<>("Ottobre", 30));

            carbsSeries.getData().add(new XYChart.Data<>("Novembre", 620));
            proteinsSeries.getData().add(new XYChart.Data<>("Novembre", 125));
            fatsSeries.getData().add(new XYChart.Data<>("Novembre", 32));

            updateSummary(610, 122, 31); // Media mensile
        }

        nutrientChart.getData().addAll(carbsSeries, proteinsSeries, fatsSeries);

        // Imposta i colori delle serie
        setSeriesColor(carbsSeries, Color.web("#4CAF50"));  // Verde per Carboidrati
        setSeriesColor(proteinsSeries, Color.web("#2196F3")); // Blu per Proteine
        setSeriesColor(fatsSeries, Color.web("#FF9800"));    // Arancione per Grassi
    }

    private void updateSummary(int avgCarbs, int avgProteins, int avgFats) {
        carbsLabel.setText("Carboidrati: Ø " + avgCarbs + " g");
        proteinsLabel.setText("Proteine: Ø " + avgProteins + " g");
        fatsLabel.setText("Grassi: Ø " + avgFats + " g");
    }

    private void setSeriesColor(XYChart.Series<String, Number> series, Color color) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: " + toRgbString(color) + ";");
        }
    }

    private String toRgbString(Color color) {
        return "rgb(" + 
            (int) (color.getRed() * 255) + "," + 
            (int) (color.getGreen() * 255) + "," + 
            (int) (color.getBlue() * 255) + ")";
    }
}
