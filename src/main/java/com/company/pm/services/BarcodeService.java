package com.company.pm.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.math3.linear.MatrixUtils;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class BarcodeService {

    public byte[] generateBarcode(String text) {
        EAN13Bean generator = new EAN13Bean();
        BitmapCanvasProvider canvas =
            new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        generator.generateBarcode(canvas, text);
        BufferedImage bufferedImage = canvas.getBufferedImage();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

    public byte[] generateQRCode(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BitMatrix encode = writer.encode(text, BarcodeFormat.QR_CODE, 100, 100);
            int matrixWidth = encode.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);
            // Paint and save the image using the ByteMatrix
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (encode.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, "png", bos);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

}
