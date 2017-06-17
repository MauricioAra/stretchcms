(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('CalendarDeleteController',CalendarDeleteController);

    CalendarDeleteController.$inject = ['$uibModalInstance', 'entity', 'Calendar'];

    function CalendarDeleteController($uibModalInstance, entity, Calendar) {
        var vm = this;

        vm.calendar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Calendar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
