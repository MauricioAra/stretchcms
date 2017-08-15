(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('BodyPoint', BodyPoint);

    BodyPoint.$inject = ['$resource'];

    function BodyPoint ($resource) {
        var resourceUrl =  'api/body-points/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
