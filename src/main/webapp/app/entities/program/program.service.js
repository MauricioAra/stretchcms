(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('Program', Program);

    Program.$inject = ['$resource'];

    function Program ($resource) {
        var resourceUrl =  'api/programs/:id';

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
