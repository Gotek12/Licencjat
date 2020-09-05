package pl.wladyga.features.photo.comparison;

import java.awt.image.BufferedImage;

public class ComparisonImages {

    public static double analiseImagesByColor(BufferedImage imgA, BufferedImage imgB) {

        double percentageResult;
        int w1 = imgA.getWidth();
        int w2 = imgB.getWidth();
        int h1 = imgA.getHeight();
        int h2 = imgB.getHeight();

        if ((w1 != w2) || (h1 != h2))
            return -1.0;
        else {
            long diff = 0;
            for (int i = 0; i < h1; i++) {
                for (int j = 0; j < w1; j++) {
                    int rgbA = imgA.getRGB(j, i);
                    int rgbB = imgB.getRGB(j, i);

                    int rA = (rgbA >> 16) & 0xff;
                    int gA = (rgbA >> 8) & 0xff;
                    int bA = (rgbA) & 0xff;

                    int rB = (rgbB >> 16) & 0xff;
                    int gB = (rgbB >> 8) & 0xff;
                    int bB = (rgbB) & 0xff;

                    diff += Math.abs(rA - rB);
                    diff += Math.abs(gA - gB);
                    diff += Math.abs(bA - bB);
                }
            }

            double allRGBPixels = w1 * h1 * 3;
            double avg = diff / allRGBPixels;
            percentageResult = (avg / 255) * 100;
        }

        return percentageResult;
    }
}
