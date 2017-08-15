(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointDialogController', BodyPointDialogController);

    BodyPointDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BodyPoint', 'UserApp', 'BodyPointRegistry'];

    function BodyPointDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BodyPoint, UserApp, BodyPointRegistry) {
        var vm = this;

        vm.bodyPoint = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userapps = UserApp.query();
        vm.bodypointregistries = BodyPointRegistry.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bodyPoint.id !== null) {
                BodyPoint.update(vm.bodyPoint, onSaveSuccess, onSaveError);
            } else {
                BodyPoint.save(vm.bodyPoint, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:bodyPointUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
