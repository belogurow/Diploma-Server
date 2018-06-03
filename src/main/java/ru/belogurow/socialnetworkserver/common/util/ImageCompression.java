package ru.belogurow.socialnetworkserver.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageCompression {
    public static byte[] compress(byte[] data, float imageQuality) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(data);
             OutputStream outputStream = new ByteArrayOutputStream()) {

            Thumbnails.of(inputStream)
                    .scale(0.9)
                    .outputQuality(imageQuality)
                    .toOutputStream(outputStream);

            return ((ByteArrayOutputStream) outputStream).toByteArray();
        }
    }

    // https://stackoverflow.com/questions/5905868/how-to-rotate-jpeg-images-based-on-the-orientation-metadata
    public static BufferedImage rotateImage(BufferedImage image, int orientation) {
        AffineTransform tx = new AffineTransform();

        int width = image.getWidth();
        int height = image.getHeight();

        switch (orientation) {
            case 1:
                break;
            case 2: // Flip X
                tx.scale(-1.0, 1.0);
                tx.translate(-width, 0);
                break;
            case 3: // PI rotation
                tx.translate(width, height);
                tx.rotate(Math.PI);
                break;
            case 4: // Flip Y
                tx.scale(1.0, -1.0);
                tx.translate(0, -height);
                break;
            case 5: // - PI/2 and Flip X
                tx.rotate(-Math.PI / 2);
                tx.scale(-1.0, 1.0);
                break;
            case 6: // -PI/2 and -width
                tx.translate(height, 0);
                tx.rotate(Math.PI / 2);
                break;
            case 7: // PI/2 and Flip
                tx.scale(-1.0, 1.0);
                tx.translate(-height, 0);
                tx.translate(0, width);
                tx.rotate(  3 * Math.PI / 2);
                break;
            case 8: // PI / 2
                tx.translate(0, width);
                tx.rotate(  3 * Math.PI / 2);
                break;
        }

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }
}
