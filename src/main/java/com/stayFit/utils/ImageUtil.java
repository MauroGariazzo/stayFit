package com.stayFit.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import java.io.InputStream;

public class ImageUtil {
	
	public static byte[] imageToByteArray(Image image) throws IOException {
		
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bImage, "png", outputStream);
        return outputStream.toByteArray();
    }
    
    public static Image byteArrayToImage(byte[] imageBytes)throws Exception {
    	if (imageBytes == null) {
            System.err.println("imageBytes null");
            return new Image("icons/question.png");
        }

        try {
            InputStream is = new ByteArrayInputStream(imageBytes);
            return new Image(is);
        }
        catch (Exception e) {
            System.err.println("Errore durante la conversione di byte[] a Image: " + e.getMessage());
            return new Image("icons/question.png");
        }
    }
}
