(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPartDetailController', BodyPartDetailController);

    BodyPartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BodyPart', 'Exercise', 'SubCategory', 'UserHealth'];

    function BodyPartDetailController($scope, $rootScope, $stateParams, previousState, entity, BodyPart, Exercise, SubCategory, UserHealth) {
        var vm = this;

        vm.bodyPart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:bodyPartUpdate', function(event, result) {
            vm.bodyPart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
