(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramFeedBackDetailController', ProgramFeedBackDetailController);

    ProgramFeedBackDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProgramFeedBack', 'Program'];

    function ProgramFeedBackDetailController($scope, $rootScope, $stateParams, previousState, entity, ProgramFeedBack, Program) {
        var vm = this;

        vm.programFeedBack = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:programFeedBackUpdate', function(event, result) {
            vm.programFeedBack = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
