(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('UserHealth', UserHealth);

    UserHealth.$inject = ['$resource'];

    function UserHealth ($resource) {
        var resourceUrl =  'api/user-healths/:id';

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
