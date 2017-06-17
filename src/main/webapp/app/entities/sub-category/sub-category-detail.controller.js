(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('SubCategoryDetailController', SubCategoryDetailController);

    SubCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SubCategory', 'BodyPart', 'Category'];

    function SubCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, SubCategory, BodyPart, Category) {
        var vm = this;

        vm.subCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('stretchCmsApp:subCategoryUpdate', function(event, result) {
            vm.subCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
