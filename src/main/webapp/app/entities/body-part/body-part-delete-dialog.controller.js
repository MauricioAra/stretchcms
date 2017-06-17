(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPartDeleteController',BodyPartDeleteController);

    BodyPartDeleteController.$inject = ['$uibModalInstance', 'entity', 'BodyPart'];

    function BodyPartDeleteController($uibModalInstance, entity, BodyPart) {
        var vm = this;

        vm.bodyPart = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BodyPart.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
