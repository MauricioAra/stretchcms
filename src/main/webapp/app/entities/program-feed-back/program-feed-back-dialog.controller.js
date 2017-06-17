(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramFeedBackDialogController', ProgramFeedBackDialogController);

    ProgramFeedBackDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProgramFeedBack', 'Program'];

    function ProgramFeedBackDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProgramFeedBack, Program) {
        var vm = this;

        vm.programFeedBack = entity;
        vm.clear = clear;
        vm.save = save;
        vm.programs = Program.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.programFeedBack.id !== null) {
                ProgramFeedBack.update(vm.programFeedBack, onSaveSuccess, onSaveError);
            } else {
                ProgramFeedBack.save(vm.programFeedBack, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:programFeedBackUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
