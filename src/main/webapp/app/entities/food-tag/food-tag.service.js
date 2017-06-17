(function() {
    'use strict';
    angular
        .module('stretchCmsApp')
        .factory('FoodTag', FoodTag);

    FoodTag.$inject = ['$resource'];

    function FoodTag ($resource) {
        var resourceUrl =  'api/food-tags/:id';

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
