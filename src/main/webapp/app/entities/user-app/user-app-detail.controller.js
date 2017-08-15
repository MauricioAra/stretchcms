(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserAppDetailController', UserAppDetailController);

    UserAppDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserApp', 'User', 'UserHealth', 'Program', 'UserVitality', 'Exercise', 'BodyPoint'];

    function UserAppDetailController($scope, $rootScope, $stateParams, previousState, entity, UserApp, User, UserHealth, Program, UserVitality, Exercise, BodyPoint) {
        var vm = this;

        vm.userApp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:userAppUpdate', function(event, result) {
            vm.userApp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
