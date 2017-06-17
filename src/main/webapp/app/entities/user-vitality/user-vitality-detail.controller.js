(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserVitalityDetailController', UserVitalityDetailController);

    UserVitalityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserVitality', 'UserApp'];

    function UserVitalityDetailController($scope, $rootScope, $stateParams, previousState, entity, UserVitality, UserApp) {
        var vm = this;

        vm.userVitality = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:userVitalityUpdate', function(event, result) {
            vm.userVitality = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
