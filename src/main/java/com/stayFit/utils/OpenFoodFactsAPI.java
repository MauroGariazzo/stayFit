package com.stayFit.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.stayFit.models.Product;

import javafx.scene.image.Image;

public class OpenFoodFactsAPI {

    public static List<Product> search(String name) throws Exception {
        // URL per la ricerca del prodotto
        URL url = new URL("https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + name + "&search_simple=1&json=1&page_size=20");

        System.out.println("URL generato: " + url); // Stampa l'URL per debugging

        // Apertura della connessione
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000); // Timeout di connessione di 10 secondi
        connection.setReadTimeout(10000);    // Timeout di lettura di 10 secondi
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Controllo del codice di risposta
        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new Exception("Invalid URL: Risorsa non trovata per l'URL: " + url);
        } 
        else if (responseCode != 200) {
            throw new Exception("Errore del server: codice " + responseCode);
        }

        // Lettura della risposta dal server
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // Debug: stampa la risposta JSON ricevuta
        System.out.println("Risposta JSON: " + content.toString());

        // Parsing della risposta JSON
        JSONObject jsonResponse = new JSONObject(content.toString());
        JSONArray productsArray = jsonResponse.optJSONArray("products");

        if (productsArray == null || productsArray.isEmpty()) {
            throw new Exception("Nessun prodotto trovato per la ricerca: " + name);
        }

        // Creazione della lista di prodotti
        List<Product> productList = new ArrayList<>();

        // Iterazione su ogni elemento dell'array di prodotti
        for (int i = 0; i < productsArray.length(); i++) {
            JSONObject productData = productsArray.getJSONObject(i);

            // Recupera l'URL dell'immagine, se presente
            String imageUrl = productData.optString("image_url", null);
            
            JSONObject nutriments = productData.optJSONObject("nutriments");

            double calories = nutriments != null ? nutriments.optDouble("energy-kcal_100g", 0) : 0;
            double proteins = nutriments != null ? nutriments.optDouble("proteins_100g", 0) : 0;
            double fats = nutriments != null ? nutriments.optDouble("fat_100g", 0) : 0;
            double carbs = nutriments != null ? nutriments.optDouble("carbohydrates_100g", 0) : 0;
            double sugars = nutriments != null ? nutriments.optDouble("sugars_100g", 0) : 0;
            double salt = nutriments != null ? nutriments.optDouble("salt_100g", 0) : 0;
            
            Product product = new Product(
                productData.optString("product_name", "Nome non disponibile"),
                productData.optString("brands", "Marca non disponibile"),
                productData.optString("categories", "Categoria non disponibile"),
                calories,
                proteins,
                fats,
                carbs,
                sugars,
                salt,
                imageUrl != null ? new Image(imageUrl) : new Image("icons/question.png")
            );

            
            //sendToDB
            productList.add(product);
        }

        // Restituisce la lista di prodotti
        return productList;
    }
}
