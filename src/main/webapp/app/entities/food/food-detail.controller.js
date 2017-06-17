(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodDetailController', FoodDetailController);

    FoodDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Food', 'FoodTag'];

    function FoodDetailController($scope, $rootScope, $stateParams, previousState, entity, Food, FoodTag) {
        var vm = this;

        vm.food = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:foodUpdate', function(event, result) {
            vm.food = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
