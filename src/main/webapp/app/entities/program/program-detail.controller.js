(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramDetailController', ProgramDetailController);

    ProgramDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Program', 'ProgramFeedBack', 'Exercise', 'UserApp', 'Calendar'];

    function ProgramDetailController($scope, $rootScope, $stateParams, previousState, entity, Program, ProgramFeedBack, Exercise, UserApp, Calendar) {
        var vm = this;

        vm.program = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:programUpdate', function(event, result) {
            vm.program = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
