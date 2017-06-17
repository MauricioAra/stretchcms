(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('SubCategoryDialogController', SubCategoryDialogController);

    SubCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubCategory', 'BodyPart', 'Category'];

    function SubCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubCategory, BodyPart, Category) {
        var vm = this;

        vm.subCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bodyparts = BodyPart.query();
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subCategory.id !== null) {
                SubCategory.update(vm.subCategory, onSaveSuccess, onSaveError);
            } else {
                SubCategory.save(vm.subCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:subCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
