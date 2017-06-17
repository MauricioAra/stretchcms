(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ExerciseDetailController', ExerciseDetailController);

    ExerciseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exercise', 'BodyPart', 'UserApp', 'Program'];

    function ExerciseDetailController($scope, $rootScope, $stateParams, previousState, entity, Exercise, BodyPart, UserApp, Program) {
        var vm = this;

        vm.exercise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:exerciseUpdate', function(event, result) {
            vm.exercise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
