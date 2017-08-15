(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointRegistryDialogController', BodyPointRegistryDialogController);

    BodyPointRegistryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BodyPointRegistry', 'BodyPoint'];

    function BodyPointRegistryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BodyPointRegistry, BodyPoint) {
        var vm = this;

        vm.bodyPointRegistry = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bodypoints = BodyPoint.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bodyPointRegistry.id !== null) {
                BodyPointRegistry.update(vm.bodyPointRegistry, onSaveSuccess, onSaveError);
            } else {
                BodyPointRegistry.save(vm.bodyPointRegistry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:bodyPointRegistryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
