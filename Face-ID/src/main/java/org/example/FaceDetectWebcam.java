package org.example;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.net.URL;
import java.nio.file.Paths;

public class FaceDetectWebcam {
    public static void main(String[] args) {

        OpenCV.loadLocally();
        System.out.println("OpenCV: " + Core.VERSION);

        URL cascadeUrl = FaceDetectWebcam.class.getResource("/haarcascade_frontalface_default.xml");
        if (cascadeUrl == null) { System.err.println("Cascade bulunamadı."); return; }
        String cascadePath;
        try { cascadePath = Paths.get(cascadeUrl.toURI()).toString(); }
        catch (Exception e) { System.err.println("Cascade yolu alınamadı: " + e.getMessage()); return; }

        CascadeClassifier faceCascade = new CascadeClassifier(cascadePath);
        if (faceCascade.empty()) { System.err.println("Cascade yüklenemedi."); return; }

        URL smileUrl = FaceDetectWebcam.class.getResource("/haarcascade_smile.xml");
        if (smileUrl == null) { System.err.println("haarcascade_smile.xml bulunamadı (resources'a ekleyin)."); return; }
        String smilePath;
        try { smilePath = Paths.get(smileUrl.toURI()).toString(); }
        catch (Exception e) { System.err.println("Smile cascade yolu alınamadı: " + e.getMessage()); return; }

        CascadeClassifier smileCascade = new CascadeClassifier(smilePath);
        if (smileCascade.empty()) { System.err.println("Smile cascade yüklenemedi."); return; }

        VideoCapture cap = new VideoCapture(0);
        if (!cap.isOpened()) { System.err.println("Kamera açılamadı."); return; }

        Mat frame = new Mat(), gray = new Mat();
        while (true) {
            if (!cap.read(frame) || frame.empty()) break;


            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(gray, gray);

            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(gray, faces, 1.1, 5, 0, new Size(30,30), new Size());

            for (Rect r : faces.toArray()) {

                Mat faceROI = gray.submat(r);

                MatOfRect smiles = new MatOfRect();

                smileCascade.detectMultiScale(
                        faceROI, smiles,
                        1.7,
                        22,
                        0,
                        new Size(25,25), // minSize
                        new Size()
                );

                boolean smiling = smiles.toArray().length > 0;
                Scalar color = smiling ? new Scalar(0, 0, 255) : new Scalar(0, 255, 0); // kırmızı: gülüyor, yeşil: algılanan normal yüz rengi

                Imgproc.rectangle(frame, new Point(r.x, r.y),
                        new Point(r.x + r.width, r.y + r.height),
                        color, 2);


                Imgproc.putText(frame, smiling ? "smile" : "face",
                        new Point(r.x, Math.max(0, r.y - 5)),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, color, 1);

                faceROI.release();
            }

            HighGui.imshow("Webcam - Face Detection (q ile çık)", frame);
            int key = HighGui.waitKey(1);
            if (key == 'q' || key == 27) break;
        }

        cap.release();
        HighGui.destroyAllWindows();
    }
}