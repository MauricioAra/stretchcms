(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramDialogController', ProgramDialogController);

    ProgramDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Program', 'ProgramFeedBack', 'Exercise', 'UserApp', 'Calendar'];

    function ProgramDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Program, ProgramFeedBack, Exercise, UserApp, Calendar) {
        var vm = this;

        vm.program = entity;
        vm.clear = clear;
        vm.save = save;
        vm.programfeedbacks = ProgramFeedBack.query();
        vm.exercises = Exercise.query();
        vm.userapps = UserApp.query();
        vm.calendars = Calendar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.program.id !== null) {
                Program.update(vm.program, onSaveSuccess, onSaveError);
            } else {
                Program.save(vm.program, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:programUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
