'use strict';

ntipafacerecognizerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/face', {
                    templateUrl: 'views/faces.html',
                    controller: 'FaceController',
                    resolve:{
                        resolvedFace: ['Face', function (Face) {
                            return Face.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
