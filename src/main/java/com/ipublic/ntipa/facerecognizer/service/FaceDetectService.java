package com.ipublic.ntipa.facerecognizer.service;

import java.io.File;
import java.io.IOException;

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
	
	public FaceDetectService() {

	}
	
	public static final String FOTOCACHE = "fotocache";
	
	public File resizeImage(File img, Face face) throws IOException {

		File fileDetect=null;
		OpenCV.loadLibrary();
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     
        CascadeClassifier faceDetector = new CascadeClassifier(Thread.class.getResource( "/haarcascade_frontalface_alt.xml" ).getPath()	 );
               
        Mat image = Highgui.imread(img.getAbsolutePath() );

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        
        for (Rect rect : faceDetections.toArray()) {
            Mat dst =  new Mat(image,rect);
            Mat res =  new Mat( );
	        Size dsize = new Size(100,100);
	    	Imgproc.resize(dst, res, dsize);
          
    	    if(face.getId() == null){
    			fileDetect = File.createTempFile("testimage_detect", ".png");
    		}else{
    			fileDetect = new File(FOTOCACHE + "/" + face.getId() + "_detect.png");
    		}

	        Highgui.imwrite(fileDetect.getAbsolutePath(), res);
        }
        return fileDetect;
    }
}
