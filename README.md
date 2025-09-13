# 😃 Java OpenCV Face & Smile Detection

Bu proje, **Java + OpenCV** kullanarak **yüz** ve **gülümseme** tespiti yapar.  
- Yüz bulunduğunda kutu **yeşil**  
- Eğer gülümseme algılanırsa kutu **kırmızıya** döner  
- Hem **görüntü dosyası** (input.jpg) hem **webcam** üzerinden çalışır  

---

## 🚀 Özellikler
- Haar Cascade ile yüz algılama  
- ROI üzerinde gülümseme algılama  
- Gerçek zamanlı webcam desteği  
- Maven projesi (kolay kurulum)  

---

## 📂 Proje Yapısı
```
Face-ID/
  ├── src/
  │   └── main/
  │       ├── java/org/example/
  │       │    ├── FaceDetectImage.java
  │       │    └── FaceDetectWebcam.java
  │       └── resources/
  │            ├── haarcascade_frontalface_default.xml
  │            └── haarcascade_smile.xml
  ├── pom.xml
  ├── README.md
  └── .gitignore
```

---

## 🔧 Kurulum
1. Maven bağımlılıklarını indir:
   ```bash
   mvn clean package
   ```

2. Gerekli Haar Cascade dosyalarını `src/main/resources/` içine koy:  
   - [haarcascade_frontalface_default.xml](https://github.com/opencv/opencv/blob/master/data/haarcascades/haarcascade_frontalface_default.xml)  
   - [haarcascade_smile.xml](https://github.com/opencv/opencv/blob/master/data/haarcascades/haarcascade_smile.xml)  

---

## ▶️ Çalıştırma

### Görsel Üzerinde Yüz Algılama
Proje köküne `input.jpg` koy:
```bash
mvn exec:java -Dexec.mainClass="org.example.FaceDetectImage"
```
Çıktı: `output_faces.jpg`

### Webcam Üzerinde Yüz + Gülümseme
```bash
mvn exec:java -Dexec.mainClass="org.example.FaceDetectWebcam"   -Dexec.jvmArgs="--enable-native-access=ALL-UNNAMED"
```
> Webcam açılır, `q` tuşu ile çıkış yapılır.

---

## ⚙️ Parametreler
- Yüz tespiti → `scaleFactor=1.1`, `minNeighbors=5`  
- Gülümseme tespiti → `scaleFactor=1.7`, `minNeighbors=22`  
- `minSize` parametrelerini değiştirerek küçük/büyük yüzleri filtreleyebilirsin  

---

## 📸 Örnek Çalışma
- **Yeşil kutu:** Normal yüz  
- **Kırmızı kutu:** Gülümseyen yüz  

---

## 📜 Lisans
OpenCV Haar Cascade modelleri [OpenCV](https://opencv.org/) tarafından sağlanmaktadır.  
Proje eğitim amaçlıdır.  
