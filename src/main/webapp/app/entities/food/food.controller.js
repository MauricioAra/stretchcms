(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('FoodController', FoodController);

    FoodController.$inject = ['Food'];

    function FoodController(Food) {

        var vm = this;

        vm.foods = [];

        loadAll();

        function loadAll() {
            Food.query(function(result) {
                vm.foods = result;
                vm.searchQuery = null;
            });
        }
    }
})();
