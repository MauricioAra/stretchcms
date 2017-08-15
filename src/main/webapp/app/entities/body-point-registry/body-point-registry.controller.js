(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPointRegistryController', BodyPointRegistryController);

    BodyPointRegistryController.$inject = ['BodyPointRegistry'];

    function BodyPointRegistryController(BodyPointRegistry) {

        var vm = this;

        vm.bodyPointRegistries = [];

        loadAll();

        function loadAll() {
            BodyPointRegistry.query(function(result) {
                vm.bodyPointRegistries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
