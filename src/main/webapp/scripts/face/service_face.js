'use strict';

ntipafacerecognizerApp.factory('Face', function ($resource) {
        return $resource('app/rest/faces/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
