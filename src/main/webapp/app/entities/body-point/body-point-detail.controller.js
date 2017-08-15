(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointDetailController', BodyPointDetailController);

    BodyPointDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BodyPoint', 'UserApp', 'BodyPointRegistry'];

    function BodyPointDetailController($scope, $rootScope, $stateParams, previousState, entity, BodyPoint, UserApp, BodyPointRegistry) {
        var vm = this;

        vm.bodyPoint = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:bodyPointUpdate', function(event, result) {
            vm.bodyPoint = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
