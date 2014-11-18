package com.ipublic.ntipa.facerecognizer.service;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import nu.pattern.OpenCV;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;

import com.ipublic.ntipa.facerecognizer.domain.Face;

@Service
public class FaceDetectService {

	private static final int SIZE = 100;

	public FaceDetectService() {

	}

	public static final String FOTOCACHE = "fotocache";

	public File resizeImage(File img, Face face) throws IOException {

		File fileDetect = null;
		OpenCV.loadLibrary();
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		CascadeClassifier faceDetector = new CascadeClassifier(Thread.class.getResource(
				"/haarcascade_frontalface_alt.xml").getPath());

		Mat image = Highgui.imread(img.getAbsolutePath());

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		Random r = new  Random();
		
		for (Rect rect : faceDetections.toArray()) {
			Mat dst = new Mat(image, rect);
			Mat res = new Mat();
			Size dsize = new Size(SIZE, SIZE);
			Imgproc.resize(dst, res, dsize);

			if (face.getId() == null) {
				fileDetect =new File( FOTOCACHE + "/" + "testimage_detect" + r.nextInt()+ ".png");
			} else {
				fileDetect = new File(FOTOCACHE + "/" + face.getId() + "_detect.png");
			}

			Highgui.imwrite(fileDetect.getAbsolutePath(), res);
		}
		return fileDetect;
	}

	public File createBlackImage() throws IOException {

		// Let's create a BufferedImage for a binary image.
		BufferedImage im = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_BYTE_BINARY);
		// We need its raster to set the pixels' values.
		WritableRaster raster = im.getRaster();
		// Put the pixels on the raster. Note that only values 0 and 1 are used
		// for the pixels.
		// You could even use other values: in this type of image, even values
		// are black and odd
		// values are white.
		for (int h = 0; h < SIZE; h++)
			for (int w = 0; w < SIZE; w++)
				if (((h / 50) + (w / 50)) % 2 == 0)
					raster.setSample(w, h, 0, 0); // checkerboard pattern.
				else
					raster.setSample(w, h, 0, 1);

		File fileDetect = File.createTempFile("testimage_detect", ".png");

		ImageIO.write(im, "PNG", fileDetect);

		return fileDetect;
	}
}
