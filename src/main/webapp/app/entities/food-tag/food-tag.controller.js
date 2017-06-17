(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodTagController', FoodTagController);

    FoodTagController.$inject = ['FoodTag'];

    function FoodTagController(FoodTag) {

        var vm = this;

        vm.foodTags = [];

        loadAll();

        function loadAll() {
            FoodTag.query(function(result) {
                vm.foodTags = result;
                vm.searchQuery = null;
            });
        }
    }
})();
