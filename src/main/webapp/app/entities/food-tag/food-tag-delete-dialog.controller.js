(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodTagDeleteController',FoodTagDeleteController);

    FoodTagDeleteController.$inject = ['$uibModalInstance', 'entity', 'FoodTag'];

    function FoodTagDeleteController($uibModalInstance, entity, FoodTag) {
        var vm = this;

        vm.foodTag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FoodTag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
