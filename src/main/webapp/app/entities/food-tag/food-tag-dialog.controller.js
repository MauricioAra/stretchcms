(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodTagDialogController', FoodTagDialogController);

    FoodTagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FoodTag', 'Food'];

    function FoodTagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FoodTag, Food) {
        var vm = this;

        vm.foodTag = entity;
        vm.clear = clear;
        vm.save = save;
        vm.foods = Food.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.foodTag.id !== null) {
                FoodTag.update(vm.foodTag, onSaveSuccess, onSaveError);
            } else {
                FoodTag.save(vm.foodTag, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:foodTagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
