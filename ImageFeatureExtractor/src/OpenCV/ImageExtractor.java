package OpenCV;

import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGet2D;

public class ImageExtractor {
	public float[] extract(String imagePath) {
		
		// Características do Homer e Bart
        float laranjaCamisaBart, azulCalcaoBart, azulSapatoBart;
        float azulCalcaHomer, marromBocaHomer, cinzaSapatoHomer;
        
        laranjaCamisaBart = 0;
        azulCalcaoBart = 0;
        azulSapatoBart = 0;
        azulCalcaHomer = 0;
        marromBocaHomer = 0; 
        cinzaSapatoHomer = 0;
        
        double red, green, blue;
        float[] features = new float[6];
        
        IplImage image = cvLoadImage(imagePath);
        
        if(image == null)
        	return null;
        
     // Varre a imagem pixel a pixel
        for (int altura = 0; altura < image.height(); altura++) {
            for (int largura = 0; largura < image.width(); largura++) {
                
                // Extração do RGB de cada pixel da imagem
                CvScalar scalarExtraiRgb = cvGet2D(image, altura, largura);
                blue = scalarExtraiRgb.val(0);
                green = scalarExtraiRgb.val(1);
                red = scalarExtraiRgb.val(2);

                // Camisa laranja do Bart                    
                if (blue >= 11 && blue <= 22 && 
                        green >= 85 && green <= 105 && 
                        red >= 240 && red <= 255) {                       
                    laranjaCamisaBart++;
                }

                // Calção azul do Bart (metade de baixo da imagem)
                if (altura > (image.height() / 2)) {
                    if (blue >= 125 && blue <= 170 && green >= 0 && green <= 12 && red >= 0 && red <= 20) {
                        azulCalcaoBart++;
                    }
                }

                // Sapato do Bart (parte inferior da imagem)
                if (altura > (image.height() / 2) + (image.height() / 3)) {
                    if (blue >= 125 && blue <= 140 && green >= 3 && green <= 12 && red >= 0 && red <= 20) {
                        azulSapatoBart++;
                    }
                }

                // Calça azul do Homer
                if (blue >= 150 && blue <= 180 && green >= 98 && green <= 120 && red >= 0 && red <= 90) {
                    azulCalcaHomer++;
                }

                // Boca do Homer (pouco mais da metade da imagem)
                if (altura < (image.height() / 2) + (image.height() / 3)) {
                    if (blue >= 95 && blue <= 140 && green >= 160 && green <= 185 && red >= 175 && red <= 200) {	
                        marromBocaHomer++;
                    }
                }

                // Sapato do Homer (parte inferior da imagem)
                if (altura > (image.height() / 2) + (image.height() / 3)) {
                    if (blue >= 25 && blue <= 45 && green >= 25 && green <= 45 && red >= 25 && red <= 45) {
                        cinzaSapatoHomer++;
                    }
                }
            }
        }
        
        // Normaliza as características pelo número de pixels totais da imagem
        laranjaCamisaBart = (laranjaCamisaBart / (image.height() * image.width())) * 100;
        azulCalcaoBart = (azulCalcaoBart / (image.height() * image.width())) * 100;
        azulSapatoBart = (azulSapatoBart / (image.height() * image.width())) * 100;
        azulCalcaHomer = (azulCalcaHomer / (image.height() * image.width())) * 100;
        marromBocaHomer = (marromBocaHomer / (image.height() * image.width())) * 100;
        cinzaSapatoHomer = (cinzaSapatoHomer / (image.height() * image.width())) * 100;
        
        features[0] = laranjaCamisaBart;
        features[1] = azulCalcaoBart;
        features[2] = azulSapatoBart;
        features[3] = marromBocaHomer;
        features[4] = azulCalcaHomer;
        features[5] = cinzaSapatoHomer;
        
		return features;
	}
}
