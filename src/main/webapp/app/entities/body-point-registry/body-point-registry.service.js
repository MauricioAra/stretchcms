(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('BodyPointRegistry', BodyPointRegistry);

    BodyPointRegistry.$inject = ['$resource'];

    function BodyPointRegistry ($resource) {
        var resourceUrl =  'api/body-point-registries/:id';

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
