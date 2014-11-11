'use strict';

ntipafacerecognizerApp.controller('FaceController', function ($scope, resolvedFace, Face, $log, CameraService) {
	
	$scope.faces = resolvedFace;
	$scope.hasUserMedia = CameraService.hasUserMedia;
	
	
	// Inside the link function above
		// If the stream works
	var onSuccess = function(stream) {
		var videoElement;

		if (navigator.mozGetUserMedia) {
		    videoElement.mozSrcObject = stream;
		  } else {
		    var vendorURL = window.URL || window.webkitURL;
		    videoElement.src = window.URL.createObjectURL(stream);
		  }
		  // Just to make sure it autoplays
		  videoElement.play();
	}
	// If there is an error
	var onFailure = function(err) {
		console.error(err);
	}

	// Make the request for the media
	navigator.getUserMedia({
		video: {
			mandatory: {
				maxHeight: 25,
				maxWidth: 25
			}
		}, 
		audio: false
	}, onSuccess, onFailure);

	$scope.w = 25;
	$scope.h = 25;
	

//	var _video = null,
//	patData = null;
//	
//	$scope.faces = resolvedFace;
//
//	$scope.onStream = function(stream,video){
//		$log.debug(stream);
//		$log.debug(video);
//
//	};
//
//	$scope.create = function () {
//		Face.save($scope.face,
//				function () {
//			$scope.faces = Face.query();
//			$('#saveFaceModal').modal('hide');
//			$scope.clear();
//		});
//	};
//
//	$scope.update = function (id) {
//		$scope.face = Face.get({id: id});
//		$('#saveFaceModal').modal('show');
//	};
//
//	$scope.delete = function (id) {
//		Face.delete({id: id},
//				function () {
//			$scope.faces = Face.query();
//		});
//	};
//
//	$scope.clear = function () {
//		$scope.face = {label: null, path: null, id: null};
//	};
//
//	$scope.onSuccess = function (video) {
//		$log.debug('SONO ENTRATO in ONSUCCESS');
//		// The video element contains the captured camera data
//		_video = video;
//		$scope.$apply(function() {
//			$scope.patOpts.w = _video.width;
//			$scope.patOpts.h = _video.height;
//			$scope.showDemos = true;
//		});
//	};
//
//	$scope.showDemos = false;
//	$scope.edgeDetection = false;
//	$scope.mono = false;
//	$scope.invert = false;
//
//	$scope.patOpts = {x: 0, y: 0, w: 320, h: 320};
//
//	$scope.webcamError = false;
//	$scope.onError = function (err) {
//		$scope.$apply(
//				function() {
//					$scope.webcamError = err;
//				}
//		);
//	};

	/**
	 * Make a snapshot of the camera data and show it in another canvas.
	 */
//	$scope.makeSnapshot = function makeSnapshot() {
//		$log.debug('SONO ENTRATO in MAKESNAPSHOT');
//		
//		Capture.captureImage(function(image) {
//			  $log.debug('image');
//			  $scope.photo = image;
//	          
//	        }, function(error) {
//	        	$log.debug('error');
//
//	        },{});
		 
		 
		 
//		if (_video) {
//			$log.debug('IL VIDEO NO NULL');
//			var patCanvas = document.querySelector('#snapshot');
////			var patCanvas = document.createElement('canvas');
//			if (!patCanvas) return;
//
//			patCanvas.width = _video.width;
//			patCanvas.height = _video.height;
//			var ctxPat = patCanvas.getContext('2d');
//
//			var idata = getVideoData($scope.patOpts.x, $scope.patOpts.y, $scope.patOpts.w, $scope.patOpts.h);
//			$log.debug('STO PER INSERIRE IMMAGINE');
//			ctxPat.putImageData(idata, 0, 0);
//			$log.debug('IMMAGINE INSERITA');
////			sendSnapshotToServer(patCanvas.toDataURL());
//
//			patData = idata;
//		}
//	};

	/**
	 * Redirect the browser to the URL given.
	 * Used to download the image by passing a dataURL string
	 */
//	$scope.downloadSnapshot = function downloadSnapshot(dataURL) {
//		window.location.href = dataURL;
//	};
//
//	var getVideoData = function getVideoData(x, y, w, h) {
//		var hiddenCanvas = document.createElement('canvas');
//		hiddenCanvas.width = _video.width;
//		hiddenCanvas.height = _video.height;
//		var ctx = hiddenCanvas.getContext('2d');
//		ctx.drawImage(_video, 0, 0, _video.width, _video.height);
//		return ctx.getImageData(x, y, w, h);
//	};

	/**
	 * This function could be used to send the image data
	 * to a backend server that expects base64 encoded images.
	 *
	 * In this example, we simply store it in the scope for display.
	 */
//	var sendSnapshotToServer = function sendSnapshotToServer(imgBase64) {
//		$scope.face.photo = imgBase64;
//	};



});
