(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('UserVitality', UserVitality);

    UserVitality.$inject = ['$resource'];

    function UserVitality ($resource) {
        var resourceUrl =  'api/user-vitalities/:id';

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
