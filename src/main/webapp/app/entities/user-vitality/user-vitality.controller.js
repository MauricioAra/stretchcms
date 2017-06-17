(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserVitalityController', UserVitalityController);

    UserVitalityController.$inject = ['UserVitality'];

    function UserVitalityController(UserVitality) {

        var vm = this;

        vm.userVitalities = [];

        loadAll();

        function loadAll() {
            UserVitality.query(function(result) {
                vm.userVitalities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
