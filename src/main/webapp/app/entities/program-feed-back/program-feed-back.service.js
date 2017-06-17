(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('ProgramFeedBack', ProgramFeedBack);

    ProgramFeedBack.$inject = ['$resource'];

    function ProgramFeedBack ($resource) {
        var resourceUrl =  'api/program-feed-backs/:id';

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
