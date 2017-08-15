(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointController', BodyPointController);

    BodyPointController.$inject = ['BodyPoint'];

    function BodyPointController(BodyPoint) {

        var vm = this;

        vm.bodyPoints = [];

        loadAll();

        function loadAll() {
            BodyPoint.query(function(result) {
                vm.bodyPoints = result;
                vm.searchQuery = null;
            });
        }
    }
})();
