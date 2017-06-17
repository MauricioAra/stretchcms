(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodTagDetailController', FoodTagDetailController);

    FoodTagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FoodTag', 'Food'];

    function FoodTagDetailController($scope, $rootScope, $stateParams, previousState, entity, FoodTag, Food) {
        var vm = this;

        vm.foodTag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:foodTagUpdate', function(event, result) {
            vm.foodTag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
