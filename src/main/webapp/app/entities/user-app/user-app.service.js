(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('UserApp', UserApp);

    UserApp.$inject = ['$resource'];

    function UserApp ($resource) {
        var resourceUrl =  'api/user-apps/:id';

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
