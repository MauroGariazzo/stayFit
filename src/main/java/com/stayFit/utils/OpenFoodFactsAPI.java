package com.stayFit.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.JSONArray;
import org.json.JSONObject;

import com.stayFit.models.Product;
import com.stayFit.product.ProductDAO;
import com.stayFit.product.ProductGetResponseDTO;
import com.stayFit.product.ProductCreateRequestDTO;
import com.stayFit.registration.RegistrationUserCredentialsDAO;
import com.stayFit.repository.DBConnector;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class OpenFoodFactsAPI {

    // classe interna per gestire lo stato di inizializzazione di JavaFX
    private static class JavaFXInitializer {
        static AtomicBoolean initialized = new AtomicBoolean(false);
    }

    private static void initializeJavaFX() {
        // verifico se JavaFX è già stato inizializzato
        if (!JavaFXInitializer.initialized.get()) {
            final CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                latch.countDown();
            });
            try {
                latch.await();
                JavaFXInitializer.initialized.set(true);
            } 
            catch (InterruptedException e) {
                throw new RuntimeException("Errore durante l'inizializzazione di JavaFX", e);
            }
        }
    }

    public static List<ProductGetResponseDTO> search(String name) throws Exception {
        //initializeJavaFX(); // Inizializza JavaFX

        // URL per la ricerca del prodotto
        String encodedName = URLEncoder.encode(name, "UTF-8");
        URL url = new URL("https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + 
            encodedName + "&search_simple=1&json=1&page_size=50");

        // Apertura connessione
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

        // Parsing della risposta JSON
        JSONObject jsonResponse = new JSONObject(content.toString());
        JSONArray productsArray = jsonResponse.optJSONArray("products");

        if (productsArray == null || productsArray.isEmpty()) {
            throw new Exception("Nessun prodotto trovato per la ricerca: " + name);
        }

        // Creazione della lista di prodotti
        List<ProductGetResponseDTO> productList = new ArrayList<>();

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
            
            ProductGetResponseDTO product = new ProductGetResponseDTO();
            product.productName = productData.optString("product_name", "Nome non disponibile");
            if(product.productName.length() > 50) {
            	product.productName = product.productName.substring(0,50);
            }
            product.brand = productData.optString("brands", "Marca non disponibile");
            if(product.brand.length() > 60) {
            	product.brand = product.brand.substring(0,60);
            }
            /*product.category = productData.optString("categories", "Categoria non disponibile");
            if(product.category.length() > 50) {
            	product.category = product.category.substring(0,50);
            }*/
            product.category = name;
            product.calories = calories;
            product.proteins = proteins;
            product.fats = fats;
            product.carbs = carbs;
            product.sugars = sugars;
            product.salt = salt;
            
            if(imageUrl != null && !imageUrl.isEmpty()) {
                try {                   
                    product.productImage = new Image(imageUrl, false);
                } 
                catch (Exception e) {
                    System.out.println("Errore nel caricamento dell'immagine: " + imageUrl);
                    product.productImage = new Image("icons/question.png");
                }
            }
            else {
                product.productImage = new Image("icons/question.png");
            }

            
            productList.add(product);
        }
        return productList;
    }
    
    public static void main(String[] args) {
        //Inizializzione JavaFX
        initializeJavaFX();

        String[] types = {
            "pane", "pasta", "carne", "bevande", "acqua", "frutta", "verdura", "pesce", "birra", "vino"};
        

        ProductDAO pd = new ProductDAO(new DBConnector());
        try {
            for(String type : types) {
            	System.out.println(type);
                List<ProductGetResponseDTO> products = search(type);
                Thread.sleep(6000);
                
                for(ProductGetResponseDTO product : products) {
                	ProductCreateRequestDTO pc = new ProductCreateRequestDTO();
                	pc.productName = product.productName;
                	pc.brand = product.brand;
                	pc.category = product.category;
                	pc.calories = product.calories;
                	pc.carbs = product.carbs;
                	pc.fats = product.fats;
                	pc.sugars = product.sugars;
                	pc.proteins = product.proteins;
                	pc.salt = product.salt;
                	pc.productImage = product.productImage;
                    pd.insert(pc);
                }                
            }
        }
        catch(Exception ex) {
            System.out.println("Errore: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }
        finally {
        	System.out.println("Spacciau");
        }
    }
}
