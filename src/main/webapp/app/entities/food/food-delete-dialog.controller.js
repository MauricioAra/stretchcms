(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodDeleteController',FoodDeleteController);

    FoodDeleteController.$inject = ['$uibModalInstance', 'entity', 'Food'];

    function FoodDeleteController($uibModalInstance, entity, Food) {
        var vm = this;

        vm.food = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Food.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
