package com.volkmer.godinho.core.util.imagem;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Redimensionar {

	public static final int IMAGE_PNG = 1;

	public byte[] fazer(byte[] imageByte, int largura, int altura) throws IOException {
	
		int type = Redimensionar.IMAGE_PNG;
		
		ByteArrayInputStream in = new ByteArrayInputStream(imageByte);
		 
		BufferedImage image = ImageIO.read(in);

		Dimension largestDimension = new Dimension(largura, altura);
		// Original size
		int imageLargura = image.getWidth(null);
		int imageAltura = image.getHeight(null);
		float aspectRation = (float)imageLargura / imageAltura;
		if (imageLargura > largura || imageAltura > altura) {

			if ((float)largestDimension.width / largestDimension.height > aspectRation) {
				largestDimension.width = (int)Math.ceil(largestDimension.height * aspectRation);
			}
			else {
				largestDimension.height = (int)Math.ceil((float)largestDimension.width / aspectRation);
			}

			imageLargura = largestDimension.width;
			imageAltura = largestDimension.height;
		}
		
		BufferedImage imgRedimensionada = this.createBufferedImage(image, type, imageLargura, imageAltura);
		
		return this.imgToBase64String(imgRedimensionada, "png");
		
	}
	
	private byte[] imgToBase64String(final RenderedImage img, final String formatName) {
	  final ByteArrayOutputStream os = new ByteArrayOutputStream();

	  try {
	  
	    ImageIO.write(img, formatName, os);
	    String encodedString = Base64.getEncoder().encodeToString(os.toByteArray());
	    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
	    
	    return decodedBytes;
	    
	  } catch (final IOException ioe) {
	    throw new UncheckedIOException(ioe);
	  }
	  
	}
	
	private BufferedImage createBufferedImage(Image image, int type, int w, int h) {
		type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage bi = new BufferedImage(w, h, type);
		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();
		return bi;
	}
	
}
