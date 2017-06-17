(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserHealthDialogController', UserHealthDialogController);

    UserHealthDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserHealth', 'BodyPart', 'UserApp'];

    function UserHealthDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserHealth, BodyPart, UserApp) {
        var vm = this;

        vm.userHealth = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bodyparts = BodyPart.query();
        vm.userapps = UserApp.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userHealth.id !== null) {
                UserHealth.update(vm.userHealth, onSaveSuccess, onSaveError);
            } else {
                UserHealth.save(vm.userHealth, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:userHealthUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
