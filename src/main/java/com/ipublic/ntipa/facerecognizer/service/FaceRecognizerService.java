package com.ipublic.ntipa.facerecognizer.service;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.bytedeco.javacpp.opencv_contrib;
import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.ipublic.ntipa.facerecognizer.domain.Face;
import com.ipublic.ntipa.facerecognizer.repository.FaceRepository;

/**
 * Service class for managing users.
 */
@Service
public class FaceRecognizerService {

	private static final String FOTOCACHE = "fotocache";

	private final Logger log = LoggerFactory
			.getLogger(FaceRecognizerService.class);

	private FaceRecognizer faceRecognizer = opencv_contrib
			.createFisherFaceRecognizer();

	@Inject
	private FaceRepository faceRepository;

	private static final String CATALOGAZIONE_OPENCV = "catalogazione_opencv";

	public FaceRecognizerService() {

	}

	@PostConstruct
	public void startup() {
		log.debug("startup...");
		File file = new File(CATALOGAZIONE_OPENCV);
		if (file.exists()) {
			faceRecognizer.load(file.getAbsolutePath());
		}

		File cacheDir = new File(FOTOCACHE);
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}
	}

	@PreDestroy
	public void shutdown() {
		log.debug("shutdown...");
		File file = new File(CATALOGAZIONE_OPENCV);
		faceRecognizer.save(file.getAbsolutePath());

	}

	private File convertFaceToFile(Face face) throws IOException {

		String fotoCorrente = face.getPhoto();
		fotoCorrente = fotoCorrente.substring(22);
		byte[] imgByteArray = Base64.decode(fotoCorrente.getBytes());

		InputStream in = new ByteArrayInputStream(imgByteArray);
		BufferedImage bufferedImage = ImageIO.read(in);

		File file = new File(FOTOCACHE + "/" + face.getId() + ".png");
		ImageIO.write(bufferedImage, "png", file);

		return file;

	}

	/**
	 * Inserimento face nella sistema di classificazione
	 * 
	 * @param face
	 * @throws IOException
	 */
	public void train(Face face) {
		try {
			File image;

			image = convertFaceToFile(face);

			Mat labels = new Mat(1, 1, CV_32SC1);
			IntBuffer labelsBuf = labels.getIntBuffer();
			MatVector images = new MatVector(1);
			String filename = image.getAbsolutePath();
			Mat box_face = imread(filename, CV_LOAD_IMAGE_GRAYSCALE);
			Integer label = face.getCount();
			log.debug("label:" + label);
			images.put(0, box_face);
			labelsBuf.put(0, label);
			faceRecognizer.train(images, labels);
		} catch (IOException e) {
			log.error("train", e);
		}

	}

	/**
	 * Inserimento face nella sistema di classificazione
	 * 
	 * @param face
	 * @throws IOException
	 */
	public void trainAll() {
		try {

			List<Face> faces = faceRepository.findAll();
			
				Mat labels = new Mat(faces.size(), 1, CV_32SC1);
				IntBuffer labelsBuf = labels.getIntBuffer();

				MatVector images = new MatVector(faces.size() );

				for (Face face : faces) {
					File image = convertFaceToFile(face);
					String filename = image.getAbsolutePath();
					Mat box_face = imread(filename, CV_LOAD_IMAGE_GRAYSCALE);
					Integer label = face.getCount();
					images.put(label, box_face);
					labelsBuf.put(label, label);
				}
				faceRecognizer.train(images, labels);
			 

		} catch (IOException e) {
			log.error("train", e);
		}

	}

	/**
	 * Cerca faccia nel sistema di catalogazione
	 * 
	 * @param faceDaVerificare
	 * @return
	 * @throws IOException
	 */
	public Integer predict(Face faceDaVerificare) throws IOException {
		File fileImageTest = convertFaceToFile(faceDaVerificare);
		Mat testImage = imread(fileImageTest.getAbsolutePath(),
				CV_LOAD_IMAGE_GRAYSCALE);
		int predictedLabel = faceRecognizer.predict(testImage);
		System.out.println("Predicted label: " + predictedLabel);
		log.debug("Predicted label trovata: " + predictedLabel);

		return predictedLabel;
	}

}
