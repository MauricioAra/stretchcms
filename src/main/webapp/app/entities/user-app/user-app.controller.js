(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserAppController', UserAppController);

    UserAppController.$inject = ['UserApp'];

    function UserAppController(UserApp) {

        var vm = this;

        vm.userApps = [];

        loadAll();

        function loadAll() {
            UserApp.query(function(result) {
                vm.userApps = result;
                vm.searchQuery = null;
            });
        }
    }
})();
