(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserHealthDeleteController',UserHealthDeleteController);

    UserHealthDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserHealth'];

    function UserHealthDeleteController($uibModalInstance, entity, UserHealth) {
        var vm = this;

        vm.userHealth = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserHealth.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
