(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramFeedBackDeleteController',ProgramFeedBackDeleteController);

    ProgramFeedBackDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProgramFeedBack'];

    function ProgramFeedBackDeleteController($uibModalInstance, entity, ProgramFeedBack) {
        var vm = this;

        vm.programFeedBack = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProgramFeedBack.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
