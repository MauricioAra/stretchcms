(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserVitalityDialogController', UserVitalityDialogController);

    UserVitalityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserVitality', 'UserApp'];

    function UserVitalityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserVitality, UserApp) {
        var vm = this;

        vm.userVitality = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userapps = UserApp.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userVitality.id !== null) {
                UserVitality.update(vm.userVitality, onSaveSuccess, onSaveError);
            } else {
                UserVitality.save(vm.userVitality, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:userVitalityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
