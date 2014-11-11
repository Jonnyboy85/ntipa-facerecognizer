'use strict';

ntipafacerecognizerApp.factory('Face', function ($resource) {
	return $resource('app/rest/faces/:id', {}, {
		'query': { method: 'GET', isArray: true},
		'get': { method: 'GET'}
	});
});

ntipafacerecognizerApp.factory('CameraService', function($window) {
	var hasUserMedia = function() {
		return !!getUserMedia();
	}
	var getUserMedia = function() {
		navigator.getUserMedia = ($window.navigator.getUserMedia || 
				$window.navigator.webkitGetUserMedia ||
				$window.navigator.mozGetUserMedia || 
				$window.navigator.msGetUserMedia);
		return navigator.getUserMedia;
	}
	return {
		hasUserMedia: hasUserMedia(),
		getUserMedia: getUserMedia
	}
});