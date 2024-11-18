package com.stayFit.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stayFit.enums.NutrientsReportInterval;
import com.stayFit.nutrientsReport.NutrientsDailyReportGetResponseDTO;
import com.stayFit.nutrientsReport.NutrientsMonthlyReportGetResponseDTO;
import com.stayFit.nutrientsReport.NutrientsReportController;
import com.stayFit.nutrientsReport.NutrientsReportDAO;
import com.stayFit.nutrientsReport.NutrientsReportGetResponseDTO;
import com.stayFit.nutrientsReport.NutrientsReportGetUseCase;
import com.stayFit.nutrientsReport.NutrientsWeeklyReportGetResponseDTO;
import com.stayFit.repository.DBConnector;
import com.stayFit.weightReport.WeightReportController;
import com.stayFit.weightReport.WeightReportGetUseCase;
import com.stayFit.weightReport.WeightReportDAO;
import com.stayFit.weightReport.WeightReportGetResponseDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ReportStage {
	private int user_fk;
	private StackedBarChart<String, Number> nutrientChart;
	private Label carbsLabel, proteinsLabel, fatsLabel;
	private List<WeightReportGetResponseDTO> weightResponse = new ArrayList<>();
	private Map<NutrientsReportInterval, List<NutrientsReportGetResponseDTO>> nutrients = new HashMap<>();

	public ReportStage(int user_fk) {
		this.user_fk = user_fk;

		try {
			WeightReportController weightReport = new WeightReportController(
					new WeightReportGetUseCase(new WeightReportDAO(new DBConnector())));
			this.weightResponse = weightReport.getWeightReport(user_fk);

			NutrientsReportController nutrientsReport = new NutrientsReportController(
					new NutrientsReportGetUseCase(new NutrientsReportDAO(new DBConnector())));
			nutrients = nutrientsReport.get(user_fk);
		} catch (Exception ex) {
			showAlert(ex.getMessage(), Alert.AlertType.WARNING);
		}
	}

	public StackPane createReportContent() {
		// GRAFICO DEL PESO 
		LineChart<String, Number> weightChart = createWeightChart();

		// SEZIONE NUTRIENTI 
		VBox nutrientReport = createNutrientReport();

		// LAYOUT GENERALE
		VBox content = new VBox(20);
		content.setAlignment(Pos.TOP_CENTER);
		content.setPadding(new Insets(20));
		content.getChildren().addAll(weightChart, nutrientReport);

		// Contenitore principale
		StackPane reportPane = new StackPane(content);
		reportPane.setPrefHeight(800);

		return reportPane;
	}

	private LineChart<String, Number> createWeightChart() {
		// Assi del grafico del peso
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Tempo (date)");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Peso (kg)");

		// Creazione del grafico del peso
		LineChart<String, Number> weightChart = new LineChart<>(xAxis, yAxis);
		weightChart.setTitle("Andamento del Peso nel Tempo");
		weightChart.setPrefHeight(300);

		// Serie di dati
		XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();
		weightSeries.setName("Peso");

		// Iterazione sui dati di weightResponse
		for (WeightReportGetResponseDTO entry : weightResponse) {
			String dateLabel = entry.dateRegistration.toString(); // Usa la data come stringa
			double weight = entry.weight;

			// Aggiungi dati alla serie
			weightSeries.getData().add(new XYChart.Data<>(dateLabel, weight));
		}

		// Aggiungi la serie di dati al grafico
		weightChart.getData().add(weightSeries);

		return weightChart;
	}

	private VBox createNutrientReport() {
		// ComboBox per selezione periodo
		ComboBox<String> periodComboBox = new ComboBox<>();
		periodComboBox.getItems().addAll("Giornaliero", "Settimanale", "Mensile");
		periodComboBox.setValue("Giornaliero"); // Valore predefinito

		periodComboBox.setOnAction(e -> {
			String selectedPeriod = periodComboBox.getValue();
			updateNutrientData(selectedPeriod);
		});

		// Etichette per riepilogo nutrienti
		carbsLabel = new Label("Carboidrati: -");
		proteinsLabel = new Label("Proteine: -");
		fatsLabel = new Label("Grassi: -");

		VBox summaryBox = new VBox(10, carbsLabel, proteinsLabel, fatsLabel);
		summaryBox.setAlignment(Pos.CENTER_LEFT);

		// Grafico nutrienti
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

		// Creazione delle serie
		XYChart.Series<String, Number> carbsSeries = new XYChart.Series<>();
		carbsSeries.setName("Carboidrati");

		XYChart.Series<String, Number> proteinsSeries = new XYChart.Series<>();
		proteinsSeries.setName("Proteine");

		XYChart.Series<String, Number> fatsSeries = new XYChart.Series<>();
		fatsSeries.setName("Grassi");

		// Aggiungi i dati alle serie
		if ("Giornaliero".equals(period)) {
			List<NutrientsReportGetResponseDTO> dailyNutrientsList = nutrients.get(NutrientsReportInterval.GIORNALIERO);
			if (dailyNutrientsList != null) {
				for (NutrientsReportGetResponseDTO nutrient : dailyNutrientsList) {
					NutrientsDailyReportGetResponseDTO dailyNutrients = (NutrientsDailyReportGetResponseDTO) nutrient;
					String date = LocalDate.of(dailyNutrients.year, dailyNutrients.month, dailyNutrients.day)
							.toString();
					
					carbsSeries.getData().add(new XYChart.Data<>(date, nutrient.carbs));
					proteinsSeries.getData().add(new XYChart.Data<>(date, nutrient.proteins));
					fatsSeries.getData().add(new XYChart.Data<>(date, nutrient.fats));
				}
				// Calcola e aggiorna il riepilogo
				updateSummary(calculateAverage(dailyNutrientsList, "carbs"),
						calculateAverage(dailyNutrientsList, "proteins"), calculateAverage(dailyNutrientsList, "fats"));
			}
		}

		else if ("Settimanale".equals(period)) {
			List<NutrientsReportGetResponseDTO> weeklyNutrientsList = nutrients
					.get(NutrientsReportInterval.SETTIMANALE);
			
			if (weeklyNutrientsList != null) {
				for (NutrientsReportGetResponseDTO nutrient : weeklyNutrientsList) {
					NutrientsWeeklyReportGetResponseDTO weeklyNutrients = (NutrientsWeeklyReportGetResponseDTO) nutrient;
					String date = String.format("Settimana: %d - Anno: %d", weeklyNutrients.week, weeklyNutrients.year);

					carbsSeries.getData().add(new XYChart.Data<>(date, nutrient.carbs));
					proteinsSeries.getData().add(new XYChart.Data<>(date, nutrient.proteins));
					fatsSeries.getData().add(new XYChart.Data<>(date, nutrient.fats));
				}
				updateSummary(calculateAverage(weeklyNutrientsList, "carbs"),
	                    calculateAverage(weeklyNutrientsList, "proteins"),
	                    calculateAverage(weeklyNutrientsList, "fats"));
			}
		}

		else if ("Mensile".equals(period)) {
			List<NutrientsReportGetResponseDTO> monthlyNutrientsList = nutrients
					.get(NutrientsReportInterval.MENSILE);
			
			if (monthlyNutrientsList != null) {
				for (NutrientsReportGetResponseDTO nutrient : monthlyNutrientsList) {
					NutrientsMonthlyReportGetResponseDTO monthlyNutrients = (NutrientsMonthlyReportGetResponseDTO) nutrient;
					String date = String.format("Mese: %d - Anno: %d", monthlyNutrients.month, monthlyNutrients.year);
					
					carbsSeries.getData().add(new XYChart.Data<>(date, nutrient.carbs));
					proteinsSeries.getData().add(new XYChart.Data<>(date, nutrient.proteins));
					fatsSeries.getData().add(new XYChart.Data<>(date, nutrient.fats));
				}
				updateSummary(calculateAverage(monthlyNutrientsList, "carbs"),
	                    calculateAverage(monthlyNutrientsList, "proteins"),
	                    calculateAverage(monthlyNutrientsList, "fats"));
			}			
		}

		// Aggiungi le serie al grafico
		nutrientChart.getData().addAll(fatsSeries, proteinsSeries, carbsSeries);

		// Assegna le classi CSS alle serie
		setSeriesStyleClass(carbsSeries, "carbs-series");
		setSeriesStyleClass(proteinsSeries, "proteins-series");
		setSeriesStyleClass(fatsSeries, "fats-series");

		// Carica il file CSS se non è già stato caricato
		if (nutrientChart.getScene() != null && nutrientChart.getScene().getStylesheets().isEmpty()) {
			nutrientChart.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		}
	}

	private void setSeriesStyleClass(XYChart.Series<String, Number> series, String styleClass) {
		// Aggiungi un listener alla proprietà node della serie
		series.nodeProperty().addListener((obs, oldNode, newNode) -> {
			if (newNode != null) {
				newNode.getStyleClass().add(styleClass);
			}
		});
	}

	private int calculateAverage(List<NutrientsReportGetResponseDTO> list, String nutrientType) {
		double sum = 0;
		int count = 0;
		for (NutrientsReportGetResponseDTO dto : list) {
			switch (nutrientType) {
			case "carbs":
				sum += dto.carbs;
				break;
			case "proteins":
				sum += dto.proteins;
				break;
			case "fats":
				sum += dto.fats;
				break;
			}
			count++;
		}
		return count > 0 ? (int) (sum / count) : 0;
	}

	private void updateSummary(int avgCarbs, int avgProteins, int avgFats) {
		carbsLabel.setText("Carboidrati: Ø " + avgCarbs + " g");
		proteinsLabel.setText("Proteine: Ø " + avgProteins + " g");
		fatsLabel.setText("Grassi: Ø " + avgFats + " g");
	}

	/*private String toRgbString(Color color) {
		return "rgb(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + ","
				+ (int) (color.getBlue() * 255) + ")";
	}*/

	private void showAlert(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Attenzione");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
