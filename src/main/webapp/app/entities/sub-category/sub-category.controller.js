(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('SubCategoryController', SubCategoryController);

    SubCategoryController.$inject = ['SubCategory'];

    function SubCategoryController(SubCategory) {

        var vm = this;

        vm.subCategories = [];

        loadAll();

        function loadAll() {
            SubCategory.query(function(result) {
                vm.subCategories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
