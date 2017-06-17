(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('Calendar', Calendar);

    Calendar.$inject = ['$resource'];

    function Calendar ($resource) {
        var resourceUrl =  'api/calendars/:id';

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
