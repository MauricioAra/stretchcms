(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('CategoryController', CategoryController);

    CategoryController.$inject = ['Category'];

    function CategoryController(Category) {

        var vm = this;

        vm.categories = [];

        loadAll();

        function loadAll() {
            Category.query(function(result) {
                vm.categories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
