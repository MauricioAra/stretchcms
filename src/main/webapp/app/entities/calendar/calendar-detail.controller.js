(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('CalendarDetailController', CalendarDetailController);

    CalendarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Calendar', 'Program'];

    function CalendarDetailController($scope, $rootScope, $stateParams, previousState, entity, Calendar, Program) {
        var vm = this;

        vm.calendar = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:calendarUpdate', function(event, result) {
            vm.calendar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
