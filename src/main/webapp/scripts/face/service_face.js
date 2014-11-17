'use strict';

ntipafacerecognizerApp.factory('Face', function ($resource) {
	return $resource('app/rest/faces/:id/:action', {}, {
		'query': { method: 'GET', isArray: true},
		'get': { method: 'GET'},
		'train': { method: 'PUT'},
		'verify': { method: 'POST',isArray: false,  params: { 'action':'verify'}}	
	});
});

ntipafacerecognizerApp.service('CameraService', function($timeout, $log) {
	var mediaStream;
	var shade = 0.99;
	var consentito = false;
	var video, $video, hiddenCanvas, overlayCanvas, click;


	this.photo = function () {
		$log.debug("$video");
		$log.debug($video);
		
		hiddenCanvas.width = $video.width();
		hiddenCanvas.height = $video.height();

		var ctx = hiddenCanvas.getContext('2d');
		ctx.translate(hiddenCanvas.width, 0);
        ctx.scale(-1, 1);

		ctx.drawImage(video, 0, 0, hiddenCanvas.width, hiddenCanvas.height);
		return hiddenCanvas.toDataURL('image/png');
	};

	this.activateWebcam = function (errorCallback) {

//		console.log("this.consentito:"+this.consentito);
		navigator.getUserMedia = (
				navigator.getUserMedia ||
				navigator.webkitGetUserMedia ||
				navigator.mozGetUserMedia ||
				navigator.msGetUserMedia
		);

		if (!navigator.getUserMedia) {
			errorCallback();
			return;
		}
		init();


		if (!this.consentito){
			this.consentito = true;
			navigator.getUserMedia (
					{
						video: true,
						audio: false
					},
					function(stream) {
						if (navigator.mozGetUserMedia) {
							video.mozSrcObject = stream;
						} else {
							var vendorURL = window.URL || window.webkitURL;
							video.src = vendorURL.createObjectURL(stream);
						}
						video.play();
						video.style.cssText = "-moz-transform: scale(-1, 1); \
							-webkit-transform: scale(-1, 1); -o-transform: scale(-1, 1); \
							transform: scale(-1, 1); filter: FlipH;";
					},
					errorCallback
			);
		}
	}; 

	var init = function () {
		video = document.getElementById('video');
		$video = $(video);
		video.width = $video.parent().width();
		hiddenCanvas = document.getElementById('hidden-canvas');
		overlayCanvas = document.getElementById('canvas-overlay');
	};

});

window.pb = window.pb || {};

function Photo(opts, $timeout) {

	var defaults = {
			'images': 1,
			'canvas': 'canvas',
			'width': 1024,
			'height': 768
	};

	var margins = {
			frame: {
				left: 15,
				top: 80
			},
			image: {
				left: 15,
				top: 40
			}
	};

	opts = defaults;

	var images = [],
	numImages = opts['images'],
	ctx, bgCtx;

	this.images = images;


	this.addPhoto = function (photo) {
		images.push(photo);
	};

	this.spaces = function () {
		return numImages;
	};

	this.first = function () {
		return images.length === 0;
	};


	this.render = function (context) {
		context = context || ctx;
		console.log("numImages:"+numImages);

	};

}
pb.Photo = Photo;


var Views = {

		BOOTH: 'booth',
		PHOTO: 'photo'

};
pb.Views = Views;
