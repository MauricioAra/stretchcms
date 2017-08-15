(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointRegistryDeleteController',BodyPointRegistryDeleteController);

    BodyPointRegistryDeleteController.$inject = ['$uibModalInstance', 'entity', 'BodyPointRegistry'];

    function BodyPointRegistryDeleteController($uibModalInstance, entity, BodyPointRegistry) {
        var vm = this;

        vm.bodyPointRegistry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BodyPointRegistry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
