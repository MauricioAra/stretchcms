(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserHealthController', UserHealthController);

    UserHealthController.$inject = ['UserHealth'];

    function UserHealthController(UserHealth) {

        var vm = this;

        vm.userHealths = [];

        loadAll();

        function loadAll() {
            UserHealth.query(function(result) {
                vm.userHealths = result;
                vm.searchQuery = null;
            });
        }
    }
})();
