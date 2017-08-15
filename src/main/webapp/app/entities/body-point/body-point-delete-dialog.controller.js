(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointDeleteController',BodyPointDeleteController);

    BodyPointDeleteController.$inject = ['$uibModalInstance', 'entity', 'BodyPoint'];

    function BodyPointDeleteController($uibModalInstance, entity, BodyPoint) {
        var vm = this;

        vm.bodyPoint = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BodyPoint.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
