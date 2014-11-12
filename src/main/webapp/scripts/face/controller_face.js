'use strict';

ntipafacerecognizerApp.controller('FaceController', function ($scope, resolvedFace, Face, $log, $translate, CameraService, $timeout) {

	var photo;
	$scope.faces = resolvedFace;
	photo = new pb.Photo({}, $timeout);
	
	$scope.newInsert = false;
	$scope.fotoFatta= false;
	
	$scope.makeSnap = function(){
		$log.debug('CONTROLLO SE HO MIA FOTO');
		$log.debug($scope.face.photo);
		$scope.face.photo = CameraService.photo();
		$scope.fotoFatta= true;
		$scope.newInsert=false;
		$log.debug('STAMPA MIA FOTO');
		$log.debug($scope.face.photo);
	};

	$scope.remakeSnap = function(){
		$scope.newInsert=true;
		$scope.face.photo =null;
		$scope.initCam();
		$log.debug('RESETTO MIA FOTO');
		$log.debug($scope.face.photo);
	};

	$scope.openFace = function() {
		$log.debug("init");
		$scope.newInsert=true;
		$scope.fotoFatta= false;
		$scope.initCam();
	
		$scope.face = {label: null, path: null, id: null, photo: null};
	};


	$scope.initCam = function() {
		CameraService.activateWebcam(
				function (error) {
					console.log('Keine Webcam verfügbar');
				}
		);
	};
	
	$scope.create = function () {
		$log.debug('CREATE');
		$log.debug($scope.faces.photo);
		Face.save($scope.face,
				function () {
			$scope.faces = Face.query();
			$('#saveFaceModal').modal('hide');
			$scope.clear();
		});
	};

	$scope.update = function (id) {
		$scope.face = Face.get({id: id});
		$scope.fotoFatta= true;
		$('#saveFaceModal').modal('show');
 
	};

	$scope.delete = function (id) {
		Face.delete({id: id},
				function () {
			$scope.faces = Face.query();
		});
	};

	$scope.clear = function () {
		$scope.face = {label: null, path: null, id: null};
	};

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
