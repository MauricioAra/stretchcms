(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserHealthDetailController', UserHealthDetailController);

    UserHealthDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserHealth', 'BodyPart', 'UserApp'];

    function UserHealthDetailController($scope, $rootScope, $stateParams, previousState, entity, UserHealth, BodyPart, UserApp) {
        var vm = this;

        vm.userHealth = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:userHealthUpdate', function(event, result) {
            vm.userHealth = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
