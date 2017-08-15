(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointRegistryDetailController', BodyPointRegistryDetailController);

    BodyPointRegistryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BodyPointRegistry', 'BodyPoint'];

    function BodyPointRegistryDetailController($scope, $rootScope, $stateParams, previousState, entity, BodyPointRegistry, BodyPoint) {
        var vm = this;

        vm.bodyPointRegistry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:bodyPointRegistryUpdate', function(event, result) {
            vm.bodyPointRegistry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
