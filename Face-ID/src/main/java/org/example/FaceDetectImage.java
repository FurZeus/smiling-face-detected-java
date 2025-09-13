package org.example;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.net.URL;
import java.nio.file.Paths;

public class FaceDetectImage {
    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("OpenCV: " + Core.VERSION);

        String inputPath  = "input.jpg";
        String outputPath = "output_faces.jpg";

        URL cascadeUrl = FaceDetectImage.class.getResource("/haarcascade_frontalface_default.xml");
        if (cascadeUrl == null) { System.err.println("Cascade bulunamadı."); return; }
        String cascadePath;
        try { cascadePath = Paths.get(cascadeUrl.toURI()).toString(); }
        catch (Exception e) { System.err.println("Cascade yolu alınamadı: " + e.getMessage()); return; }

        Mat img = Imgcodecs.imread(inputPath);
        if (img.empty()) { System.err.println("Görsel okunamadı: " + inputPath); return; }

        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(gray, gray);

        CascadeClassifier faceCascade = new CascadeClassifier(cascadePath);
        if (faceCascade.empty()) { System.err.println("Cascade yüklenemedi."); return; }

        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(gray, faces, 1.1, 5, 0, new Size(30,30), new Size());

        for (Rect r : faces.toArray()) {
            Imgproc.rectangle(img, new Point(r.x, r.y),
                    new Point(r.x + r.width, r.y + r.height),
                    new Scalar(0,255,0), 2);
        }

        Imgcodecs.imwrite(outputPath, img);
        System.out.println("Tamam: " + outputPath + " | Yüz sayısı: " + faces.toArray().length);
    }
}