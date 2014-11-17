
import java.io.File;

import nu.pattern.OpenCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {

    public static void main(String[] args) {
OpenCV.loadLibrary();
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     
        System.out.println("\nRunning FaceDetector");

        CascadeClassifier faceDetector = new CascadeClassifier(FaceDetector.class.getResource("haarcascade_frontalface_alt.xml").getPath());
        
        File images = new File("/home/gricco/git/ntipa-facerecognizer/trainingDir/001-donna01.jpg");
        
        Mat image = Highgui
                .imread(images.getAbsolutePath() );

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        int i=0;
        
        for (Rect rect : faceDetections.toArray()) {
            Mat dst =  new Mat(image,rect);
            Mat res =  new Mat( );
	        Size dsize = new Size(100,100);
	    	Imgproc.resize(dst, res, dsize);
    	    i++;
          
	    	String filenamer = i+"_r_ouput.png";
	        System.out.println(String.format("Writing %s", filenamer));
	        Highgui.imwrite(filenamer, res);
            
        }
        


        
        
    }
}