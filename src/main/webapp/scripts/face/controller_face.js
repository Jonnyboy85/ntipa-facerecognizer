'use strict';

ntipafacerecognizerApp.controller('FaceController', function ($scope, resolvedFace, Face) {

        $scope.faces = resolvedFace;

//        $scope.onStream = function(stream,video){
//        	$log.debug(stream);
//        	$log.debug(video);
//        	
//        };
        
        $scope.onStream = function(stream,video){
        	$log.debug(stream);
        	$log.debug(video);
        	
        };
        
        
        $scope.create = function () {
            Face.save($scope.face,
                function () {
                    $scope.faces = Face.query();
                    $('#saveFaceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.face = Face.get({id: id});
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
    });
