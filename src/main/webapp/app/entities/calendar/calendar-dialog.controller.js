(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('CalendarDialogController', CalendarDialogController);

    CalendarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Calendar', 'Program'];

    function CalendarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Calendar, Program) {
        var vm = this;

        vm.calendar = entity;
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
            if (vm.calendar.id !== null) {
                Calendar.update(vm.calendar, onSaveSuccess, onSaveError);
            } else {
                Calendar.save(vm.calendar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:calendarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
